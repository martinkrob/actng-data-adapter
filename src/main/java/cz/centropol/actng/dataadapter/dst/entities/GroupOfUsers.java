/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author krob
 */
@Entity(name = "DSTGroupOfUsers")
@Table(name = "cfg_group_of_users")

public class GroupOfUsers extends AbstractEntity {
               
    @Column(name = "folder_fk", nullable = false)
    private Integer folderId; // Změna na Integer

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }
    
    @Column(name = "name", nullable = false, length = 255, unique = true)
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

    @Column(name = "integration_id", nullable = true, unique = true)    
    private Integer integrationId = null;

    /**
     * Get the value of integrationId
     *
     * @return the value of integrationId
     */
    public Integer getIntegrationId() {
        return integrationId;
    }

    /**
     * Set the value of integrationId
     *
     * @param integrationId new value of integrationId
     */
    public void setIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
    }
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cfg_group_of_users_user", joinColumns = {@JoinColumn(name = "group_fk")}, inverseJoinColumns = {@JoinColumn(name = "user_fk")}) 
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }                

}
