/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.botskills;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;



public abstract class AMoveBotSkill extends ABotSkill
{
	// 
	// @SerialData(type = ESerialDataType.UINT16)
	// private int kickSpeed;
	//
	// @SerialData(type = ESerialDataType.UINT8)
	// private int kickFlags;
	//
	// @SerialData(type = ESerialDataType.UINT16)
	// private int dribbleSpeed = 0;
	
	
	
	public AMoveBotSkill(final EBotSkill skill)
	{
		super(skill);
	}
	
	
	// 
	// public void setKick(final double kickSpeed, final EKickerDevice device, final EKickerMode mode)
	// {
	// this.kickSpeed = (int) kickSpeed * 1000;
	// kickFlags &= ~(0xF3); // setAutocharge also modifies a bit in this field
	// kickFlags |= device.getValue() | (mode.getId() << 1);
	// }
	//
	//
	// 
	// public final void setDribbleSpeed(final int dribbleSpeed)
	// {
	// this.dribbleSpeed = dribbleSpeed;
	// }
}
