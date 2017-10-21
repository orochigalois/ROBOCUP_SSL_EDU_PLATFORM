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



public interface IStateMachine<StateType>
{
	
	void update();
	
	
	
	void triggerEvent(Enum<? extends Enum<?>> event);
	
	
	
	void setInitialState(StateType currentState);
	
	
	
	StateType getCurrentStateId();
	
	
	
	void addTransition(final EventStatePair esp, final StateType state);
	
	
	
	boolean valid();
	
	
	
	void restart();
	
	
	
	void stop();
}
