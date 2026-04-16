/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.src.entities;

import java.lang.reflect.Field;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


/**
 *
 * @author krob
 */
@Entity(name = "SRCFolder")
@Table(name = "cfg_folder")
public class Folder extends Act2AbstractEntity
{        
    public Folder()
    {
    }
            
//    @JoinColumn(name="parent_fk")
//    @ManyToOne
//    private Folder parent;
//
//    public Folder getParent()
//    {
//        return parent;
//    }
//
//    public void setParent(Folder parent)
//    {
//        this.parent = parent;
//    }
    
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EntityType folderType;

    /**
     * Get the value of folderType
     *
     * @return the value of folderType
     */
    public EntityType getFolderType()
    {
        return folderType;
    }

    /**
     * Set the value of folderType
     *
     * @param folderType new value of folderType
     */
    public void setFolderType(EntityType folderType)
    {
        this.folderType = folderType;
    }
        
    @Override
    public String displayName()
    {
        return name;
    }

    @Override
    public String location()
    {
        StringBuilder sb = new StringBuilder();
        Folder f = this;

        while ((f != null) && (!Objects.equals(f, f.getParent())))
        {
            sb.insert(0, "/" + f.getName());
            f = f.getParent();
        }

        return sb.toString();
    }
    
    @Override
    public String toString()
    {
        String result = getClass().getName();
        result += "[";
        result += "id=" + getId() + ",";        
                
        for (Field f : getClass().getDeclaredFields())
        {
            try
            {
                result += f.getName().toLowerCase() + "=";
                result += (f.get(this) != null ? "'" + f.get(this) + "'" : "null") + ",";
            }
            catch (IllegalAccessException | IllegalArgumentException ex)
            {
            }
        }
        
        result += "location=" + location() + ",";
        result += "created=" + getCreationTime() + ",";
        result += "modified=" + getModificationTime();
        result += "]";
                
        return result;
    }
}
