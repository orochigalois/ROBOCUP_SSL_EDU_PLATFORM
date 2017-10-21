/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine;

import edu.tigers.autoreferee.IAutoRefFrame;



public class PassiveAutoRefEngine extends AbstractAutoRefEngine
{
	
	@Override
	public void stop()
	{
	}
	
	
	@Override
	public AutoRefMode getMode()
	{
		return AutoRefMode.PASSIVE;
	}
	
	
	@Override
	public synchronized void process(final IAutoRefFrame frame)
	{
		if (engineState == EEngineState.PAUSED)
		{
			return;
		}
		super.process(frame);
		logGameEvents(getGameEvents(frame));
	}
}
