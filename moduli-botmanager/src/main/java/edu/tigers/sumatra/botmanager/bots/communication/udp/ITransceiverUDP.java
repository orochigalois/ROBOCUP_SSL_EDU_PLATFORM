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

import java.net.NetworkInterface;

import edu.tigers.sumatra.botmanager.bots.communication.ITransceiver;



public interface ITransceiverUDP extends ITransceiver
{
	
	void addObserver(ITransceiverUDPObserver o);
	
	
	
	void removeObserver(ITransceiverUDPObserver o);
	
	
	
	void open();
	
	
	
	void open(String host, int dstPort);
	
	
	
	void close();
	
	
	
	boolean isOpen();
	
	
	
	void setDestination(String host, int port);
	
	
	
	void setLocalPort(int port);
	
	
	
	void setNetworkInterface(NetworkInterface network);
}
