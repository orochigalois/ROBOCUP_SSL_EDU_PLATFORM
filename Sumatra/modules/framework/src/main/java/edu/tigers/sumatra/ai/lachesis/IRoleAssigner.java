/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.lachesis;

import java.util.List;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public interface IRoleAssigner
{
	
	
	void assignRoles(BotIDMap<ITrackedBot> assignees, List<APlay> activePlays, AthenaAiFrame frame);
	
}
