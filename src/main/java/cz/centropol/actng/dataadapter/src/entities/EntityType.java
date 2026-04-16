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
public enum EntityType implements Serializable
{
    ROOT, APPLICATION, USER, GROUP_OF_USERS, ETM, TASK, QUEUE, REASON_CODE, OPENING_TIME, INTERFACE, DIALPLAN_CONTEXT, LDAP_CONNECTION,
    EMAIL_HANDLER, TIME_PROFILE, ANI_POOL, ANI_RESOURCE, PHONE_NUMBER, QM;

}
