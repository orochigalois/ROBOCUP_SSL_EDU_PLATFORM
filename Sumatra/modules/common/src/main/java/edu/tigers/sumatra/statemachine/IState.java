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



public interface IState
{
	
	void doEntryActions();
	
	
	
	void doExitActions();
	
	
	
	void doUpdate();
	
	
	
	Enum<?> getIdentifier();
	
	
	
	default String getName()
	{
		if (getIdentifier() != null)
		{
			return getIdentifier().name();
		}
		return "";
	}
}
