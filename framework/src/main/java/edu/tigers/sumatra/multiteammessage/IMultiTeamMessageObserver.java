/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.multiteammessage;

import edu.tigers.sumatra.ai.data.MultiTeamMessage;



public interface IMultiTeamMessageObserver
{
	
	void onNewMultiTeamMessage(MultiTeamMessage message);
}
