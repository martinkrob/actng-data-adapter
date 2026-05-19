package cz.centropol.actng.dataadapter.dst.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
@Table(name = "t_monitorfile")
public class MonitorFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "integration_id", nullable = true, unique = true)
    private Integer integrationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answer_time", nullable = true)
    private Date answerTime;

    @Column(name = "duration", nullable = false)
    private Integer durationInSec = 0;

    @Column(name = "billable_sec", nullable = false)
    private Integer billableSeconds = 0;

    @Column(name = "unique_id", length = 64, nullable = false)
    private String uniqueID;

    @Column(name = "linked_id", nullable = true, length = 64)
    private String linkedID;

    @Column(name = "is_human", nullable = false)
    private boolean human = true;

    @Column(name = "agent_id", length = 64, nullable = true)
    private String agentID;

    @Column(name = "queue", nullable = true, length = 64)
    private String queue;

    @Column(name = "channel", length = 255, nullable = true)
    private String channel;

    @Column(name = "dcontext")
    private String dstContext;

    @Column(name = "accountcode", nullable = true, length = 128)
    private String accountCode;

    @Column(name = "userfield", nullable = true, length = 255)
    private String userField;

    @Column(name = "src", nullable = true, length = 32)
    private String src;

    @Column(name = "dst", nullable = true, length = 32)
    private String dst;

    @Column(name = "last_app", nullable = true, length = 255)
    private String lastApp;

    @Column(name = "last_data", nullable = true, length = 255)
    private String lastData;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "audio_attachment_fk", nullable = true)
    private Attachment record = null;

    @Lob
    @Column(name = "transcript", nullable = true)
    private String transcript = null;

    @Lob
    @Column(name = "collected_data", nullable = true)
    private String collectedData = null;


    public Integer getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(Integer durationInSec) {
        this.durationInSec = durationInSec;
    }

    public Integer getBillableSeconds() {
        return billableSeconds;
    }

    public void setBillableSeconds(Integer billableSeconds) {
        this.billableSeconds = billableSeconds;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getLinkedID() {
        return linkedID;
    }

    public void setLinkedID(String linkedID) {
        this.linkedID = linkedID;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDstContext() {
        return dstContext;
    }

    public void setDstContext(String dstContext) {
        this.dstContext = dstContext;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getLastApp() {
        return lastApp;
    }

    public void setLastApp(String lastApp) {
        this.lastApp = lastApp;
    }

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public Attachment getRecord() {
        return record;
    }

    public void setRecord(Attachment record) {
        this.record = record;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getCollectedData() {
        return collectedData;
    }

    public void setCollectedData(String collectedData) {
        this.collectedData = collectedData;
    }
}
