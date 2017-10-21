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


public enum EKickerDevice
{
	
	STRAIGHT(0),
	
	CHIP(1);
	
	
	private final int	value;
	
	
	private EKickerDevice(final int value)
	{
		this.value = value;
	}
	
	
	
	public final int getValue()
	{
		return value;
	}
}
