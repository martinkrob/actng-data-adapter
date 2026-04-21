/*
 * Copyright (C) 2020 krob
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
package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "SRCInteraction")
@Table(name = "t_interaction", indexes = {@Index(columnList = "parent_id"), @Index(columnList = "start_time"), @Index(columnList = "agent_id"), @Index(columnList = "customer_no") ,@Index(columnList = "start_time,agent_id")})
public class Interaction implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Column(name = "id")
    private Integer dbid;

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    @Column(name = "interaction_id", nullable = false, length = 255, unique = true)
    private String interactionId;

    /**
     * Get the value of interactionId
     *
     * @return the value of interactionId
     */
    public String getInteractionId() {
        return interactionId;
    }

    /**
     * Set the value of interactionId
     *
     * @param interactionId new value of interactionId
     */
    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    @Column(name = "parent_id", nullable = true, length = 255)
    private String parentId;

    /**
     * Get the value of parentId
     *
     * @return the value of parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Set the value of parentId
     *
     * @param parentId new value of parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Transient
    private Integer colorCode = null;

    /**
     * Get the value of colorCode
     *
     * @return the value of colorCode
     */
    public Integer getColorCode() {
        return colorCode;
    }

    /**
     * Set the value of colorCode
     *
     * @param colorCode new value of colorCode
     */
    public void setColorCode(Integer colorCode) {
        this.colorCode = colorCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date startTime = Calendar.getInstance().getTime();

    /**
     * Get the value of startTime
     *
     * @return the value of startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Set the value of startTime
     *
     * @param startTime new value of startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = true)
    private Date endTime;

    /**
     * Get the value of endTime
     *
     * @return the value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Set the value of endTime
     *
     * @param endTime new value of endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "phone_call_fk", nullable = true)
    private PhoneCall phoneCall = null;

    /**
     * Get the value of phoneCall
     *
     * @return the value of phoneCall
     */
    public PhoneCall getPhoneCall() {
        return phoneCall;
    }

    /**
     * Set the value of phoneCall
     *
     * @param phoneCall new value of phoneCall
     */
    public void setPhoneCall(PhoneCall phoneCall) {
        this.phoneCall = phoneCall;
    }    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_task_fk", nullable = true)
    private Task task = null;

    /**
     * Get the value of parentTask
     *
     * @return the value of parentTask
     */
    public Task getTask() {
        return task;
    }

    /**
     * Set the value of parentTask
     *
     * @param task new value of parentTask
     */
    public void setTask(Task task) {
        this.task = task;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "visit_fk", nullable = true)
    private Visit visit = null;

    /**
     * Get the value of visit
     *
     * @return the value of visit
     */
    public Visit getVisit() {
        return visit;
    }

    /**
     * Set the value of visit
     *
     * @param visit new value of visit
     */
    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    @Column(name = "agent_id", nullable = false, length = 255)
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

    @Column(name = "customer_name", nullable = true, length = 255)
    private String customerName;

    /**
     * Get the value of customerName
     *
     * @return the value of customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Set the value of customerName
     *
     * @param customerName new value of customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    // 6.3.2025 - Bordel na ESB je třeba aktivně využívat customerExternalID
    @Transient
    private long customerId = 0L;

    /**
     * Get the value of customerId
     *
     * @return the value of customerId
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Set the value of customerId
     *
     * @param customerId new value of customerId
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
    @Column(name = "crm_activity_url", nullable = true, length = 255)    
    private String crmActivityUrl = null;

    /**
     * Get the value of crmActivityGuid
     *
     * @return the value of crmActivityGuid
     */
    public String getCrmActivityUrl() {
        return crmActivityUrl;
    }

    /**
     * Set the value of crmActivityGuid
     *
     * @param crmActivityUrl new value of crmActivityGuid
     */
    public void setCrmActivityUrl(String crmActivityUrl) {
        this.crmActivityUrl = crmActivityUrl;
    }

    @Lob
    @Column(name = "voicebot_data", nullable = true)
    private String voicebotData = null;

    /**
     * Get the value of voicebotData
     *
     * @return the value of voicebotData
     */
    public String getVoicebotData() {
        return voicebotData;
    }

    /**
     * Set the value of voicebotData
     *
     * @param voicebotData new value of voicebotData
     */
    public void setVoicebotData(String voicebotData) {
        this.voicebotData = voicebotData;
    }
        
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SRCInteractionType type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public SRCInteractionType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(SRCInteractionType type) {
        this.type = type;
    }

    @Transient
    private final List<Url> activityUrl = new ArrayList<>();

    public List<Url> getActivityUrl() {
        return activityUrl;
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

}
