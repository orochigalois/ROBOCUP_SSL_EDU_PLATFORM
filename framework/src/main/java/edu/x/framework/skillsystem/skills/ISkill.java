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

import edu.tigers.sumatra.ai.data.LedControl;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.statemachine.IState;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.MovementCon;
import edu.x.framework.skillsystem.driver.IPathDriver;



public interface ISkill
{
	
	ESkill getType();
	
	
	
	void update(WorldFrameWrapper wfw, ABot bot);
	
	
	
	void setBotId(final BotID botId);
	
	
	
	void calcActions();
	
	
	
	void calcExitActions();
	
	
	
	void calcEntryActions();
	
	
	
	ShapeMap getShapes();
	
	
	
	MovementCon getMoveCon();
	
	
	
	BotID getBotId();
	
	
	
	IState getCurrentState();
	
	
	
	boolean isInitialized();
	
	
	
	IPathDriver getPathDriver();
	
	
	
	LedControl getLedControl();
	
	
	
	void setLedControl(final LedControl ledControl);
}