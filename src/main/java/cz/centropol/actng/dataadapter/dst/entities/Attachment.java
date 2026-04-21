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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "DSTAttachment")
@Table(name = "t_attachment")
public class Attachment implements Serializable {

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
    
    @Column(name = "file_name", nullable = false, length = 255)    
    private String fileName;

    /**
     * Get the value of fileName
     *
     * @return the value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the value of fileName
     *
     * @param fileName new value of fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "content_fk", nullable = false)
    private Integer contentId;

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
    
}
