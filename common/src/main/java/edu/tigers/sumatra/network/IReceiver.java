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
import java.net.DatagramPacket;
import java.net.DatagramSocket;



public interface IReceiver
{
	
	
	static final int	UNDEFINED_PORT	= -1;
	
	
	
	DatagramPacket receive(DatagramPacket store) throws IOException;
	
	
	
	void cleanup() throws IOException;
	
	
	
	boolean isReady();
}
