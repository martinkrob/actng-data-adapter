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
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "SRCMonitorFile")
@Table(name = "t_monitorfile", indexes = {
    @Index(columnList = "start_time"),
    @Index(columnList = "unique_id"),
    @Index(columnList = "agent_id"),
    @Index(columnList = "start_time,agent_id")})
public class MonitorFile implements Serializable {

    public static final String FIND_BY_UNIQUEID = "MonitorFile.FIND_BY_UNIQUEID";
    public static final String FIND_BY_AGENTID = "MonitorFile.FIND_BY_AGENTID";
    public static final String FIND_BY_TIME = "MonitorFile.FIND_BY_TIME";

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")    
    private Date startTime;

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
    @Column(name = "answer_time", nullable = true)    
    private Date answerTime;

    /**
     * Get the value of answerTime
     *
     * @return the value of answerTime
     */
    public Date getAnswerTime() {
        return answerTime;
    }

    /**
     * Set the value of answerTime
     *
     * @param answerTime new value of answerTime
     */
    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    @Column(name = "duration", nullable = false)
    private Long durationInMillis = 0L;

    /**
     * Get the value of duration
     *
     * @return the value of duration
     */
    public Long getDurationInMillis() {
        return durationInMillis;
    }

    /**
     * Set the value of duration
     *
     * @param duration new value of duration
     */
    public void setDurationInMillis(Long duration) {
        this.durationInMillis = duration;
    }

    @Column(name = "billable_sec", nullable = false)
    private Integer billableSeconds = 0;

    /**
     * Get the value of billableSeconds
     *
     * @return the value of billableSeconds
     */
    public Integer getBillableSeconds() {
        return billableSeconds;
    }

    /**
     * Set the value of billableSeconds
     *
     * @param billableSeconds new value of billableSeconds
     */
    public void setBillableSeconds(Integer billableSeconds) {
        this.billableSeconds = billableSeconds;
    }

    @Column(name = "unique_id", length = 64, nullable = false)    
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

    @Column(name = "agent_id", length = 64, nullable = true)    
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

    @Column(name = "channel", length = 255, nullable = true)    
    private String channel;

    /**
     * Get the value of channel
     *
     * @return the value of channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Set the value of channel
     *
     * @param channel new value of channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Column(name = "dcontext")
    private String dstContext;

    /**
     * Get the value of dstContext
     *
     * @return the value of dstContext
     */
    public String getDstContext() {
        return dstContext;
    }

    /**
     * Set the value of dstContext
     *
     * @param dstContext new value of dstContext
     */
    public void setDstContext(String dstContext) {
        this.dstContext = dstContext;
    }

    @Column(name = "linked_id", nullable = true, length = 64)
    private String linkedId;

    /**
     * Get the value of linkedId
     *
     * @return the value of linkedId
     */
    public String getLinkedId() {
        return linkedId;
    }

    /**
     * Set the value of linkedId
     *
     * @param linkedId new value of linkedId
     */
    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    @Column(name = "accountcode", nullable = true, length = 128)
    private String accountCode;

    /**
     * Get the value of accountCode
     *
     * @return the value of accountCode
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * Set the value of accountCode
     *
     * @param accountCode new value of accountCode
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Column(name = "userfield", nullable = true, length = 255)
    private String userField;

    /**
     * Get the value of userField
     *
     * @return the value of userField
     */
    public String getUserField() {
        return userField;
    }

    /**
     * Set the value of userField
     *
     * @param userField new value of userField
     */
    public void setUserField(String userField) {
        this.userField = userField;
    }

    @Column(name = "src", nullable = true, length = 32)
    private String src;

    /**
     * Get the value of src
     *
     * @return the value of src
     */
    public String getSrc() {
        return src;
    }

    /**
     * Set the value of src
     *
     * @param src new value of src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    @Column(name = "dst", nullable = true, length = 32)
    private String dst;

    /**
     * Get the value of dst
     *
     * @return the value of dst
     */
    public String getDst() {
        return dst;
    }

    /**
     * Set the value of dst
     *
     * @param dst new value of dst
     */
    public void setDst(String dst) {
        this.dst = dst;
    }

    @Column(name = "last_app", nullable = true, length = 255)
    private String lastApp;

    /**
     * Get the value of lastApp
     *
     * @return the value of lastApp
     */
    public String getLastApp() {
        return lastApp;
    }

    /**
     * Set the value of lastApp
     *
     * @param lastApp new value of lastApp
     */
    public void setLastApp(String lastApp) {
        this.lastApp = lastApp;
    }

    @Column(name = "last_data", nullable = true, length = 255)
    private String lastData;

    /**
     * Get the value of lastData
     *
     * @return the value of lastData
     */
    public String getLastData() {
        return lastData;
    }

    /**
     * Set the value of lastData
     *
     * @param lastData new value of lastData
     */
    public void setLastData(String lastData) {
        this.lastData = lastData;
    }    

    @Column(name = "note", length = 1024, nullable = true)
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "audio_attachment_fk", nullable = true)
    private Attachment audioRecord = null;

    /**
     * Get the value of audioRecord
     *
     * @return the value of audioRecord
     */
    public Attachment getAudioRecord() {
        return audioRecord;
    }

    /**
     * Set the value of audioRecord
     *
     * @param audioRecord new value of audioRecord
     */
    public void setAudioRecord(Attachment audioRecord) {
        this.audioRecord = audioRecord;
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
    
    @Deprecated
    @Column(name = "content_fk", nullable = false)
    private Integer contentId = -1;

    /**
     * Get the value of contentId
     *
     * @return the value of contentId
     */
    public Integer getContentId() {
        return contentId;
    }

    /**
     * Set the value of contentId
     *
     * @param contentId new value of contentId
     */
    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Deprecated
    @Column(name = "content_size", nullable = false)
    private int size = 0;

    /**
     * Get the value of size
     *
     * @return the value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the value of size
     *
     * @param size new value of size
     */
    public void setSize(int size) {
        this.size = size;
    }

    @Deprecated
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

    @Deprecated
    @Column(name = "archive_path", nullable = true, length = 255)
    private String archivePath;

    /**
     * Get the value of archivePath
     *
     * @return the value of archivePath
     */
    public String getArchivePath() {
        return archivePath;
    }

    /**
     * Set the value of archivePath
     *
     * @param archivePath new value of archivePath
     */
    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
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
