/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.rcm;



public enum EControllerAction
{
	
	FORWARD(true),
	
	BACKWARD(true),
	
	LEFT(true),
	
	RIGHT(true),
	
	ROTATE_LEFT(true),
	
	ROTATE_RIGHT(true),
	
	DRIBBLE(true),
	
	DISARM,
	
	KICK_ARM,
	
	KICK_FORCE,
	
	CHIP_ARM,
	
	CHIP_FORCE,
	
	
	ACCELERATE(true),
	
	DECELERATE(true),
	
	UNDEFINED;
	
	
	private final ExtIdentifier	defaultMapping	= ExtIdentifier.undefinedIdentifier();
	private final boolean			continuous;
	
	
	private EControllerAction()
	{
		continuous = false;
	}
	
	
	private EControllerAction(final boolean continous)
	{
		continuous = continous;
	}
	
	
	
	public ExtIdentifier getDefaultMapping()
	{
		return defaultMapping;
	}
	
	
	
	public boolean isContinuous()
	{
		return continuous;
	}
}
