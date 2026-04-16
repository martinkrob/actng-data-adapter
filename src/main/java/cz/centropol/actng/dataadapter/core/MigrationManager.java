/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.centropol.actng.dataadapter.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MigrationManager {

    private final EntityManagerFactory srcEmf;
    private final EntityManagerFactory dstEmf;
    
    // In-memory mapa pro přiřazení: Kód starého zásobníku -> ID nového zásobníku
    private Map<Integer, Integer> stackIdMap = new HashMap<>();

    public MigrationManager() {
        // Inicializace podle persistence.xml
        System.out.println("Inicializuji připojení k databázím...");
        this.srcEmf = Persistence.createEntityManagerFactory("src_PU");
        this.dstEmf = Persistence.createEntityManagerFactory("dst_PU");
    }

    public void startMigration() {
        // 1. Krok: Načtení mapy ETM front
        loadEtmMap();
        
        // 2. Krok: Migrace úkolů v dávkách (např. po 1000)
        migrateTasksInBatches(1000);
        
        System.out.println("Migrace dokončena.");
    }

    private void loadEtmMap() {
        EntityManager dstEm = dstEmf.createEntityManager();
        try {
            System.out.println("Načítám mapování ETM zásobníků...");
            // TODO: Zde napíšeme nativní SQL nebo JPQL dotaz do NOVÉ databáze,
            // který vybere (integration_id, nové_id) a naplní stackIdMap.
            
            // Příklad (pseudokód):
            // List<Object[]> results = dstEm.createNativeQuery("SELECT integration_stack_id, id FROM nova_tabulka_etm").getResultList();
            // for(Object[] row : results) { stackIdMap.put((Integer)row[0], (Integer)row[1]); }
            
        } finally {
            dstEm.close();
        }
    }

    private void migrateTasksInBatches(int batchSize) {
        EntityManager srcEm = srcEmf.createEntityManager();
        
        try {
            // Zjistíme celkový počet (nepovinné, ale dobré pro progress bar)
            Long totalTasks = srcEm.createQuery("SELECT COUNT(t) FROM SRCTask t", Long.class).getSingleResult();
            System.out.println("Celkem úkolů k migraci: " + totalTasks);

            int offset = 0;
            boolean hasMoreData = true;

            while (hasMoreData) {
                // Načtení dávky ze staré DB
                List<cz.centropol.actng.dataadapter.src.entities.Task> oldTasks = srcEm.createQuery("SELECT t FROM SRCTask t ORDER BY t.id", cz.centropol.actng.dataadapter.src.entities.Task.class)
                        .setFirstResult(offset)
                        .setMaxResults(batchSize)
                        .getResultList();

                if (oldTasks.isEmpty()) {
                    hasMoreData = false;
                    break;
                }

                // Transformace a uložení
                processAndSaveBatch(oldTasks);

                offset += batchSize;
                System.out.println("Zpracováno " + offset + " záznamů...");
                
                // Abychom uvolnili paměť v JPA kontextu staré databáze
                srcEm.clear(); 
            }
        } finally {
            srcEm.close();
        }
    }

    private void processAndSaveBatch(List<cz.centropol.actng.dataadapter.src.entities.Task> oldTasks) {
        EntityManager dstEm = dstEmf.createEntityManager();
        dstEm.getTransaction().begin();
        
        try {
            for (cz.centropol.actng.dataadapter.src.entities.Task oldTask : oldTasks) {
                // TODO: 1. Vytvořit instanci nového (cílového) Tasku
                // TODO: 2. Překlopit data (oldTask.getName() -> newTask.setName() atd.)
                // TODO: 3. Namapovat Stack: newTask.setStackId( stackIdMap.get(oldTask.getStackId()) )
                // TODO: 4. dstEm.persist(newTask)
            }
            dstEm.getTransaction().commit();
        } catch (Exception e) {
            dstEm.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            dstEm.close();
        }
    }
}
