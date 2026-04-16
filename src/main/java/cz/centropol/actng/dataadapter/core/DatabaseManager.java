package cz.centropol.actng.dataadapter.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {

    // Jediná instance v celé aplikaci (Singleton)
    private static DatabaseManager instance;

    private final EntityManagerFactory srcEmf;
    private final EntityManagerFactory dstEmf;

    // Privátní konstruktor zabrání vytvoření instance přes 'new' zvenčí
    private DatabaseManager() {
        System.out.println("Inicializuji připojení k databázím (může to chvíli trvat)...");
        
        // Názvy musí přesně odpovídat těm v tvém persistence.xml
        this.srcEmf = Persistence.createEntityManagerFactory("src_PU");
        this.dstEmf = Persistence.createEntityManagerFactory("dst_PU");
        
        System.out.println("Připojení k databázím bylo úspěšně navázáno.");
    }

    /**
     * Získá hlavní (a jedinou) instanci DatabaseManageru.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Vytvoří nový EntityManager pro čtení ze STARÉ databáze (MySQL).
     * Nezapomeň ho po použití zavřít (em.close())!
     */
    public EntityManager getSourceEntityManager() {
        return srcEmf.createEntityManager();
    }

    /**
     * Vytvoří nový EntityManager pro zápis do NOVÉ databáze (MariaDB).
     * Nezapomeň ho po použití zavřít (em.close())!
     */
    public EntityManager getDestinationEntityManager() {
        return dstEmf.createEntityManager();
    }

    /**
     * Korektně uzavře továrny při ukončení celé aplikace.
     */
    public void close() {
        System.out.println("Uzavírám spojení s databázemi...");
        if (srcEmf != null && srcEmf.isOpen()) {
            srcEmf.close();
        }
        if (dstEmf != null && dstEmf.isOpen()) {
            dstEmf.close();
        }
    }
}
