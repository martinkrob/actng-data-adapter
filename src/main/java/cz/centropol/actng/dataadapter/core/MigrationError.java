package cz.centropol.actng.dataadapter.core;

public class MigrationError {
    
    private final String entityId;
    private final String phase;
    private final String errorMessage;

    public MigrationError(String entityId, String phase, String errorMessage) {
        this.entityId = entityId;
        this.phase = phase;
        this.errorMessage = errorMessage;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getPhase() {
        return phase;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Fáze: %s | Chyba: %s", entityId, phase, errorMessage);
    }
}
