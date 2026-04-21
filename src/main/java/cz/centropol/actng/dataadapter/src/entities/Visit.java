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
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@Entity(name = "SRCVisit")
@Table(name = "t_visit", indexes = {@Index(columnList = "visit_id")})
public class Visit implements Serializable
{    
    @Id
    @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)
    @Column(name = "id")
    private Integer dbid;

    public Integer getDbid()
    {
        return dbid;
    }

    public void setDbid(Integer dbid)
    {
        this.dbid = dbid;
    }

    @Column(name = "visit_id", nullable = false, length = 64)
    private String visit_id = UUID.randomUUID().toString().replace("-", "").toUpperCase();

    /**
     * Get the value of visit_id
     *
     * @return the value of visit_id
     */
    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = true)
    private Date creationTime = Calendar.getInstance().getTime();

    /**
     * Get the value of creationTime
     *
     * @return the value of creationTime
     */
    public Date getCreationTime()
    {
        return creationTime;
    }

    /**
     * Set the value of creationTime
     *
     * @param creationTime new value of creationTime
     */
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    @Column(name = "subject", nullable = false, length = 255)
    private String subject = null;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Set the value of name
     *
     * @param subject new value of name
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    @Column(name = "note", length = 4096, nullable = true)
    private String note = null;

    /**
     * Get the value of note
     *
     * @return the value of note
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Set the value of note
     *
     * @param note new value of note
     */
    public void setNote(String note)
    {
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

    @OneToMany(fetch = FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
    @JoinTable(name="t_visit_url", joinColumns={@javax.persistence.JoinColumn(name="visit_fk")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="url_fk")})
    private List<Url> url;

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public List<Url> getUrl()
    {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(List<Url> url)
    {
        this.url = url;
    }

    @OneToMany(cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true, fetch=FetchType.EAGER)
    @JoinTable(name="t_visit_kvpair", joinColumns={@javax.persistence.JoinColumn(name="visit_fk")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="kvpair_fk")})
    private List<KVPair> kvPairs;

    public List<KVPair> getKvPairs()
    {
        return kvPairs;
    }

    public void setKvPairs(List<KVPair> kvPairs)
    {
        this.kvPairs = kvPairs;
    }

    @Version
    @Column(name = "version")
    private Integer version;

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.dbid);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Visit other = (Visit) obj;
        if (!Objects.equals(this.dbid, other.dbid))
        {
            return false;
        }
        return true;
    }
}
