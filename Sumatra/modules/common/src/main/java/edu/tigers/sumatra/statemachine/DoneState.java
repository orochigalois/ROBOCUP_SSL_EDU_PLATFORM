/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.statemachine;



public class DoneState implements IState, IRoleState
{
	
	public enum EStateId
	{
		
		DONE;
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
	public Enum<? extends Enum<?>> getIdentifier()
	{
		return EStateId.DONE;
	}
}
