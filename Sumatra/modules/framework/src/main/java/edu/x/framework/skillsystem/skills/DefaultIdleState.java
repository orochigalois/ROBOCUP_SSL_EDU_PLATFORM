/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;

import edu.tigers.sumatra.statemachine.IState;





public class DefaultIdleState implements IState
{
	private static enum EStateId
	{
		DEFAULT
	}
	
	
	@Override
	public void doEntryActions()
	{
	}
	
	
	@Override
	public void doExitActions()
	{
	}
	
	
	@Override
	public void doUpdate()
	{
	}
	
	
	@Override
	public Enum<?> getIdentifier()
	{
		return EStateId.DEFAULT;
	}
}
