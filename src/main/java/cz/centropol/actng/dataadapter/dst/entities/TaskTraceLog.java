/*
 * Copyright (C) 2019 krob
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "DSTTaskTraceLog")
@Table(name = "t_task_trace_log")
public class TaskTraceLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")    
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    @ManyToOne
    @JoinColumn(name = "attempt_fk", nullable = false)   
    private TaskAttempt attempt;

    public TaskAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(TaskAttempt attempt) {
        this.attempt = attempt;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_time", nullable = false)    
    private Date eventTime;

    /**
     * Get the value of eventTime
     *
     * @return the value of eventTime
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * Set the value of eventTime
     *
     * @param eventTime new value of eventTime
     */
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = true)    
    private DSTTaskEventType eventType;

    /**
     * Get the value of eventType
     *
     * @return the value of eventType
     */
    public DSTTaskEventType getEventType() {
        return eventType;
    }

    /**
     * Set the value of eventType
     *
     * @param eventType new value of eventType
     */
    public void setEventType(DSTTaskEventType eventType) {
        this.eventType = eventType;
    }

    @Column(name = "orig_reason", nullable = true)    
    private Integer origReason;

    /**
     * Get the value of origReason
     *
     * @return the value of origReason
     */
    public Integer getOrigReason() {
        return origReason;
    }

    /**
     * Set the value of origReason
     *
     * @param origReason new value of origReason
     */
    public void setOrigReason(Integer origReason) {
        this.origReason = origReason;
    }

    @Column(name = "orig_reason_desc", nullable = true, length = 1024)    
    private String origReasonDesc;

    /**
     * Get the value of origReasonDesc
     *
     * @return the value of origReasonDesc
     */
    public String getOrigReasonDesc() {
        return origReasonDesc;
    }

    /**
     * Set the value of origReasonDesc
     *
     * @param origReasonDesc new value of origReasonDesc
     */
    public void setOrigReasonDesc(String origReasonDesc) {
        this.origReasonDesc = origReasonDesc;
    }

    @Column(name = "unique_id", nullable = true, length = 128)    
    private String uniqueId;

    /**
     * Get the value of uniqueId
     *
     * @return the value of uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Set the value of uniqueId
     *
     * @param uniqueId new value of uniqueId
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Column(name = "queue", nullable = true, length = 64)    
    private String queue;

    /**
     * Get the value of queue
     *
     * @return the value of queue
     */
    public String getQueue() {
        return queue;
    }

    /**
     * Set the value of queue
     *
     * @param queue new value of queue
     */
    public void setQueue(String queue) {
        this.queue = queue;
    }

    @Column(name = "agent_id", nullable = true, length = 128)    
    private String agentId;

    /**
     * Get the value of agentId
     *
     * @return the value of agentId
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Set the value of agentId
     *
     * @param agentId new value of agentId
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    
    @Column(name = "process_code", nullable = true, length = 64)
    private String processCode = null;

    /**
     * Get the value of processCode
     *
     * @return the value of processCode
     */
    public String getProcessCode() {
        return processCode;
    }

    /**
     * Set the value of processCode
     *
     * @param processCode new value of processCode
     */
    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    @Column(name = "data1", nullable = true, length = 1024)
    private String data1;

    /**
     * Get the value of data1
     *
     * @return the value of data1
     */
    public String getData1() {
        return data1;
    }

    /**
     * Set the value of data1
     *
     * @param data1 new value of data1
     */
    public void setData1(String data1) {
        this.data1 = data1;
    }

    @Column(name = "data2", nullable = true, length = 1024)
    private String data2;

    /**
     * Get the value of data2
     *
     * @return the value of data2
     */
    public String getData2() {
        return data2;
    }

    /**
     * Set the value of data2
     *
     * @param data2 new value of data2
     */
    public void setData2(String data2) {
        this.data2 = data2;
    }

    @Column(name = "data3", nullable = true, length = 1024)
    private String data3;

    /**
     * Get the value of data3
     *
     * @return the value of data3
     */
    public String getData3() {
        return data3;
    }

    /**
     * Set the value of data3
     *
     * @param data3 new value of data3
     */
    public void setData3(String data3) {
        this.data3 = data3;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final TaskTraceLog other = (TaskTraceLog) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
