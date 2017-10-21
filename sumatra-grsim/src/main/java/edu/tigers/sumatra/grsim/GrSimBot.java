/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.grsim;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;
import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.botmanager.bots.ASimBot;
import edu.tigers.sumatra.ids.BotID;



@Persistent
public class GrSimBot extends ASimBot
{
	private transient GrSimStatus					status					= new GrSimStatus();
	private transient final GrSimBaseStation	baseStation;
															
	@Configurable
	private static double							center2DribblerDist	= 90;
																						
																						
	static
	{
		ConfigRegistration.registerClass("botmgr", GrSimBot.class);
	}
	
	
	@SuppressWarnings("unused")
	private GrSimBot()
	{
		super();
		baseStation = null;
	}
	
	
	
	public GrSimBot(final BotID id, final GrSimBaseStation baseStation)
	{
		super(EBotType.GRSIM, id, baseStation);
		this.baseStation = baseStation;
	}
	
	
	
	public final GrSimStatus getStatus()
	{
		return status;
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
		return 0.09;
	}
	
	
	
	@Override
	public double getCenter2DribblerDist()
	{
		return center2DribblerDist;
	}
}
