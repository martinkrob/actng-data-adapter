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
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "DSTPhoneCall")
@Table(name = "t_phonecall", indexes = {@Index(columnList = "ext_cust_id")})
public class PhoneCall implements Serializable {

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
    
    @Column(name = "contact_id", nullable = true)
    private Integer contactID = null;

    /**
     * Get the value of contactID
     *
     * @return the value of contactID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * Set the value of contactID
     *
     * @param contactID new value of contactID
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    @Column(name = "task_id", nullable = true)
    private Integer taskId = null;

    /**
     * Get the value of taskId
     *
     * @return the value of taskId
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * Set the value of taskId
     *
     * @param taskId new value of taskId
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hangup_time", nullable = false)
    private Date hangupTime;

    /**
     * Get the value of hangupTime
     *
     * @return the value of hangupTime
     */
    public Date getHangupTime() {
        return hangupTime;
    }

    /**
     * Set the value of hangupTime
     *
     * @param hangupTime new value of hangupTime
     */
    public void setHangupTime(Date hangupTime) {
        this.hangupTime = hangupTime;
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

    @Column(name = "called_number", nullable = true, length = 64)
    private String calledNumber;

    /**
     * Get the value of calledNumber
     *
     * @return the value of calledNumber
     */
    public String getCalledNumber() {
        return calledNumber;
    }

    /**
     * Set the value of calledNumber
     *
     * @param calledNumber new value of calledNumber
     */
    public void setCalledNumber(String calledNumber) {
        this.calledNumber = calledNumber;
    }

    @Column(name = "caller_id", nullable = true, length = 64)
    private String callerID;

    /**
     * Get the value of callerID
     *
     * @return the value of callerID
     */
    public String getCallerID() {
        return callerID;
    }

    /**
     * Set the value of callerID
     *
     * @param callerID new value of callerID
     */
    public void setCallerID(String callerID) {
        this.callerID = callerID;
    }
    
    @Column(name = "channel", nullable = true, length = 255)
    private String channel = null;

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

    @Column(name = "unique_id", nullable = true, length = 64)
    private String uniqueID;

    /**
     * Get the value of uniqueID
     *
     * @return the value of uniqueID
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Set the value of uniqueID
     *
     * @param uniqueID new value of uniqueID
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
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
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "t_phonecall_url", joinColumns = {@JoinColumn(name = "call_fk")}, inverseJoinColumns = {@JoinColumn(name = "url_fk")})
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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final PhoneCall other = (PhoneCall) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
