/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots.communication;

import edu.tigers.sumatra.botmanager.commands.ACommand;



public interface ITransceiver
{
	
	void enqueueCommand(ACommand cmd);
	
	
	
	Statistics getReceiverStats();
	
	
	
	Statistics getTransmitterStats();
}
