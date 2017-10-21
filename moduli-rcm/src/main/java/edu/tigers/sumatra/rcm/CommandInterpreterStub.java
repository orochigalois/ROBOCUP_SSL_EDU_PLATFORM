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
import edu.tigers.sumatra.botmanager.bots.DummyBot;



public class CommandInterpreterStub implements ICommandInterpreter
{
	private final ABot bot = new DummyBot();
	
	
	@Override
	public void interpret(final BotActionCommand command)
	{
	}
	
	
	@Override
	public void stopAll()
	{
	}
	
	
	@Override
	public ABot getBot()
	{
		return bot;
	}
	
	
	@Override
	public boolean isHighSpeedMode()
	{
		return false;
	}
	
	
	@Override
	public void setHighSpeedMode(final boolean highSpeedMode)
	{
	}
	
	
	@Override
	public boolean isPaused()
	{
		return false;
	}
	
	
	@Override
	public void setPaused(final boolean paused)
	{
	}
	
	
	@Override
	public double getCompassThreshold()
	{
		return 0.4;
	}
}
