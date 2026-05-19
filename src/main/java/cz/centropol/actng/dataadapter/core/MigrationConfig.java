/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.centropol.actng.dataadapter.core;

import java.util.Date;
import java.util.List;

public class MigrationConfig {
    
    // Nový výčet podporovaných entit
    public enum EntityType {
        TASK, INTERACTION, MONITORFILE, QM
    }

    private final EntityType targetEntity;
    private final Date startDate;
    private final Date endDate;
    private final List<String> statuses;                                        // Uchováme jako String, konkrétní Job si to přeloží

    public MigrationConfig(EntityType targetEntity, Date startDate, Date endDate, List<String> statuses) {
        this.targetEntity = targetEntity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statuses = statuses;
    }

    public EntityType getTargetEntity() {
        return targetEntity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    @Override
    public String toString() {
        return "MigrationConfig{" + "targetEntity=" + targetEntity + ", startDate=" + startDate + ", endDate=" + endDate + ", statuses=" + statuses + '}';
    }
}
