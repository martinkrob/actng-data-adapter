/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.centropol.actng.dataadapter.dst.entities;

import java.io.Serializable;

/**
 *
 * @author krob
 */
public enum DSTEntityType implements Serializable {
    ROOT, 
        ENVIRONMENT, 
            APPLICATION, USER, GROUP_OF_USERS, ETMSTACK, TASK, OPERATING_TIME, EMAIL_HANDLER, QMFORM, APIKEY,
    
        SWITCH, 
            QUEUE, GROUP_OF_QUEUES, QAP, REASON_CODE, ENDPOINT, DIALPLAN_CONTEXT, DIALER ;
}
