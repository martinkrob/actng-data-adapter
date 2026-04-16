/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author krob
 */
@Entity(name = "SRCPermission")
@Table(name = "cfg_permission", indexes = {@Index(columnList = "entity_id")})
public class Permission implements Serializable {
    
    public static final byte SPECL = Byte.parseByte("001000", 2);
    public static final byte EXEC = Byte.parseByte("000100", 2);
    public static final byte WRITE = Byte.parseByte("000010", 2);
    public static final byte READ = Byte.parseByte("000001", 2);
    public static final byte EMPTY = Byte.parseByte("000000", 2);

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

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    /**
     * Get the value of entityId
     *
     * @return the value of entityId
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Set the value of entityId
     *
     * @param entityId new value of entityId
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    /**
     * Get the value of ownerId
     *
     * @return the value of ownerId
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * Set the value of ownerId
     *
     * @param ownerId new value of ownerId
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "owner_perm", nullable = false)
    private int ownerPermValue;

    /**
     * Get the value of ownerPermValue
     *
     * @return the value of ownerPermValue
     */
    public int getOwnerPermValue() {
        return ownerPermValue;
    }

    /**
     * Set the value of ownerPermValue
     *
     * @param ownerPermValue new value of ownerPermValue
     */
    public void setOwnerPermValue(int ownerPermValue) {
        this.ownerPermValue = ownerPermValue;
    }

    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    /**
     * Get the value of groupId
     *
     * @return the value of groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Set the value of groupId
     *
     * @param groupId new value of groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Column(name = "group_perm", nullable = false)
    private int groupPermValue;

    /**
     * Get the value of groupPermValue
     *
     * @return the value of groupPermValue
     */
    public int getGroupPermValue() {
        return groupPermValue;
    }

    /**
     * Set the value of groupPermValue
     *
     * @param groupPermValue new value of groupPermValue
     */
    public void setGroupPermValue(int groupPermValue) {
        this.groupPermValue = groupPermValue;
    }

    @Column(name = "other_perm", nullable = false)
    private int otherPermValue;

    /**
     * Get the value of otherPermValue
     *
     * @return the value of otherPermValue
     */
    public int getOtherPermValue() {
        return otherPermValue;
    }

    /**
     * Set the value of otherPermValue
     *
     * @param otherPermValue new value of otherPermValue
     */
    public void setOtherPermValue(int otherPermValue) {
        this.otherPermValue = otherPermValue;
    }

    @Transient
    private String ownerName;

    /**
     * Get the value of ownerName
     *
     * @return the value of ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Set the value of ownerName
     *
     * @param ownerName new value of ownerName
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Transient
    private String groupName;

    /**
     * Get the value of groupName
     *
     * @return the value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the value of groupName
     *
     * @param groupName new value of groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    public String toString() {
        char[] str
                = {
                    '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'
                };
        if ((ownerPermValue & READ) == READ) {
            str[0] = 'r';
        }
        if ((ownerPermValue & WRITE) == WRITE) {
            str[1] = 'w';
        }
        if ((ownerPermValue & EXEC) == EXEC) {
            str[2] = 'x';
        }
        if ((ownerPermValue & SPECL) == SPECL) {
            str[3] = 's';
        }
        if ((groupPermValue & READ) == READ) {
            str[4] = 'r';
        }
        if ((groupPermValue & WRITE) == WRITE) {
            str[5] = 'w';
        }
        if ((groupPermValue & EXEC) == EXEC) {
            str[6] = 'x';
        }
        if ((groupPermValue & SPECL) == SPECL) {
            str[7] = 's';
        }
        if ((otherPermValue & READ) == READ) {
            str[8] = 'r';
        }
        if ((otherPermValue & WRITE) == WRITE) {
            str[9] = 'w';
        }
        if ((otherPermValue & EXEC) == EXEC) {
            str[10] = 'x';
        }
        if ((otherPermValue & SPECL) == SPECL) {
            str[11] = 's';
        }

        return String.valueOf(str);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.version);
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
        final Permission other = (Permission) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }
}
