package cz.centropol.actng.dataadapter.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigrationContext {

    private int totalRecords = 0;
    private int successfulRecords = 0;
    private int skippedRecords = 0;
    private int failedRecords = 0;

    private final List<MigrationError> errors = new ArrayList<>();
    private final String entityName;
    private final long startTime;

    public MigrationContext(String entityName) {
        this.entityName = entityName;
        this.startTime = System.currentTimeMillis();
    }

    // --- Metody pro inkrementaci statistik ---

    public void incrementTotal() {
        this.totalRecords++;
    }

    public void incrementSuccess() {
        this.successfulRecords++;
    }

    public void incrementSkipped() {
        this.skippedRecords++;
    }

    public void addError(String entityId, String phase, String errorMessage) {
        this.failedRecords++;
        this.errors.add(new MigrationError(entityId, phase, errorMessage));
    }

    // --- Výstupní metody ---

    public void printConsoleSummary() {
        long durationSeconds = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("\n=== SHRNUTÍ MIGRACE (" + entityName + ") ===");
        System.out.println("Čas běhu: " + durationSeconds + " s");
        System.out.println("Celkem ke zpracování: " + totalRecords);
        System.out.println("Úspěšně zmigrováno:   " + successfulRecords);
        System.out.println("Přeskočeno (beze změny): " + skippedRecords);
        System.out.println("Chyby:                " + failedRecords);
    }

    public void exportReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String filename = "migration_report_" + entityName + "_" + sdf.format(new Date()) + ".txt";

        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("=== REPORT MIGRACE: " + entityName + " ===");
            out.println("Datum generování: " + new Date());
            out.println("Čas běhu: " + ((System.currentTimeMillis() - startTime) / 1000) + " s");
            out.println("----------------------------------------");
            out.println("Celkem ke zpracování: " + totalRecords);
            out.println("Úspěšně zmigrováno:   " + successfulRecords);
            out.println("Přeskočeno:           " + skippedRecords);
            out.println("Chyby:                " + failedRecords);
            out.println("----------------------------------------");
            
            if (!errors.isEmpty()) {
                out.println("\n=== DETAIL CHYB ===");
                for (MigrationError error : errors) {
                    out.println(error.toString());
                }
            }
            
            System.out.println("\nDetailní report byl uložen do souboru: " + filename);
            
        } catch (IOException e) {
            System.err.println("Nepodařilo se uložit soubor s reportem! Chyba: " + e.getMessage());
        }
    }
}
