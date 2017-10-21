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

import java.util.Set;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.events.IGameEventDetector.EGameEventDetectorType;
import edu.tigers.autoreferee.engine.log.IGameLog;



public interface IAutoRefEngine
{
	
	public enum AutoRefMode
	{
		
		ACTIVE,
		
		PASSIVE
	}
	
	
	
	public void pause();
	
	
	
	public void resume();
	
	
	
	public void stop();
	
	
	
	public void reset();
	
	
	
	public AutoRefMode getMode();
	
	
	
	public IGameLog getGameLog();
	
	
	
	public void setActiveGameEvents(Set<EGameEventDetectorType> types);
	
	
	
	public void process(IAutoRefFrame frame);
}
