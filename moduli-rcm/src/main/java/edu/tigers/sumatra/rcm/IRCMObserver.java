/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.rcm;


public interface IRCMObserver
{
	
	void onStartStopButtonPressed(final boolean activeState);
	
	
	
	void onReconnect(boolean keepConnections);
	
	
	
	void setUpController(final boolean keepConnections);
	
	
	
	void onNextBot(ActionSender actionSender);
	
	
	
	void onPrevBot(ActionSender actionSender);
	
	
	
	void onBotUnassigned(ActionSender actionSender);
}
