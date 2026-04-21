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
package cz.centropol.actng.dataadapter.dst.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 *
 * @author krob
 */
@Entity(name = "DSTInteraction")
@Table(name = "t_interaction", indexes = {@Index(columnList = "parent_interaction_id"), @Index(columnList = "start_time"), @Index(columnList = "agent_id"), @Index(columnList = "start_time,agent_id")})

public class Interaction implements Serializable, VersionedEntity {

    public static final String FIND_BY_GUID = "Interaction.findByGUID";
    public static final String FIND_BY_STARTTIME = "Interaction.findByStartTime";
    public static final String FIND_BY_AGENT = "Interaction.findByStartTimeAndAgent";
    public static final String FIND_BY_TYPE = "Interaction.findByStartTimeAndType";
    public static final String FIND_BY_AGENT_AND_TYPE = "Interaction.findByStartTimeAndAgentAndType";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dbid")
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    @Column(name = "interaction_id", nullable = false, length = 64, unique = true)
    private String interactionID = UUID.randomUUID().toString().replace("-", "").toUpperCase();;

    /**
     * Get the value of interactionID
     *
     * @return the value of interactionID
     */
    public String getInteractionID() {
        return interactionID;
    }

    /**
     * Set the value of interactionID
     *
     * @param interactionID new value of interactionID
     */
    public void setInteractionID(String interactionID) {
        this.interactionID = interactionID;
    }

    @Column(name = "parent_interaction_id", nullable = true, length = 255)
    private String parentID;

    /**
     * Get the value of parentID
     *
     * @return the value of parentID
     */
    public String getParentID() {
        return parentID;
    }

    /**
     * Set the value of parentID
     *
     * @param parentID new value of parentID
     */
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DSTInteractionType type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public DSTInteractionType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(DSTInteractionType type) {
        this.type = type;
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
    @JoinColumn(name = "phonecall_fk", nullable = true)
    private PhoneCall call = null;

    /**
     * Get the value of call
     *
     * @return the value of call
     */
    public PhoneCall getCall() {
        return call;
    }

    /**
     * Set the value of call
     *
     * @param call new value of call
     */
    public void setCall(PhoneCall call) {
        this.call = call;
    }
        
    @Column(name = "task_fk", nullable = true)
    private Integer taskFk = null;

    /**
     * Get the value of parentTask
     *
     * @return the value of parentTask
     */
    public Integer getTaskFk() {
        return taskFk;
    }

    /**
     * Set the value of parentTask
     *
     * @param taskFk new value of parentTask
     */
    public void setTaskFk(Integer taskFk) {
        this.taskFk = taskFk;
    }        
    
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "visit_fk", nullable = true)
    private PersonalVisit visit = null;

    /**
     * Get the value of visit
     *
     * @return the value of visit
     */
    public PersonalVisit getVisit() {
        return visit;
    }

    /**
     * Set the value of visit
     *
     * @param visit new value of visit
     */
    public void setVisit(PersonalVisit visit) {
        this.visit = visit;
    }

    @Column(name = "agent_id", nullable = false, length = 255)
    private String agentID;

    /**
     * Get the value of agentID
     *
     * @return the value of agentID
     */
    public String getAgentID() {
        return agentID;
    }

    /**
     * Set the value of agentID
     *
     * @param agentID new value of agentID
     */
    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
    
    @Column(name = "note", nullable = true, length = 1024)
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
}
