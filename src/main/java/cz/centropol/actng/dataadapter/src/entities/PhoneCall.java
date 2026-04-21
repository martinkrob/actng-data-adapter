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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "SRCPhoneCall")
@Table(name = "t_phonecall")
public class PhoneCall implements Serializable {

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
    private String callerId;

    /**
     * Get the value of callerId
     *
     * @return the value of callerId
     */
    public String getCallerId() {
        return callerId;
    }

    /**
     * Set the value of callerId
     *
     * @param callerId new value of callerId
     */
    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    @Column(name = "channel", nullable = true, length = 128)
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

    @Column(name = "dest_channel", nullable = true, length = 128)
    private String destionatoinChannel;

    /**
     * Get the value of destionatoinChannel
     *
     * @return the value of destionatoinChannel
     */
    public String getDestionatoinChannel() {
        return destionatoinChannel;
    }

    /**
     * Set the value of destionatoinChannel
     *
     * @param destionatoinChannel new value of destionatoinChannel
     */
    public void setDestionatoinChannel(String destionatoinChannel) {
        this.destionatoinChannel = destionatoinChannel;
    }

    @Column(name = "interface", nullable = true, length = 128)
    private String iface;

    /**
     * Get the value of iface
     *
     * @return the value of iface
     */
    public String getIface() {
        return iface;
    }

    /**
     * Set the value of iface
     *
     * @param iface new value of iface
     */
    public void setIface(String iface) {
        this.iface = iface;
    }

    @Column(name = "unique_id", nullable = true, length = 64)
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

    @Column(name = "dest_unique_id", nullable = true, length = 64)
    private String destUniqueId;

    /**
     * Get the value of destUniqueId
     *
     * @return the value of destUniqueId
     */
    public String getDestUniqueId() {
        return destUniqueId;
    }

    /**
     * Set the value of destUniqueId
     *
     * @param destUniqueId new value of destUniqueId
     */
    public void setDestUniqueId(String destUniqueId) {
        this.destUniqueId = destUniqueId;
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
