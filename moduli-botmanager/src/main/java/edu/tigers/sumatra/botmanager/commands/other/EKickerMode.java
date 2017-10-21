/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.other;


public enum EKickerMode
{
	
	FORCE(0),
	
	ARM(1),
	
	DISARM(2),
	
	DRIBBLER(3),
	
	ARM_AIM(4),
	
	NONE(0x0F);
	private final int id;
	
	
	private EKickerMode(final int id)
	{
		this.id = id;
	}
	
	
	
	public final int getId()
	{
		return id;
	}
}
