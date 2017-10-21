/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.presenter.humanref;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.log.GameLogEntry;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public interface IHumanRefViewDriver
{
	
	public void setNewWorldFrame(WorldFrameWrapper frame);
	
	
	
	public void setNewRefFrame(IAutoRefFrame frame);
	
	
	
	public void setNewGameLogEntry(GameLogEntry entry);
	
	
	
	public void paintField();
	
	
	
	public void start();
	
	
	
	public void stop();
}
