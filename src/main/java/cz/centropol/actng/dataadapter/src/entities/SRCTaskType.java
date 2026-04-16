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
package cz.centropol.actng.dataadapter.src.entities;

/**
 *
 * @author krob
 */
public enum SRCTaskType {
    GENERAL, EMAIL, POWER_CALL, PREVIEW_DIALING, CO_WORK,    
    CRM_EMAIL, CRM_CALL, CRM_TASK, CRM_LETTER, CRM_APPOINTMENT, CRM_VISIT, CRM_CASE, CRM_LEAD,
    BASED_ON_ACD_CALL, BASED_ON_CALL, BASED_ON_VISIT;
}
