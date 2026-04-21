/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "DSTPersonalVisit")
@Table(name = "t_personal_visit", indexes = {@Index(columnList = "visit_id"), @Index(columnList = "ext_cust_id")})
public class PersonalVisit implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer dbid;

    public Integer getDbid() {
        return dbid;
    }

    public void setDbid(Integer dbid) {
        this.dbid = dbid;
    }

    @Column(name = "visit_id", nullable = false, length = 64)
    private String visitID = UUID.randomUUID().toString().replace("-", "").toUpperCase();

    /**
     * Get the value of visitID
     *
     * @return the value of visitID
     */
    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
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

    @Column(name = "subject", nullable = false, length = 255)
    private String subject = null;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the value of name
     *
     * @param subject new value of name
     */
    public void setSubject(String subject) {
        this.subject = subject;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "video_attachment_fk", nullable = true)
    private Attachment videoRecord = null;

    /**
     * Get the value of videoRecord
     *
     * @return the value of videoRecord
     */
    public Attachment getVideoRecord() {
        return videoRecord;
    }

    /**
     * Set the value of videoRecord
     *
     * @param videoRecord new value of videoRecord
     */
    public void setVideoRecord(Attachment videoRecord) {
        this.videoRecord = videoRecord;
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
    
    @Column(name = "customer_no", nullable = true)
    private String customerNo = null;

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
    
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "t_personal_visit_kvpair", joinColumns = {@JoinColumn(name = "ccvisit_fk")}, inverseJoinColumns = {@JoinColumn(name = "kvpair_fk")})
    private List<KVPair> kvPairs;

    public List<KVPair> getKvPairs() {
        return kvPairs;
    }

    public void setKvPairs(List<KVPair> kvPairs) {
        this.kvPairs = kvPairs;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "t_personal_visit_url", joinColumns = {@JoinColumn(name = "visit_fk")}, inverseJoinColumns = {@JoinColumn(name = "url_fk")})
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
        hash = 37 * hash + Objects.hashCode(this.dbid);
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
        final PersonalVisit other = (PersonalVisit) obj;
        return Objects.equals(this.dbid, other.dbid);
    }
    
}
