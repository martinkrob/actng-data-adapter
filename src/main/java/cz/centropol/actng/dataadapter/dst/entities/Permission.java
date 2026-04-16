/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;


/**
 *
 * @author krob
 */
@Entity(name = "DSTPermission")
@Table(name = "cfg_permission", indexes = {@Index(columnList = "entity_id")})
@TableGenerator(name = "permission_id_generator", table = "ID_GENERATOR", valueColumnName = "sequence_count", pkColumnName = "sequence_name", pkColumnValue = "permission_id", allocationSize = 1)
public class Permission implements Serializable, VersionedEntity {

    private static final long serialVersionUID = 1L;
    
    public static final byte WRITE  = Byte.parseByte("000010", 2);
    public static final byte READ   = Byte.parseByte("000001", 2);
    public static final byte EMPTY  = Byte.parseByte("000000", 2);

    @Id
    @GeneratedValue(generator = "permission_id_generator", strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    /**
     * Get the value of entityID
     *
     * @return the value of entityID
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Set the value of entityID
     *
     * @param entityID new value of entityID
     */
    public void setEntityId(Integer entityID) {
        this.entityId = entityID;
    }

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    /**
     * Get the value of ownerID
     *
     * @return the value of ownerID
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * Set the value of ownerID
     *
     * @param ownerID new value of ownerID
     */
    public void setOwnerId(Integer ownerID) {
        this.ownerId = ownerID;
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
     * Get the value of groupID
     *
     * @return the value of groupID
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Set the value of groupID
     *
     * @param groupID new value of groupID
     */
    public void setGroupId(Integer groupID) {
        this.groupId = groupID;
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
    private User owner;

    /**
     * Get the value of ownerName
     *
     * @return the value of ownerName
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set the value of ownerName
     *
     * @param owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Transient
    private GroupOfUsers group;

    /**
     * Get the value of group
     *
     * @return the value of group
     */
    public GroupOfUsers getGroup() {
        return group;
    }

    /**
     * Set the value of group
     *
     * @param group new value of group
     */
    public void setGroup(GroupOfUsers group) {
        this.group = group;
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

    @Override
    public String toString() {
        char[] str = {'-', '-', '-', '-', '-', '-'};        
        if ((ownerPermValue & READ) == READ) str[0] = 'r';
        if ((ownerPermValue & WRITE) == WRITE) str[1] = 'w';                        
        if ((groupPermValue & READ) == READ) str[2] = 'r';        
        if ((groupPermValue & WRITE) == WRITE) str[3] = 'w';                              
        if ((otherPermValue & READ) == READ) str[4] = 'r';        
        if ((otherPermValue & WRITE) == WRITE) str[5] = 'w';                                
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
