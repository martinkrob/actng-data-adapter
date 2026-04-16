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
public enum SRCTaskEventType
{
    ORIGINATE, ORIGINATE_ERROR, QUEUED, ABANDONED, RING_ABAND, AGENT_CALLED, AGENT_CONNECT, AGENT_COMPLETE,
    TASK_ADDED, TASK_DELIVERED, TASK_RESCHEDULED, TASK_RESCHEDULED_AND_TRANSFERRED, TASK_EXPIRED_AND_RESCHEDULED, TASK_TRANSFERRED, TASK_FINISHED,
    TASK_COWORK_START, TASK_COWORK_REJECTED, TASK_COWORK_FINISHED, TASK_COWORK_CANCELED, TASK_CANCELED, TASK_EXPIRED_AND_CANCELED, TASK_CANCELED_ATTEMPT_NUM_OVERFLOW, TASK_CANCELED_NO_PHONE, TASK_UPDATED;        
}
