/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.rcm;

import edu.dhbw.mannheim.tigers.sumatra.proto.BotActionCommandProtos.BotActionCommand;
import edu.tigers.sumatra.botmanager.bots.ABot;



public interface ICommandInterpreter
{
	
	
	void interpret(BotActionCommand command);
	
	
	
	void stopAll();
	
	
	
	ABot getBot();
	
	
	
	boolean isHighSpeedMode();
	
	
	
	void setHighSpeedMode(boolean highSpeedMode);
	
	
	
	boolean isPaused();
	
	
	
	void setPaused(boolean paused);
	
	
	
	double getCompassThreshold();
	
}