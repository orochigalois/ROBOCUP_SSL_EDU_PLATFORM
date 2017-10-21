/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter.view;

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ids.BotID;



public interface IRoleControlPanelObserver
{
	
	void addRole(ARole role, BotID botId);
	
	
	
	void removeRole(ARole role);
}
