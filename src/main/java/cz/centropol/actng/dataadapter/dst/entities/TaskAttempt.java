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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "DSTTaskAttempt")
@Table(name = "t_task_attempt", indexes = {@Index(columnList = "task_id")})
public class TaskAttempt implements Serializable {
    

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
    
    @Column(name = "task_id", nullable = true)
    private Integer taskId;

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
    @Column(name = "attempt_time", nullable = false)
    private Date attemptTime;

    /**
     * Get the value of attemptTime
     *
     * @return the value of attemptTime
     */
    public Date getAttemptTime() {
        return attemptTime;
    }

    /**
     * Set the value of attemptTime
     *
     * @param attemptTime new value of attemptTime
     */
    public void setAttemptTime(Date attemptTime) {
        this.attemptTime = attemptTime;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final TaskAttempt other = (TaskAttempt) obj;
        return Objects.equals(this.id, other.id);
    }
}
