/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.states;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.events.IGameEvent;



public interface IAutoRefState
{
	
	public boolean proceed(IAutoRefStateContext ctx);
	
	
	
	public void update(IAutoRefFrame frame, IAutoRefStateContext ctx);
	
	
	
	public boolean handleGameEvent(IGameEvent gameEvent, IAutoRefStateContext ctx);
	
	
	
	public boolean canProceed();
	
	
	
	public void reset();
}
