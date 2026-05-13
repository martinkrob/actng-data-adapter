package cz.centropol.actng.dataadapter.jobs;

import cz.centropol.actng.dataadapter.core.DatabaseManager;
import cz.centropol.actng.dataadapter.core.MigrationConfig;
import cz.centropol.actng.dataadapter.core.MigrationContext;
import cz.centropol.actng.dataadapter.core.MigrationJob;
import cz.centropol.actng.dataadapter.dst.entities.Attachment;
import cz.centropol.actng.dataadapter.dst.entities.MonitorFile;
import java.util.List;
import javax.persistence.EntityManager;

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

                    // --- PODMÍNKA ARCHIVACE (dle zadání) ---
                    cz.centropol.actng.dataadapter.src.entities.Attachment srcAtt = src.getAudioRecord();
                    if (srcAtt == null || !srcAtt.isArchived() || srcAtt.getArchivePath() == null || srcAtt.getArchivePath().isBlank()) {
                        // Přeskakujeme záznam, který není řádně archivován
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
                    dst.setAgentID(src.getAgentId());
                    
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
                    
                    // Poznámka a Voicebot data
                    dst.setNote(src.getNote());
                    dst.setTranscript(src.getVoicebotData()); // Mapování voicebotData na transcript (zatím)
                    
                    // Logika isHuman: human = true pokud voicebotData == null
                    dst.setHuman(src.getVoicebotData() == null);
                    
                    // Mapování Attachmentu (přílohy)
                    Attachment dstAtt = new Attachment();
                    dstAtt.setFileName(srcAtt.getFileName());
                    dstAtt.setContentId(srcAtt.getContentId());
                    dstAtt.setSize(srcAtt.getSize());
                    dstAtt.setArchived(true); // Musí být true dle podmínky
                    dstAtt.setArchivePath(srcAtt.getArchivePath());
                    
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
}
