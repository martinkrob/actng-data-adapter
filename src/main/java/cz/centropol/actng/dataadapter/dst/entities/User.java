/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author krob
 */
@Entity(name = "DSTUser")
@Table(name = "cfg_user")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "folder_fk", nullable = false)
    private Integer folderId; // Změna na Integer

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }
    
    @Column(name = "user_name", nullable = false, length = 255, unique = true)
    private String userName = null;

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name = "password", nullable = true, length = 160 /* SHA-1 */) 
    private String password = null;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }
        
    @Column(name = "full_name", nullable = true, length = 255, unique = true)
    private String fullName = null;

    /**
     * Get the value of fullName
     *
     * @return the value of fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the value of fullName
     *
     * @param fullName new value of fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "email", nullable = true, length = 255)
    private String emailAddress = null;

    /**
     * Get the value of emailAddress
     *
     * @return the value of emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set the value of emailAddress
     *
     * @param emailAddress new value of emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
        
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "photo", nullable = true)
    private byte[] photo;

    /**
     * Get the value of photo
     *
     * @return the value of photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Set the value of photo
     *
     * @param photo new value of photo
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }    

    @Column(name = "root")
    private boolean root = false;

    /**
     * Get the value of root
     *
     * @return the value of root
     */
    public boolean isRoot() {
        return root;
    }

    /**
     * Set the value of root
     *
     * @param root new value of root
     */
    public void setRoot(boolean root) {
        this.root = root;
    }    
    
    @Column(name = "call_management", nullable = false)
    private boolean cms = true;

    /**
     * Get the value of cms
     *
     * @return the value of cms
     */
    public boolean isCms() {
        return cms;
    }

    /**
     * Set the value of cms
     *
     * @param cms new value of cms
     */
    public void setCms(boolean cms) {
        this.cms = cms;
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
    
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "cfg_user_kvpair", joinColumns = {@JoinColumn(name = "user_fk")}, inverseJoinColumns = {@JoinColumn(name = "kvpair_fk")})
    private ArrayList<KVPair> kvPairs;

    public ArrayList<KVPair> getKvPairs() {
        return kvPairs;
    }

    public void setKvPairs(ArrayList<KVPair> kvPairs) {
        this.kvPairs = kvPairs;
    }                    
}
