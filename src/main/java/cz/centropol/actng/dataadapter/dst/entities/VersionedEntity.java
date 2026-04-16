/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.centropol.actng.dataadapter.dst.entities;

/**
 *
 * @author krob
 */
public interface VersionedEntity {
    Object getId();    
    
    void setVersion(Integer version);
    
    Integer getVersion();    
}
