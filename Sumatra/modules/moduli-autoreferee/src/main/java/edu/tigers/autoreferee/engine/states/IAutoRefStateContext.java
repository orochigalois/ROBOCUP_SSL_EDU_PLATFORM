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

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.engine.log.GameLog;
import edu.tigers.autoreferee.remote.ICommandResult;



public interface IAutoRefStateContext
{
	
	public ICommandResult sendCommand(RefCommand cmd);
	
	
	
	public FollowUpAction getFollowUpAction();
	
	
	
	public void setFollowUpAction(FollowUpAction action);
	
	
	
	public boolean doProceed();
	
	
	
	public GameLog getGameLog();
	
}
