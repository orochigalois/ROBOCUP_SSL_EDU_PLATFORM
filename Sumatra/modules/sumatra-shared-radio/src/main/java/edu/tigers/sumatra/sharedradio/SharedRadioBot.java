/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.sharedradio;

import com.github.g3force.configurable.Configurable;
import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.botmanager.bots.ASimBot;
import edu.tigers.sumatra.ids.BotID;



@Persistent
public class SharedRadioBot extends ASimBot
{
	
	private final SharedRadioBaseStation	baseStation;
														
	@Configurable
	private static double						center2DribblerDist	= 75;
																					
																					
	@SuppressWarnings("unused")
	private SharedRadioBot()
	{
		super();
		baseStation = null;
	}
	
	
	
	public SharedRadioBot(final BotID botId, final SharedRadioBaseStation baseStation)
	{
		super(EBotType.SHARED_RADIO, botId, baseStation);
		this.baseStation = baseStation;
	}
	
	
	@Override
	public void sendMatchCommand()
	{
		super.sendMatchCommand();
		baseStation.sendMatchCommand(this);
	}
	
	
	@Override
	protected double getFeedbackDelay()
	{
		return 0.17;
	}
	
	
	
	@Override
	public double getCenter2DribblerDist()
	{
		return center2DribblerDist;
	}
	
}
