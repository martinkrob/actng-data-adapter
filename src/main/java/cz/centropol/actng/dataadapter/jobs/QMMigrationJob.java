package cz.centropol.actng.dataadapter.jobs;

import cz.centropol.actng.dataadapter.core.DatabaseManager;
import cz.centropol.actng.dataadapter.core.MigrationConfig;
import cz.centropol.actng.dataadapter.core.MigrationContext;
import cz.centropol.actng.dataadapter.core.MigrationJob;
import cz.centropol.actng.dataadapter.dst.entities.Task;
import cz.centropol.actng.dataadapter.dst.entities.QMAnswerType;
import cz.centropol.actng.dataadapter.dst.entities.QMEvaluation;
import cz.centropol.actng.dataadapter.dst.entities.QMEvaluationSet;
import cz.centropol.actng.dataadapter.dst.entities.QMEvoluationSetType;
import cz.centropol.actng.dataadapter.dst.entities.QMFileSelectionType;
import cz.centropol.actng.dataadapter.dst.entities.QMForm;
import cz.centropol.actng.dataadapter.dst.entities.QMQuestion;
import cz.centropol.actng.dataadapter.dst.entities.QMWorktop;
import cz.centropol.actng.dataadapter.src.entities.SRCQMEvaluation;
import cz.centropol.actng.dataadapter.src.entities.SRCQMEvaluationSet;
import cz.centropol.actng.dataadapter.src.entities.SRCQMForm;
import cz.centropol.actng.dataadapter.src.entities.SRCQMQuestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class QMMigrationJob implements MigrationJob {

    private static final int BATCH_SIZE = 100;
    
    // Mapování pro formuláře a otázky (Klíč = SRC ID, Hodnota = DST Entita)
    private final Map<Integer, QMForm> formMap = new HashMap<>();
    private final Map<Integer, QMQuestion> questionMap = new HashMap<>();

    @Override
    public void run(MigrationConfig config) {
        MigrationContext context = new MigrationContext("QM");
        System.out.println("Zahajuji migraci entit QM (Hodnocení kvality)...");

        EntityManager srcEm = null;
        EntityManager dstEm = null;

        try {
            srcEm = DatabaseManager.getInstance().getSourceEntityManager();
            dstEm = DatabaseManager.getInstance().getDestinationEntityManager();

            // 1. Načtení zmigrovaných formulářů a otázek
            loadFormAndQuestionMappings(dstEm, context);

            // 2. Migrace sad hodnocení
            migrateEvaluationSets(config, srcEm, dstEm, context);

        } catch (Exception e) {
            System.err.println("Kritická chyba při běhu QM migrace: " + e.getMessage());
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

    private void loadFormAndQuestionMappings(EntityManager dstEm, MigrationContext context) {
        System.out.println("Načítám mapování již zmigrovaných formulářů a otázek...");
        
        List<QMForm> dstForms = dstEm.createQuery("SELECT f FROM QMForm f WHERE f.integrationId IS NOT NULL", QMForm.class).getResultList();
        for (QMForm dstForm : dstForms) {
            formMap.put(dstForm.getIntegrationId(), dstForm);
            if (dstForm.getQuestions() != null) {
                for (QMQuestion dstQ : dstForm.getQuestions()) {
                    if (dstQ.getIntegrationId() != null) {
                        questionMap.put(dstQ.getIntegrationId(), dstQ);
                    }
                }
            }
        }
        System.out.println("Načteno " + formMap.size() + " formulářů a " + questionMap.size() + " otázek.");
    }

    private void migrateEvaluationSets(MigrationConfig config, EntityManager srcEm, EntityManager dstEm, MigrationContext context) {
        System.out.println("Zahajuji migraci sad hodnocení...");

        // Řazení podle hodnotitele a času vytvoření, abychom mohli uplatnit logiku Worktopů
        String jpql = "SELECT s FROM SRCQMEvaluationSet s "
                + "WHERE s.creationTime >= :start "
                + "AND s.creationTime <= :end "
                + "ORDER BY s.evaluator.userName ASC, s.creationTime ASC";

        int offset = 0;
        boolean hasMoreData = true;

        // Stavové proměnné pro algoritmus seskupování do Worktopů
        String lastEvaluatorName = null;
        String lastNote = null;
        Date lastTime = null;
        QMWorktop currentWorktop = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        while (hasMoreData) {
            List<SRCQMEvaluationSet> oldSets = srcEm.createQuery(jpql, SRCQMEvaluationSet.class)
                    .setParameter("start", config.getStartDate())
                    .setParameter("end", config.getEndDate())
                    .setFirstResult(offset)
                    .setMaxResults(BATCH_SIZE)
                    .getResultList();

            if (oldSets.isEmpty()) {
                hasMoreData = false;
                break;
            }

            dstEm.getTransaction().begin();

            try {
                for (SRCQMEvaluationSet srcSet : oldSets) {
                    context.incrementTotal();

                    // 1. Zjištění IDEMPOTENCE (zda už sada s tímto starým ID neexistuje)
                    List<QMEvaluationSet> existingSets = dstEm.createQuery(
                            "SELECT s FROM QMEvaluationSet s WHERE s.integrationId = :intId", QMEvaluationSet.class)
                            .setParameter("intId", srcSet.getId())
                            .getResultList();

                    String evalName = srcSet.getEvaluator() != null ? srcSet.getEvaluator().getUserName() : "Neznámý";
                    String agentName = srcSet.getAgent() != null ? srcSet.getAgent().getUserName() : "Neznámý";
                    String note = srcSet.getNote();
                    Date creationTime = srcSet.getCreationTime();

                    if (!existingSets.isEmpty()) {
                        // Už existuje, přeskočíme, ale aktualizujeme stavové proměnné pro další sady
                        lastEvaluatorName = evalName;
                        lastNote = note;
                        lastTime = creationTime;
                        currentWorktop = existingSets.get(0).getWorktop();
                        continue;
                    }

                    // 2. Řízení QMWorktop (Inteligentní dávkování)
                    boolean isSameEvaluator = evalName.equals(lastEvaluatorName);
                    boolean isSameNote = (note != null && note.equals(lastNote));
                    boolean isWithinTime = (lastTime != null && creationTime != null && 
                            (creationTime.getTime() - lastTime.getTime() < 30 * 60 * 1000));

                    boolean useCurrentWorktop = false;

                    if (isSameEvaluator) {
                        if (isSameNote && note != null && !note.isBlank()) {
                            // Pravidlo 1: Stejný hodnotitel a stejný neprázdný název (kampaň)
                            useCurrentWorktop = true;
                        } else if ((note == null || note.isBlank()) && (lastNote == null || lastNote.isBlank()) && isWithinTime) {
                            // Pravidlo 2: Stejný hodnotitel, oba záznamy bez názvu, vzniklo do 30 min (ad-hoc burst)
                            useCurrentWorktop = true;
                        }
                    }

                    if (!useCurrentWorktop || currentWorktop == null) {
                        // Založit nový Worktop
                        currentWorktop = new QMWorktop();
                        currentWorktop.setOwner(evalName);
                        
                        String wtName = (note != null && !note.isBlank()) ? note : "Hodnocení - " + sdf.format(creationTime);
                        currentWorktop.setName(wtName);
                        currentWorktop.setCreationTime(creationTime);
                        
                        dstEm.persist(currentWorktop);
                    }

                    // 3. Mapování a zápis QMEvaluationSet
                    QMEvaluationSet dstSet = new QMEvaluationSet();
                    dstSet.setIntegrationId(srcSet.getId());
                    dstSet.setWorktop(currentWorktop);
                    dstSet.setEvaluator(evalName);
                    dstSet.setAgent(agentName);
                    
                    dstSet.setType(QMEvoluationSetType.valueOf(srcSet.getType().name()));
                    dstSet.setForm(formMap.get(srcSet.getForm().getId()));
                    dstSet.setCreationTime(creationTime);
                    dstSet.setCallCount(srcSet.getCallCount());
                    dstSet.setDuration(srcSet.getDuration());
                    dstSet.setScore(srcSet.getScore());
                    dstSet.setScoreInPercentage(srcSet.getScoreInPercentage());
                    dstSet.setNote(note);

                    dstEm.persist(dstSet);

                    // 4. Mapování samotných hodnocení a vazby na Task
                    List<SRCQMEvaluation> srcEvals = srcEm.createQuery("SELECT e FROM SRCQMEvaluation e WHERE e.evaluationSet.id = :setId", SRCQMEvaluation.class)
                            .setParameter("setId", srcSet.getId())
                            .getResultList();

                    for (SRCQMEvaluation srcEval : srcEvals) {
                        QMEvaluation dstEval = new QMEvaluation();
                        dstEval.setEvaluationSet(dstSet);
                        dstEval.setCreationTime(creationTime);
                        dstEval.setSelectionType(QMFileSelectionType.valueOf(srcEval.getSelectionType().name()));
                        dstEval.setScore(srcEval.getScore());
                        dstEval.setScoreInPercentage(srcEval.getScoreInPercentage());
                        dstEval.setNote(srcEval.getNote());

                        // DOHLEDÁNÍ CÍLOVÉHO ÚKOLU! (Závislost na TaskMigrationJob)
                        if (srcEval.getOriginTaskId() != null) {
                            List<Task> targetTasks = dstEm.createQuery("SELECT t FROM Task t WHERE t.integrationId = :intId", Task.class)
                                    .setParameter("intId", srcEval.getOriginTaskId())
                                    .setMaxResults(1)
                                    .getResultList();
                            
                            if (!targetTasks.isEmpty()) {
                                dstEval.setOriginTask(targetTasks.get(0));
                            }
                        }

                        // Mapování hodnot odpovědí
                        Map<QMQuestion, Integer> dstValues = new HashMap<>();
                        if (srcEval.getValues() != null) {
                            for (Map.Entry<SRCQMQuestion, Integer> entry : srcEval.getValues().entrySet()) {
                                QMQuestion dstQ = questionMap.get(entry.getKey().getId());
                                if (dstQ != null) {
                                    dstValues.put(dstQ, entry.getValue());
                                }
                            }
                        }
                        dstEval.setValues(dstValues);
                        
                        dstEm.persist(dstEval);
                    }

                    // Aktualizace stavových proměnných pro příští iteraci
                    lastEvaluatorName = evalName;
                    lastNote = note;
                    lastTime = creationTime;

                    context.incrementSuccess();
                }
                dstEm.getTransaction().commit();

            } catch (Exception e) {
                dstEm.getTransaction().rollback();
                context.addError("BATCH", "Chyba dávky (offset: " + offset + ")", e.getMessage());
            } finally {
                dstEm.clear();
                srcEm.clear();
            }

            offset += BATCH_SIZE;
            System.out.println("Zpracováno " + offset + " QM sad...");
        }
    }
}
