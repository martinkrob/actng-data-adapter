package cz.centropol.actng.dataadapter.jobs;

import cz.centropol.actng.dataadapter.core.DatabaseManager;
import cz.centropol.actng.dataadapter.core.MigrationConfig;
import cz.centropol.actng.dataadapter.core.MigrationContext;
import cz.centropol.actng.dataadapter.core.MigrationJob;
import cz.centropol.actng.dataadapter.dst.entities.DSTInteractionType;
import cz.centropol.actng.dataadapter.dst.entities.Url;
import cz.centropol.actng.dataadapter.src.entities.Interaction;
import cz.centropol.actng.dataadapter.src.entities.SRCInteractionType;
import cz.centropol.actng.dataadapter.src.entities.SRCTaskStatusType;
import cz.centropol.actng.dataadapter.src.entities.Task;
import cz.centropol.actng.dataadapter.src.entities.TaskAttemptReport;
import cz.centropol.actng.dataadapter.src.entities.TaskTraceLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

public class TaskMigrationJob implements MigrationJob {

    private final Map<Integer, Integer> stackMap = new HashMap<>();
    private static final int BATCH_SIZE = 100;

    @Override
    public void run(MigrationConfig config) {
        MigrationContext context = new MigrationContext("TASK");
        System.out.println("Zahajuji migraci entit TASK...");

        EntityManager srcEm = null;
        EntityManager dstEm = null;

        try {
            srcEm = DatabaseManager.getInstance().getSourceEntityManager();
            dstEm = DatabaseManager.getInstance().getDestinationEntityManager();

            // 1. Natažení převodní mapy zásobníků z nové DB
            loadEtmMap(dstEm);

            // 2. Samotná migrace v dávkách
            migrateTasksInBatches(config, context, srcEm, dstEm);

        } catch (Exception e) {
            System.err.println("Kritická chyba při běhu migrace: " + e.getMessage());
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

    /**
     * Načte mapování ETM front z nové databáze (integration_id -> id)
     */
    private void loadEtmMap(EntityManager dstEm) {
        System.out.println("Načítám mapování ETM zásobníků...");        
        String sql = "SELECT integration_id, id FROM cfg_etm WHERE integration_id IS NOT NULL";
        List<Object[]> results = dstEm.createNativeQuery(sql).getResultList();
        for (Object[] row : results) {
            Integer oldId = ((Number) row[0]).intValue();
            Integer newId = ((Number) row[1]).intValue();
            stackMap.put(oldId, newId);
        }
        System.out.println("Načteno " + stackMap.size() + " mapování ETM zásobníků.");
    }

    /**
     * Hlavní dávková smyčka pro čtení úkolů a jejich historie
     */
    private void migrateTasksInBatches(MigrationConfig config, MigrationContext context, EntityManager srcEm, EntityManager dstEm) {
        System.out.println("Spouštím dávkové zpracování...");

        // Převedeme String stavy z parametru na Enumy pro JPQL dotaz
        List<SRCTaskStatusType> filterStatuses = config.getStatuses().stream()
                .map(s -> SRCTaskStatusType.valueOf(s))
                .collect(Collectors.toList());

        String jpql = "SELECT t FROM SRCTask t "
                + "WHERE t.modificationTime >= :start "
                + "AND t.modificationTime <= :end "
                + "AND t.status IN :statuses "
                + "ORDER BY t.id ASC";

        int offset = 0;
        boolean hasMoreData = true;

        while (hasMoreData) {
            // Načtení dávky úkolů ze staré DB
            List<Task> oldTasks = srcEm.createQuery(jpql, Task.class)
                    .setParameter("start", config.getStartDate())
                    .setParameter("end", config.getEndDate())
                    .setParameter("statuses", filterStatuses)
                    .setFirstResult(offset)
                    .setMaxResults(BATCH_SIZE)
                    .getResultList();

            if (oldTasks.isEmpty()) {
                hasMoreData = false;
                break;
            }

            // Výběr IDček z aktuální dávky pro efektivní dotaz na historii (Řešení N+1)
            List<Integer> oldTaskIds = oldTasks.stream().map(Task::getId).collect(Collectors.toList());

            // Hromadné načtení Attemptů pro celou dávku
            List<TaskAttemptReport> attempts = srcEm.createQuery(
                    "SELECT a FROM SRCTaskAttemptReport a WHERE a.taskId IN :ids", TaskAttemptReport.class)
                    .setParameter("ids", oldTaskIds)
                    .getResultList();

            // Hromadné načtení TraceLogů k těmto Attemptům
            List<Integer> attemptIds = attempts.stream().map(TaskAttemptReport::getId).collect(Collectors.toList());
            List<TaskTraceLog> traceLogs = new ArrayList<>();
            if (!attemptIds.isEmpty()) {
                traceLogs = srcEm.createQuery(
                        "SELECT l FROM SRCTaskTraceLog l WHERE l.attemptId IN :attemptIds", TaskTraceLog.class)
                        .setParameter("attemptIds", attemptIds)
                        .getResultList();
            }

            // --- NOVÉ: Hromadné načtení Interakcí pro celou dávku ---
            // Záměrně BEZ časového filtru, chceme přenést kompletní historii zmigrovaného úkolu!
            List<cz.centropol.actng.dataadapter.src.entities.Interaction> interactions = new ArrayList<>();
            if (!oldTaskIds.isEmpty()) {
                interactions = srcEm.createQuery(
                        "SELECT i FROM SRCInteraction i WHERE i.task.id IN :ids", 
                        cz.centropol.actng.dataadapter.src.entities.Interaction.class)
                        .setParameter("ids", oldTaskIds)
                        .getResultList();
            }

            // Rozřazení historie k úkolům a zápis do cíle
            processAndSaveBatch(oldTasks, attempts, traceLogs, interactions, context, dstEm);

            offset += BATCH_SIZE;
            System.out.println("Zpracováno " + offset + " úkolů...");

            // Vyčištění paměti pro další dávku!
            srcEm.clear();
        }
    }

    /**
     * Zpracování, transformace a uložení jedné dávky do nové DB
     */
    private void processAndSaveBatch(List<Task> oldTasks, List<TaskAttemptReport> allAttempts,
            List<TaskTraceLog> allLogs, List<Interaction> allInteractions, 
            MigrationContext context, EntityManager dstEm) {

        dstEm.getTransaction().begin();

        try {
            for (Task oldTask : oldTasks) {
                context.incrementTotal();
                
                try {                    
                    mapAndSaveTask(oldTask, allAttempts, allLogs, allInteractions, dstEm);
                    context.incrementSuccess();
                    
                } catch (Exception e) {                    
                    context.addError(String.valueOf(oldTask.getId()), "Mapování a zápis", e.getMessage());
                }
            }
            dstEm.getTransaction().commit();

        } catch (Exception e) {
            dstEm.getTransaction().rollback();
            context.addError("BATCH", "Commit transakce", "Dávka selhala: " + e.getMessage());
        } finally {
            dstEm.clear();                                                      // Uvolníme paměť cílového kontextu
        }
    }
    
    /**
     * Provede kompletní mapování jednoho úkolu včetně kolekcí, historie a interakcí.
     */
    private void mapAndSaveTask(cz.centropol.actng.dataadapter.src.entities.Task oldTask,
                                List<cz.centropol.actng.dataadapter.src.entities.TaskAttemptReport> allAttempts,
                                List<cz.centropol.actng.dataadapter.src.entities.TaskTraceLog> allLogs,
                                List<cz.centropol.actng.dataadapter.src.entities.Interaction> allInteractions,
                                EntityManager dstEm) {

        // ==========================================================
        // 1. IDEMPOTENCE (Zabránění duplicitám při opakovaném spuštění)
        // ==========================================================
        cz.centropol.actng.dataadapter.dst.entities.Task newTask = null;
        List<cz.centropol.actng.dataadapter.dst.entities.Task> existingTasks = dstEm.createQuery(
                "SELECT t FROM DSTTask t WHERE t.integrationId = :intId", 
                cz.centropol.actng.dataadapter.dst.entities.Task.class)
                .setParameter("intId", oldTask.getId())
                .getResultList();

        boolean isUpdate = !existingTasks.isEmpty();
        if (isUpdate) {
            newTask = existingTasks.get(0);
            // U updatu vyčistíme staré kolekce, aby se nám nehromadily
            if (newTask.getUrl() != null) newTask.getUrl().clear();
            if (newTask.getKvPairs() != null) newTask.getKvPairs().clear();
        } else {
            newTask = new cz.centropol.actng.dataadapter.dst.entities.Task();
            newTask.setIntegrationId(oldTask.getId());
        }

        // ==========================================================
        // 2. MAPOVÁNÍ ZÁKLADNÍCH POLÍ A ENUMŮ ÚKOLU
        // ==========================================================
        newTask.setName(oldTask.getName());
        newTask.setPriority(oldTask.getPriority()); 
        newTask.setStatus(cz.centropol.actng.dataadapter.dst.entities.DSTTaskStatusType.valueOf(oldTask.getStatus().name()));
        newTask.setType(cz.centropol.actng.dataadapter.dst.entities.DSTTaskType.valueOf(oldTask.getType().name()));
        newTask.setNote(oldTask.getNote());
        newTask.setProcessDesc(oldTask.getProcessDesc());
        newTask.setPhoneNumber(oldTask.getPhoneNumber());
        newTask.setExtGUID(oldTask.getExtGuid());        
        newTask.setCustomerNo(oldTask.getCustomerNo());
        
        newTask.setCreationTime(oldTask.getCreationTime());
        newTask.setModificationTime(oldTask.getModificationTime());
        newTask.setExpiryDate(oldTask.getExpiryDate());
        newTask.setNextAttemptTime(oldTask.getNextAttemptTime() != null ? oldTask.getNextAttemptTime() : oldTask.getModificationTime());
        newTask.setLastAttemptTime(oldTask.getLastAttemptTime());

        newTask.setLastAppliedExpProcRule(oldTask.getLastAppliedExpProcRule());
        newTask.setLastSolver(mapAgentId(oldTask.getProcessedBy()));
        newTask.setReserved(oldTask.isReserved());        
        newTask.setReservedFor(mapAgentId(oldTask.getAgentId()));        
        newTask.setFlagArchived(oldTask.isArchived());
                        
        // ==========================================================
        // 3. MAPOVÁNÍ ETM ZÁSOBNÍKŮ
        // ==========================================================
        Integer newStackId = stackMap.get(oldTask.getStackId());
        if (newStackId == null) {
            throw new RuntimeException("Nenalezeno mapování pro stack_fk: " + oldTask.getStackId());
        }
        newTask.setStackId(newStackId);

        if (oldTask.getPreviousStackId() != null) {
            Integer prevStackId = stackMap.get(oldTask.getPreviousStackId());
            newTask.setPreviousStackId(prevStackId != null ? prevStackId : newStackId);
        } else {
            newTask.setPreviousStackId(newStackId);
        }

        // ==========================================================
        // 4. MAPOVÁNÍ KOLEKCÍ ÚKOLU (URL a KVPair)
        // ==========================================================
        if (newTask.getUrl() == null) newTask.setUrl(new ArrayList<>());
        if (oldTask.getUrl() != null) {
            for (cz.centropol.actng.dataadapter.src.entities.Url oldUrl : oldTask.getUrl()) {
                cz.centropol.actng.dataadapter.dst.entities.Url newUrl = new cz.centropol.actng.dataadapter.dst.entities.Url();
                newUrl.setUrl(oldUrl.getUrl());
                newTask.getUrl().add(newUrl);
            }
        }

        if (newTask.getKvPairs() == null) newTask.setKvPairs(new ArrayList<>());
        if (oldTask.getKvPairs() != null) {
            for (cz.centropol.actng.dataadapter.src.entities.KVPair oldKv : oldTask.getKvPairs()) {
                cz.centropol.actng.dataadapter.dst.entities.KVPair newKv = new cz.centropol.actng.dataadapter.dst.entities.KVPair();
                newKv.setKey(oldKv.getKey());
                newKv.setValue(oldKv.getValue());
                newTask.getKvPairs().add(newKv);
            }
        }

        // ==========================================================
        // 5. ULOŽENÍ ÚKOLU PRO ZÍSKÁNÍ ID
        // ==========================================================
        if (isUpdate) {
            newTask = dstEm.merge(newTask);
        } else {
            dstEm.persist(newTask);
        }
        dstEm.flush(); // Vynutí zápis do MariaDB a naplní newTask.getId()

        // ==========================================================
        // 6. MIGRACE HISTORIE ÚKOLU (AttemptReport a TraceLog)
        // ==========================================================
        if (isUpdate) {
            dstEm.createQuery("DELETE FROM DSTTaskTraceLog l WHERE l.attempt.taskId = :tid")
                 .setParameter("tid", newTask.getId()).executeUpdate();
            dstEm.createQuery("DELETE FROM DSTTaskAttempt a WHERE a.taskId = :tid")
                 .setParameter("tid", newTask.getId()).executeUpdate();
        }

        List<cz.centropol.actng.dataadapter.src.entities.TaskAttemptReport> myAttempts = allAttempts.stream()
                .filter(a -> a.getTaskId() != null && a.getTaskId().equals(oldTask.getId()))
                .collect(Collectors.toList());

        for (cz.centropol.actng.dataadapter.src.entities.TaskAttemptReport oldAttempt : myAttempts) {
            cz.centropol.actng.dataadapter.dst.entities.TaskAttempt newAttempt = new cz.centropol.actng.dataadapter.dst.entities.TaskAttempt();
            newAttempt.setTaskId(newTask.getId()); 
            newAttempt.setAttemptTime(oldAttempt.getAttemptTime());
            
            dstEm.persist(newAttempt);
            dstEm.flush(); 

            if (oldTask.getLastAttemptId() != null && oldTask.getLastAttemptId().equals(oldAttempt.getId())) {
                newTask.setLastAttemptId(newAttempt.getId());
                dstEm.merge(newTask);
            }

            List<cz.centropol.actng.dataadapter.src.entities.TaskTraceLog> myLogs = allLogs.stream()
                    .filter(l -> l.getAttemptId() != null && l.getAttemptId().equals(oldAttempt.getId()))
                    .collect(Collectors.toList());

            for (cz.centropol.actng.dataadapter.src.entities.TaskTraceLog oldLog : myLogs) {
                cz.centropol.actng.dataadapter.dst.entities.TaskTraceLog newLog = new cz.centropol.actng.dataadapter.dst.entities.TaskTraceLog();
                newLog.setAttempt(newAttempt); 
                newLog.setEventTime(oldLog.getEventTime());
                newLog.setEventType(cz.centropol.actng.dataadapter.dst.entities.DSTTaskEventType.valueOf(oldLog.getEventType().name()));
                newLog.setOrigReason(oldLog.getOrigReason());
                newLog.setOrigReasonDesc(oldLog.getOrigReasonDesc());
                newLog.setUniqueId(oldLog.getUniqueId());
                newLog.setQueue(oldLog.getQueue());
                newLog.setAgentId(mapAgentId(oldLog.getAgentId()));
                newLog.setData1(oldLog.getData1());
                newLog.setData2(oldLog.getData2());
                newLog.setData3(oldLog.getData3());
                dstEm.persist(newLog);
            }
        }

        // ==========================================================
        // 7. MIGRACE INTERAKCÍ (PhoneCall a PersonalVisit)
        // ==========================================================
        if (isUpdate) {
            dstEm.createQuery("DELETE FROM DSTInteraction i WHERE i.taskFk = :tid")
                 .setParameter("tid", newTask.getId()).executeUpdate();
        }

        List<cz.centropol.actng.dataadapter.src.entities.Interaction> myInteractions = allInteractions.stream()
                .filter(i -> i.getTask() != null && i.getTask().getId().equals(oldTask.getId()))
                .collect(Collectors.toList());

        for (cz.centropol.actng.dataadapter.src.entities.Interaction srcInt : myInteractions) {
            
            if (srcInt.getAgentId() == null || srcInt.getAgentId().trim().isEmpty() || srcInt.getType() == null) {
                System.out.println("Přeskočena interakce " + srcInt.getInteractionId() + " (chybí agent nebo typ).");
                continue;
            }

            cz.centropol.actng.dataadapter.dst.entities.Interaction dstInt = new cz.centropol.actng.dataadapter.dst.entities.Interaction();
            dstInt.setInteractionID(srcInt.getInteractionId());
            dstInt.setParentID(srcInt.getParentId());
            dstInt.setType(mapInteractionType(srcInt.getType())); 
            dstInt.setStartTime(srcInt.getStartTime());
            dstInt.setEndTime(srcInt.getEndTime());
            dstInt.setAgentID(mapAgentId(srcInt.getAgentId()));
            dstInt.setTaskFk(newTask.getId());             
                        
            // --- HOVOR ---
            if (srcInt.getPhoneCall() != null) {
                cz.centropol.actng.dataadapter.dst.entities.PhoneCall dstPhone = new cz.centropol.actng.dataadapter.dst.entities.PhoneCall();
                dstPhone.setStartTime(srcInt.getPhoneCall().getStartTime());
                dstPhone.setAnswerTime(srcInt.getPhoneCall().getAnswerTime());
                dstPhone.setHangupTime(srcInt.getPhoneCall().getHangupTime() != null ? srcInt.getPhoneCall().getHangupTime() : srcInt.getEndTime());
                dstPhone.setCallerID(srcInt.getPhoneCall().getCallerId());
                dstPhone.setCalledNumber(srcInt.getPhoneCall().getCalledNumber());
                dstPhone.setQueue(srcInt.getPhoneCall().getQueue());
                dstPhone.setUniqueID(srcInt.getPhoneCall().getUniqueId());
                dstPhone.setCustomerNo(srcInt.getCustomerNo());
                
                if (srcInt.getCustomerId() > 0) {
                    dstPhone.setExtCustID((int) srcInt.getCustomerId());
                }
                    
                if (srcInt.getActivityUrl() != null && !srcInt.getActivityUrl().isEmpty()) {
                    dstPhone.setUrl(new ArrayList<Url>());
                    srcInt.getActivityUrl().forEach((url) -> {
                        cz.centropol.actng.dataadapter.dst.entities.Url newUrl = new cz.centropol.actng.dataadapter.dst.entities.Url();
                        newUrl.setUrl(url.getUrl());
                        dstPhone.getUrl().add(newUrl);
                    });
                }
                                
                dstInt.setCall(dstPhone); 
            }

            // --- NÁVŠTĚVA ---
            if (srcInt.getVisit() != null) {
                cz.centropol.actng.dataadapter.dst.entities.PersonalVisit dstVisit = new cz.centropol.actng.dataadapter.dst.entities.PersonalVisit();
                dstVisit.setVisitID(srcInt.getVisit().getVisit_id());
                dstVisit.setSubject(srcInt.getVisit().getSubject());
                dstVisit.setNote(srcInt.getVisit().getNote());
                dstVisit.setCreationTime(srcInt.getVisit().getCreationTime());
                dstVisit.setCustomerNo(srcInt.getCustomerNo());
                
                // ==========================================
                // PŘENOS AUDIO ZÁZNAMU
                // ==========================================                          
                if (srcInt.getVisit().getAudioRecord() != null && srcInt.getVisit().getAudioRecord().isArchived()) {
                    cz.centropol.actng.dataadapter.src.entities.Attachment srcAudio = srcInt.getVisit().getAudioRecord();
                    cz.centropol.actng.dataadapter.dst.entities.Attachment dstAudio = new cz.centropol.actng.dataadapter.dst.entities.Attachment();
                    
                    dstAudio.setArchivePath(srcAudio.getArchivePath());
                    dstAudio.setArchived(true);
                    dstAudio.setFileName(srcAudio.getFileName());
                    dstAudio.setContentId(0);                    
                    dstAudio.setSize(srcAudio.getSize());                                        
                    
                    dstVisit.setAudioRecord(dstAudio);
                }
                
                if (srcInt.getCustomerId() > 0) {
                    dstVisit.setExtCustID((int) srcInt.getCustomerId());
                }
                
                if (srcInt.getVisit().getKvPairs() != null) {
                    List<cz.centropol.actng.dataadapter.dst.entities.KVPair> dstKvPairs = new ArrayList<>();
                    for (cz.centropol.actng.dataadapter.src.entities.KVPair srcKv : srcInt.getVisit().getKvPairs()) {
                        cz.centropol.actng.dataadapter.dst.entities.KVPair dstKv = new cz.centropol.actng.dataadapter.dst.entities.KVPair();
                        dstKv.setKey(srcKv.getKey());
                        dstKv.setValue(srcKv.getValue());
                        dstKvPairs.add(dstKv);
                    }
                    dstVisit.setKvPairs(dstKvPairs);
                }
                
                if (srcInt.getVisit().getUrl() != null) {
                    List<cz.centropol.actng.dataadapter.dst.entities.Url> dstUrls = new ArrayList<>();
                    for (cz.centropol.actng.dataadapter.src.entities.Url srcUrl : srcInt.getVisit().getUrl()) {
                        cz.centropol.actng.dataadapter.dst.entities.Url dstUrl = new cz.centropol.actng.dataadapter.dst.entities.Url();
                        dstUrl.setUrl(srcUrl.getUrl());
                        dstUrls.add(dstUrl);
                    }
                    dstVisit.setUrl(dstUrls);
                }
                
                dstInt.setVisit(dstVisit);
            }

            dstEm.persist(dstInt);
        }
    }
    
    private DSTInteractionType mapInteractionType(SRCInteractionType srcType) {
        if (srcType == null) return null;
        
        switch (srcType) {
            case ACD_CALL:
                return DSTInteractionType.INBOUND_CALL;
            case OUTBOUND_CALL:
                return DSTInteractionType.OUTBOUND_CALL;
            case OUTBOUND_EMAIL:
                return DSTInteractionType.OUTBOUND_EMAIL;
            case EMAIL:
                return DSTInteractionType.INBOUND_EMAIL;
            case VISIT:
                return DSTInteractionType.VISIT;
            case VOICEBOT:
                return DSTInteractionType.VOICEBOT;
            case CHATBOT:
                return DSTInteractionType.CHATBOT;
            case TASK:
            default:
                return DSTInteractionType.TASK;
        }
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
