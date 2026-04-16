/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.centropol.actng.dataadapter.core;

/**
 *
 * @author krob
 */
public interface MigrationJob {
    /**
     * Spustí migrační proces pro danou entitu.
     * * @param config Konfigurace zadaná uživatelem z příkazové řádky.
     * @param config
     */
    void run(MigrationConfig config);
}
