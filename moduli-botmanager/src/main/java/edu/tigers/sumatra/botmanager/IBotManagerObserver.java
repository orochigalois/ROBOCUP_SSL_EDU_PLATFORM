/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager;

import edu.tigers.sumatra.botmanager.bots.ABot;



public interface IBotManagerObserver
{
	
	void onBotAdded(ABot bot);
	
	
	
	void onBotRemoved(ABot bot);
}
