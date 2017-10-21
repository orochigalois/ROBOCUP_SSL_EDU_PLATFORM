/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.List;

import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.BotIDMap;



public interface IPlayStrategy
{
	
	
	List<APlay> getActivePlays();
	
	
	
	BotIDMap<ARole> getActiveRoles();
	
	
	
	List<ARole> getActiveRoles(final ERole roleType);
	
	
	
	List<ARole> getActiveRoles(final EPlay playType);
	
	
	
	List<APlay> getFinishedPlays();
	
	
	
	int getNumRoles();
	
	
	
	EAIControlState getAIControlState();
}