package cz.centropol.actng.dataadapter.jobs;

import cz.centropol.actng.dataadapter.core.DatabaseManager;
import cz.centropol.actng.dataadapter.core.MigrationConfig;
import cz.centropol.actng.dataadapter.core.MigrationContext;
import cz.centropol.actng.dataadapter.core.MigrationJob;
import cz.centropol.actng.dataadapter.dst.entities.Attachment;
import cz.centropol.actng.dataadapter.dst.entities.MonitorFile;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MonitorFileMigrationJob implements MigrationJob {

    private static final int BATCH_SIZE = 100;

    @Override
    public void run(MigrationConfig config) {
        MigrationContext context = new MigrationContext("MONITORFILE");
        System.out.println("Zahajuji migraci entit MonitorFile (Nahrávky)...");

        EntityManager srcEm = null;
        EntityManager dstEm = null;

        try {
            srcEm = DatabaseManager.getInstance().getSourceEntityManager();
            dstEm = DatabaseManager.getInstance().getDestinationEntityManager();

            migrateMonitorFiles(config, srcEm, dstEm, context);

        } catch (Exception e) {
            System.err.println("Kritická chyba při běhu MonitorFile migrace: " + e.getMessage());
            e.printStackTrace();
            context.addError("SYSTEM", "Kritické selhání jobu", e.getMessage());
        } finally {
            if (srcEm != null && srcEm.isOpen()) {
                srcEm.close();
            }
            if (dstEm != null && dstEm.isOpen()) {
                dstEm.close();
            }
            context.printConsoleSummary();
            context.exportReport();
        }
    }

    private void migrateMonitorFiles(MigrationConfig config, EntityManager srcEm, EntityManager dstEm, MigrationContext context) {
        String jpql = "SELECT f FROM SRCMonitorFile f "
                + "WHERE f.startTime >= :start "
                + "AND f.startTime <= :end "
                + "ORDER BY f.id ASC";

        int offset = 0;
        boolean hasMoreData = true;

        while (hasMoreData) {
            List<cz.centropol.actng.dataadapter.src.entities.MonitorFile> oldFiles = srcEm.createQuery(jpql, cz.centropol.actng.dataadapter.src.entities.MonitorFile.class)
                    .setParameter("start", config.getStartDate())
                    .setParameter("end", config.getEndDate())
                    .setFirstResult(offset)
                    .setMaxResults(BATCH_SIZE)
                    .getResultList();

            if (oldFiles.isEmpty()) {
                hasMoreData = false;
                break;
            }

            dstEm.getTransaction().begin();

            try {
                for (cz.centropol.actng.dataadapter.src.entities.MonitorFile src : oldFiles) {
                    context.incrementTotal();

                    // --- PODMÍNKA ARCHIVACE (Fallback na historická pole) ---
                    cz.centropol.actng.dataadapter.src.entities.Attachment srcAtt = src.getAudioRecord();
                    
                    boolean isArchived = false;
                    String archivePath = null;
                    int size = 0;
                    String fileName = "unknown";

                    if (srcAtt != null && srcAtt.isArchived() && srcAtt.getArchivePath() != null && !srcAtt.getArchivePath().isBlank()) {
                        // 1. Způsob: Data jsou v novější entitě Attachment
                        isArchived = true;
                        archivePath = srcAtt.getArchivePath();
                        size = srcAtt.getSize();
                        fileName = srcAtt.getFileName();
                    } else if (src.isArchived() && src.getArchivePath() != null && !src.getArchivePath().isBlank()) {
                        // 2. Způsob: Data jsou ve starých historických (Deprecated) atributech přímo v MonitorFile
                        isArchived = true;
                        archivePath = src.getArchivePath();
                        size = src.getSize();
                        fileName = extractFileName(archivePath);
                    }

                    if (!isArchived) {
                        // Přeskakujeme záznam, který není řádně archivován ani jedním ze způsobů
                        continue;
                    }

                    // --- IDEMPOTENCE ---
                    List<MonitorFile> existing = dstEm.createQuery("SELECT f FROM MonitorFile f WHERE f.integrationId = :intId", MonitorFile.class)
                            .setParameter("intId", src.getId())
                            .getResultList();

                    if (!existing.isEmpty()) {
                        context.incrementSuccess();
                        continue;
                    }

                    // --- MAPOVÁNÍ ---
                    MonitorFile dst = new MonitorFile();
                    dst.setIntegrationId(src.getId());
                    dst.setStartTime(src.getStartTime());
                    dst.setAnswerTime(src.getAnswerTime());
                    
                    // Převod duration (millis -> sec)
                    if (src.getDurationInMillis() != null) {
                        dst.setDurationInSec((int) (src.getDurationInMillis() / 1000));
                    }
                    
                    dst.setBillableSeconds(src.getBillableSeconds());
                    dst.setUniqueID(src.getUniqueId());
                    dst.setLinkedID(src.getLinkedId());
                    dst.setAgentID(mapAgentId(src.getAgentId()));
                    
                    // Telefonní atributy
                    dst.setQueue(src.getQueue());
                    dst.setChannel(src.getChannel());
                    dst.setDstContext(src.getDstContext());
                    dst.setAccountCode(src.getAccountCode());
                    dst.setUserField(src.getUserField());
                    dst.setSrc(src.getSrc());
                    dst.setDst(src.getDst());
                    dst.setLastApp(src.getLastApp());
                    dst.setLastData(src.getLastData());
                    
                    // Voicebot data
                    dst.setCollectedData(src.getVoicebotData()); 
                    
                    // Extrakce transcriptu z voicebotData (JSON)
                    if (src.getVoicebotData() != null) {
                        try {
                            JSONParser parser = new JSONParser();
                            JSONObject body = (JSONObject) parser.parse(src.getVoicebotData());
                            JSONArray flow = (JSONArray) body.get("speakFlow");

                            if (flow != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < flow.size(); i++) {
                                    JSONObject activity = (JSONObject) flow.get(i);
                                    Object value = activity.get("type");
                                    Integer type = (value instanceof Number) ? ((Number) value).intValue() : -1;

                                    switch (type) {
                                        case 3 -> {
                                            // BOT
                                            sb.append("VOICEBOT: ").append(String.valueOf(activity.get("activity")));
                                            sb.append("\r\n");
                                        }
                                        case 5 -> {
                                            // CLOVEK
                                            sb.append("VOLAJÍCÍ: ").append(String.valueOf(activity.get("activity")).toUpperCase());
                                            sb.append("\r\n");
                                        }
                                    }
                                }
                                dst.setTranscript(sb.toString());
                            }
                        } catch (ParseException ex) {
                            dst.setTranscript("CHYBA PARSOVÁNÍ: " + ex.getMessage());
                        }
                    }
                    
                    // Logika isHuman: human = true pokud voicebotData == null
                    dst.setHuman(src.getVoicebotData() == null);
                    
                    // Mapování Attachmentu (přílohy) - nezávisle na zdroji
                    Attachment dstAtt = new Attachment();
                    dstAtt.setFileName(fileName);
                    dstAtt.setContentId(0); // Dle zadání natvrdo 0
                    dstAtt.setSize(size);
                    dstAtt.setArchived(true); 
                    dstAtt.setArchivePath(archivePath);
                    
                    dst.setRecord(dstAtt);

                    dstEm.persist(dst);
                    context.incrementSuccess();
                }
                dstEm.getTransaction().commit();

            } catch (Exception e) {
                dstEm.getTransaction().rollback();
                context.addError("BATCH", "Chyba dávky u offsetu " + offset, e.getMessage());
            } finally {
                dstEm.clear();
                srcEm.clear();
            }

            offset += BATCH_SIZE;
            System.out.println("Zpracováno " + offset + " nahrávek...");
        }
    }

    private String extractFileName(String path) {
        if (path == null) {
            return "unknown.wav";
        }
        int maxSlash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        if (maxSlash >= 0 && maxSlash < path.length() - 1) {
            return path.substring(maxSlash + 1);
        }
        return path;
    }

    private String mapAgentId(String oldAgentId) {
        String result = null;
        if (oldAgentId != null && !oldAgentId.isBlank()) {
            int pos = oldAgentId.lastIndexOf(".");
            if (pos == -1) {
                result = oldAgentId;
            } else {
                if (pos > 0) {
                    result = oldAgentId.substring(0, pos);
                }
            }
        }
        return result;
    }
}
