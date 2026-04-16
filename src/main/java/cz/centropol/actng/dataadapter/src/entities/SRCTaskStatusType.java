/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.src.entities;

import java.io.Serializable;

/**
 *
 * @author krob
 */
public enum SRCTaskStatusType implements Serializable {
    ACTIVE, QUEUED, INPROCESSING, DELIVERED, PAUSED, FINISHED, CANCELED;
}
