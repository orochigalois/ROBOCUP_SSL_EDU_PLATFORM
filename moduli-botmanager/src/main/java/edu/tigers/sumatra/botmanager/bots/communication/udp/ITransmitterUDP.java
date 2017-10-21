/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots.communication.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.tigers.sumatra.botmanager.bots.communication.Statistics;
import edu.tigers.sumatra.botmanager.commands.ACommand;



public interface ITransmitterUDP
{
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	void enqueueCommand(ACommand cmd);
	
	
	
	void start();
	
	
	
	void stop();
	
	
	
	void setSocket(DatagramSocket newSocket) throws IOException;
	
	
	
	void setDestination(InetAddress dstIp, int dstPort);
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	Statistics getStats();
	
	
	
	void setLegacy(boolean legacy);
}