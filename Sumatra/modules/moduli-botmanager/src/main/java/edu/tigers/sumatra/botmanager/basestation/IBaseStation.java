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

import com.github.g3force.configurable.IConfigObserver;

import edu.tigers.sumatra.botmanager.bots.communication.ENetworkState;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.ids.BotID;



public interface IBaseStation extends IConfigObserver
{
	
	void enqueueCommand(BotID id, ACommand cmd);
	
	
	
	void connect();
	
	
	
	void disconnect();
	
	
	
	void addObserver(IBaseStationObserver observer);
	
	
	
	void removeObserver(IBaseStationObserver observer);
	
	
	
	ENetworkState getNetState();
	
	
	
	void startPing(int numPings, int payload);
	
	
	
	void stopPing();
	
	
	
	String getName();
}
