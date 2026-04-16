/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "SRCTask")
@Table(name = "t_task", indexes = {@Index(columnList = "status"), @Index(columnList = "stack_fk"), @Index(columnList = "agent_id"), @Index(columnList = "customer_no"), @Index(columnList = "agent_id,status"), @Index(columnList = "cowork_owner_fk"), 
    @Index(columnList = "last_attempt_time"), @Index(columnList = "creation_time,ext_guid")})

public class Task implements Serializable {
    
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "customer_no", nullable = true, length = 64)
    private String customerNo;

    /**
     * Get the value of customerNo
     *
     * @return the value of customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * Set the value of customerNo
     *
     * @param customerNo new value of customerNo
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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
    private SRCTaskType type = SRCTaskType.GENERAL;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public SRCTaskType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(SRCTaskType type) {
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
    private SRCTaskStatusType status = SRCTaskStatusType.ACTIVE;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public SRCTaskStatusType getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(SRCTaskStatusType status) {
        this.status = status;
    }

    @Column(name = "note", length = 4096, nullable = true)
    private String note;

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

    @Column(name = "process_desc", length = 255, nullable = true)
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

    @OneToMany(fetch = FetchType.EAGER, cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "t_task_url", joinColumns = {@javax.persistence.JoinColumn(name = "task_fk")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "url_fk")})
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

    @Column(name = "ext_guid", nullable = true, length = 255)
    private String extGuid;

    /**
     * Get the value of extGuid
     *
     * @return the value of extGuid
     */
    public String getExtGuid() {
        return extGuid;
    }

    /**
     * Set the value of extGuid
     *
     * @param extGuid new value of extGuid
     */
    public void setExtGuid(String extGuid) {
        this.extGuid = extGuid;
    }    

    @Column(name = "phone", nullable = true, length = 32)
    private String phoneNumber;

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
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "expiry_date", nullable = true)
    private Date expiryDate;

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
    private Date lastAttemptTime;

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

    @Column(name = "reuse_last_callerid", nullable = false)
    private boolean reuseLastCallerId = false;

    /**
     * Get the value of reuseLastCallerId
     *
     * @return the value of reuseLastCallerId
     */
    public boolean isReuseLastCallerId() {
        return reuseLastCallerId;
    }

    /**
     * Set the value of reuseLastCallerId
     *
     * @param reuseLastCallerId new value of reuseLastCallerId
     */
    public void setReuseLastCallerId(boolean reuseLastCallerId) {
        this.reuseLastCallerId = reuseLastCallerId;
    }

    @Column(name = "last_callerid", length = 16, nullable = true)
    private String lastCallerId = null;

    /**
     * Get the value of lastCallerId
     *
     * @return the value of lastCallerId
     */
    public String getLastCallerId() {
        return lastCallerId;
    }

    /**
     * Set the value of lastCallerId
     *
     * @param lastCallerId new value of lastCallerId
     */
    public void setLastCallerId(String lastCallerId) {
        this.lastCallerId = lastCallerId;
    }    

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_attempt_time")
    private Date nextAttemptTime;

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

    @Column(name = "agent_id", nullable = true, length = 255)
    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    @Column(name = "processed_by", nullable = true, length = 128)
    private String processedBy;

    /**
     * Get the value of processedBy
     *
     * @return the value of processedBy
     */
    public String getProcessedBy() {
        return processedBy;
    }

    /**
     * Set the value of processedBy
     *
     * @param processedBy new value of processedBy
     */
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    @OneToMany(cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "t_task_kvpair", joinColumns = {
        @javax.persistence.JoinColumn(name = "task_fk")}, inverseJoinColumns = {
        @javax.persistence.JoinColumn(name = "kvpair_fk")})
    private List<KVPair> kvPairs;

    public List<KVPair> getKvPairs() {
        return kvPairs;
    }

    public void setKvPairs(List<KVPair> kvPairs) {
        this.kvPairs = kvPairs;
    }

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

    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    /**
     * Get the value of archived
     *
     * @return the value of archived
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Set the value of archived
     *
     * @param archived new value of archived
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }        
    
    @Version
    @Column(name = "version")
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
