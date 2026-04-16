package cz.centropol.actng.dataadapter;

import cz.centropol.actng.dataadapter.core.DatabaseManager;
import cz.centropol.actng.dataadapter.core.MigrationConfig;
import cz.centropol.actng.dataadapter.core.MigrationJob;
import cz.centropol.actng.dataadapter.jobs.TaskMigrationJob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static void main(String[] args) {
        System.out.println("=== ActNG DataAdapter ===");

        try {
            MigrationConfig config = parseArguments(args);
            System.out.println("Spouštím migraci s parametry: " + config);
                        
            MigrationJob job;
            
            switch (config.getTargetEntity()) {                                 // Výběr správného jobu podle parametru --entity
                case TASK:
                    job = new TaskMigrationJob();
                    break;
                case INTERACTION:
                    // job = new InteractionMigrationJob(); // Tady si to později odkomentuješ
                    throw new UnsupportedOperationException("Migrace pro INTERACTION zatím není implementována.");
                default:
                    throw new IllegalArgumentException("Neznámá entita: " + config.getTargetEntity());
            }

            // Spuštění vybrané migrace
            job.run(config);
            
            
        } catch (IllegalArgumentException | ParseException e) {            
            System.err.println("Chyba parametrizace: " + e.getMessage());
            printHelp();
            System.exit(1);
            
        } finally {            
            DatabaseManager.getInstance().close();                              // Vždy na konci programu uklidíme databázová spojení!
        }
    }

    private static MigrationConfig parseArguments(String[] args) throws ParseException {
        String entityStr = null;
        String startStr = null;
        String endStr = null;
        String statusStr = null;

        for (String arg : args) {
            if (arg.startsWith("--entity=")) {
                entityStr = arg.substring(9).toUpperCase();
            } else if (arg.startsWith("--start=")) {
                startStr = arg.substring(8);
            } else if (arg.startsWith("--end=")) {
                endStr = arg.substring(6);
            } else if (arg.startsWith("--status=")) {
                statusStr = arg.substring(9);
            }
        }

        if (entityStr == null || startStr == null || endStr == null) {
            throw new IllegalArgumentException("Chybí povinné parametry (--entity, --start, --end)!");
        }

        MigrationConfig.EntityType targetEntity = MigrationConfig.EntityType.valueOf(entityStr);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date startDate = sdf.parse(startStr);
        Date endDate = sdf.parse(endStr);
        endDate.setTime(endDate.getTime() + (24 * 60 * 60 * 1000) - 1);

        List<String> statuses = new ArrayList<>();
        if (statusStr != null && !statusStr.isEmpty()) {
            statuses = Arrays.asList(statusStr.split(","));
        }

        return new MigrationConfig(targetEntity, startDate, endDate, statuses);
    }

    private static void printHelp() {
        System.out.println("\nPoužití:");
        System.out.println("java -jar DataAdapter.jar --entity=ENTITA --start=YYYY-MM-DD --end=YYYY-MM-DD [--status=STATUS1,STATUS2]");
        System.out.println("\nPříklad:");
        System.out.println("java -jar DataAdapter.jar --entity=TASK --start=2024-01-01 --end=2024-01-31 --status=FINISHED,CANCELED");
        System.out.println("java -jar DataAdapter.jar --entity=INTERACTION --start=2024-01-01 --end=2024-01-31");
    }
}
