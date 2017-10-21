/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.basestation;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.botmanager.bots.communication.ENetworkState;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.ids.BotID;



public class DummyBaseStation extends ABaseStation
{
	
	public DummyBaseStation()
	{
		super(EBotType.UNKNOWN);
	}
	
	
	@Override
	public void enqueueCommand(final BotID id, final ACommand cmd)
	{
	}
	
	
	@Override
	public void onConnect()
	{
	}
	
	
	@Override
	public void onDisconnect()
	{
	}
	
	
	@Override
	public ENetworkState getNetState()
	{
		return ENetworkState.ONLINE;
	}
	
	
	@Override
	public String getName()
	{
		return "Dummy";
	}
}
