/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.log;

import java.util.List;

import edu.tigers.autoreferee.engine.log.GameLog.IGameLogObserver;



public interface IGameLog
{
	
	
	public List<GameLogEntry> getEntries();
	
	
	
	public void addObserver(IGameLogObserver observer);
	
	
	
	public void removeObserver(IGameLogObserver observer);
	
}