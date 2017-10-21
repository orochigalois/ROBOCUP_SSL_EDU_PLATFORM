/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.botmanager.basestation.DummyBaseStation;
import edu.tigers.sumatra.ids.BotID;



@Persistent
public class DummyBot extends ASimBot
{
	private boolean avail2Ai = false;
	
	
	
	public DummyBot()
	{
		super(EBotType.UNKNOWN, BotID.get(), new DummyBaseStation());
	}
	
	
	
	public DummyBot(final BotID botId)
	{
		super(EBotType.UNKNOWN, botId, new DummyBaseStation());
	}
	
	
	
	public DummyBot(final ABot aBot)
	{
		super(aBot, EBotType.UNKNOWN);
	}
	
	
	@Override
	public boolean isAvailableToAi()
	{
		return avail2Ai;
	}
	
	
	
	public final void setAvail2Ai(final boolean avail2Ai)
	{
		this.avail2Ai = avail2Ai;
	}
	
	
	@Override
	public boolean isBarrierInterrupted()
	{
		return false;
	}
	
	
	
	@Override
	public double getCenter2DribblerDist()
	{
		return 90;
	}
}
