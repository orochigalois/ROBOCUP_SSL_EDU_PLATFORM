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

import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.bots.communication.ENetworkState;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationEthStats;
import edu.tigers.sumatra.botmanager.commands.basestation.BaseStationWifiStats;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchFeedback;
import edu.tigers.sumatra.ids.BotID;



public interface IBaseStationObserver
{
	
	default void onIncommingBotCommand(final BotID id, final ACommand command)
	{
	}
	
	
	
	default void onIncommingBaseStationCommand(final ACommand command)
	{
	}
	
	
	
	default void onNewBaseStationWifiStats(final BaseStationWifiStats stats)
	{
	}
	
	
	
	default void onNewBaseStationEthStats(final BaseStationEthStats stats)
	{
	}
	
	
	
	default void onNetworkStateChanged(final ENetworkState netState)
	{
	}
	
	
	
	default void onNewPingDelay(final double delay)
	{
	}
	
	
	
	default void onBotOffline(final BotID id)
	{
	}
	
	
	
	default void onBotOnline(final ABot bot)
	{
	}
	
	
	
	default void onNewMatchFeedback(final BotID botId, final TigerSystemMatchFeedback feedback)
	{
	}
}
