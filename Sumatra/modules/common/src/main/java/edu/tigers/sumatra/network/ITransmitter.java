/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.network;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;



public interface ITransmitter<D>
{
	
	
	static final int	UNDEFINED_PORT	= -1;
	
	
	
	boolean send(D data);
	
	
	
	void cleanup() throws IOException;
	
	
	
	int getLocalPort();
	
	
	
	InetAddress getLocalAddress();
	
	
	
	int getTargetPort();
	
	
	
	boolean isReady();
}
