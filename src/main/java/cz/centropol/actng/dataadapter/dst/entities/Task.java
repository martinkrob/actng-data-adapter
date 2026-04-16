/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import cz.centropol.actng.dataadapter.src.entities.TaskTraceLog;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author krob     SELECT e FROM Task e WHERE e.stack.id = :stackid AND e.archived = false
 */
@Entity(name = "DSTTask")
@Table(name = "t_task", indexes = {
    @Index(name = "idx_task_distribution", columnList = "stack_fk, status, flag_archived, reserved, next_attempt_time, prio"),
    @Index(columnList = "status"), @Index(columnList = "stack_fk"), 
    @Index(columnList = "last_solver"), 
    @Index(columnList = "ext_cust_id"), 
    @Index(columnList = "reserved_for,status"), 
    @Index(columnList = "last_attempt_time"), 
    @Index(columnList = "creation_time,ext_guid")})

public class Task implements Serializable, VersionedEntity {    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "integration_id", nullable = true)    
    private Integer integrationId = null;

    public Integer getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }

    @Version
    @Column(name = "version")
    private Integer version;

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
          
    @Column(name = "ext_guid", nullable = true, length = 255)
    private String extGUID = null;

    /**
     * Get the value of extGUID
     *
     * @return the value of extGUID
     */
    public String getExtGUID() {
        return extGUID;
    }

    /**
     * Set the value of extGUID
     *
     * @param extGUID new value of extGUID
     */
    public void setExtGUID(String extGUID) {
        this.extGUID = extGUID;
    }

    @Column(name = "ext_cust_id", nullable = true)
    private Integer extCustID = null;

    /**
     * Get the value of extCustID
     *
     * @return the value of extCustID
     */
    public Integer getExtCustID() {
        return extCustID;
    }

    /**
     * Set the value of extCustID
     *
     * @param extCustID new value of extCustID
     */
    public void setExtCustID(Integer extCustID) {
        this.extCustID = extCustID;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = true)
    private Date creationTime = Calendar.getInstance().getTime();

    /**
     * Get the value of creationTime
     *
     * @return the value of creationTime
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * Set the value of creationTime
     *
     * @param creationTime new value of creationTime
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", nullable = false)
    private Date modificationTime;

    /**
     * Get the value of modificationTime
     *
     * @return the value of modificationTime
     */
    public Date getModificationTime() {
        return modificationTime;
    }

    /**
     * Set the value of modificationTime
     *
     * @param modificationTime new value of modificationTime
     */
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    @Column(name = "stack_fk", nullable = false)
    private Integer stackId;

    /**
     * Get the value of stackId (ID zdrojového ETM zásobníku)
     *
     * @return the value of stackId
     */
    public Integer getStackId() {
        return stackId;
    }

    /**
     * Set the value of stackId
     *
     * @param stackId new value of stackId
     */
    public void setStackId(Integer stackId) {
        this.stackId = stackId;
    }  
    
    @Column(name = "previous_stack_fk", nullable = false)
    private Integer previousStackId;

    public Integer getPreviousStackId() {
        return previousStackId;
    }

    public void setPreviousStackId(Integer previousStackId) {
        this.previousStackId = previousStackId;
    }
            
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DSTTaskType type = DSTTaskType.GENERAL;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public DSTTaskType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(DSTTaskType type) {
        this.type = type;
    }

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "prio", nullable = false)
    private Integer priority = 0;

    /**
     * Get the value of priority
     *
     * @return the value of priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Set the value of priority
     *
     * @param priority new value of priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DSTTaskStatusType status = DSTTaskStatusType.ACTIVE;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public DSTTaskStatusType getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(DSTTaskStatusType status) {
        this.status = status;
    }

    @Column(name = "note", length = 4096, nullable = true)
    private String note = null;

    /**
     * Get the value of note
     *
     * @return the value of note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set the value of note
     *
     * @param note new value of note
     */
    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "process_desc", length = 1024, nullable = true)
    private String processDesc;

    /**
     * Get the value of processDesc
     *
     * @return the value of processDesc
     */
    public String getProcessDesc() {
        return processDesc;
    }

    /**
     * Set the value of processDesc
     *
     * @param processDesc new value of processDesc
     */
    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "t_task_url", joinColumns = {@JoinColumn(name = "task_fk")}, inverseJoinColumns = {@JoinColumn(name = "url_fk")})
    private List<Url> url;

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public List<Url> getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(List<Url> url) {
        this.url = url;
    }        

    @Column(name = "phone", nullable = true, length = 32)
    private String phoneNumber = null;

    /**
     * Get the value of phoneNumber
     *
     * @return the value of phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the value of phoneNumber
     *
     * @param phoneNumber new value of phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "exp_process_last_applied_rule", nullable = true)
    private String lastAppliedExpProcRule = null;

    /**
     * Get the value of lastAppliedRule
     *
     * @return the value of lastAppliedRule
     */
    public String getLastAppliedExpProcRule() {
        return lastAppliedExpProcRule;
    }

    /**
     * Set the value of lastAppliedRule
     *
     * @param lastAppliedExpProcRule new value of lastAppliedRule
     */
    public void setLastAppliedExpProcRule(String lastAppliedExpProcRule) {
        this.lastAppliedExpProcRule = lastAppliedExpProcRule;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate = null;

    /**
     * Get the value of expiryDate
     *
     * @return the value of expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Set the value of expiryDate
     *
     * @param expiryDate new value of expiryDate
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Column(name = "attempt_number")
    private Integer attemptNumber = 0;

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_attempt_time")
    private Date lastAttemptTime = null;

    /**
     * Get the value of lastAttemptTime
     *
     * @return the value of lastAttemptTime
     */
    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    /**
     * Set the value of lastAttemptTime
     *
     * @param lastAttemptTime new value of lastAttemptTime
     */
    public void setLastAttemptTime(Date lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    @Column(name = "last_attempt_id", nullable = true)
    private Integer lastAttemptId = 0;

    /**
     * Get the value of lastAttemptId
     *
     * @return the value of lastAttemptId
     */
    public Integer getLastAttemptId() {
        return lastAttemptId;
    }

    /**
     * Set the value of lastAttemptId
     *
     * @param lastAttemptId new value of lastAttemptId
     */
    public void setLastAttemptId(Integer lastAttemptId) {
        this.lastAttemptId = lastAttemptId;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_attempt_time", nullable = false)
    private Date nextAttemptTime = new Date();

    /**
     * Get the value of nextAttemptTime
     *
     * @return the value of nextAttemptTime
     */
    public Date getNextAttemptTime() {
        return nextAttemptTime;
    }

    /**
     * Set the value of nextAttemptTime
     *
     * @param nextAttemptTime new value of nextAttemptTime
     */
    public void setNextAttemptTime(Date nextAttemptTime) {
        this.nextAttemptTime = nextAttemptTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notify_time", nullable = true)
    private Date notifyTime = null;

    /**
     * Get the value of notifyTime
     *
     * @return the value of notifyTime
     */
    public Date getNotifyTime() {
        return notifyTime;
    }

    /**
     * Set the value of notifyTime
     *
     * @param notifyTime new value of notifyTime
     */
    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }
    
    @Column(name = "reserved", nullable = false)
    private boolean reserved = false;

    /**
     * Get the value of reserved
     *
     * @return the value of reserved
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Set the value of reserved
     *
     * @param reserved new value of reserved
     */
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Column(name = "reserved_for", nullable = true, length = 128)
    private String reservedFor = null;

    public String getReservedFor() {
        return reservedFor;
    }

    public void setReservedFor(String reservedFor) {
        this.reservedFor = reservedFor;
    }
    
    @Column(name = "last_solver", nullable = true, length = 128)
    private String lastSolver = null;

    /**
     * Get the value of lastSolver
     *
     * @return the value of lastSolver
     */
    public String getLastSolver() {
        return lastSolver;
    }

    /**
     * Set the value of lastSolver
     *
     * @param lastSolver new value of lastSolver
     */
    public void setLastSolver(String lastSolver) {
        this.lastSolver = lastSolver;
    }

    @Column(name = "last_used_ident_pos", nullable = false)    
    private Integer lastUsedIdentityPos = 0;

    /**
     * Get the value of lastUsedIdentityPos
     *
     * @return the value of lastUsedIdentityPos
     */
    public Integer getLastUsedIdentityPos() {
        return lastUsedIdentityPos;
    }

    /**
     * Set the value of lastUsedIdentityPos
     *
     * @param lastUsedIdentityPos new value of lastUsedIdentityPos
     */
    public void setLastUsedIdentityPos(Integer lastUsedIdentityPos) {
        this.lastUsedIdentityPos = lastUsedIdentityPos;
    }
    
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "t_task_kvpair", joinColumns = {@JoinColumn(name = "task_fk")}, inverseJoinColumns = {@JoinColumn(name = "kvpair_fk")})
    private List<KVPair> kvPairs;

    public List<KVPair> getKvPairs() {
        return kvPairs;
    }

    public void setKvPairs(List<KVPair> kvPairs) {
        this.kvPairs = kvPairs;
    }
        
    @Column(name = "flag_archived", nullable = false)
    private boolean flagArchived = false;

    /**
     * Get the value of flagArchived
     *
     * @return the value of flagArchived
     */
    public boolean isFlagArchived() {
        return flagArchived;
    }

    /**
     * Set the value of flagArchived
     *
     * @param flagArchived new value of flagArchived
     */
    public void setFlagArchived(boolean flagArchived) {
        this.flagArchived = flagArchived;
    }

//    Zrušeno 23. 1. 2026 po zjištění, že přílohy nejsou absolutně vůbec využívány.    
//    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JoinTable(name = "t_task_attachement", joinColumns = {@JoinColumn(name = "task_fk")}, inverseJoinColumns = {@JoinColumn(name = "attachement_fk")})
//    private List<Attachment> attachments;
//
//    public List<Attachment> getAttachments() {
//        return attachments;
//    }
//
//    public void setAttachments(List<Attachment> attachments) {
//        this.attachments = attachments;
//    }

    @Transient
    private Integer attemptId;

    /**
     * Get the value of attemptId
     *
     * @return the value of attemptId
     */
    public Integer getAttemptId() {
        return attemptId;
    }

    /**
     * Set the value of attemptId
     *
     * @param attemptId new value of attemptId
     */
    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }
    
    @Transient
    private List<TaskTraceLog> traceLog;

    public List<TaskTraceLog> getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(List<TaskTraceLog> traceLog) {
        this.traceLog = traceLog;
    }    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) {return false;}
        final Task other = (Task) obj;
        return Objects.equals(this.id, other.id);
    }
}
