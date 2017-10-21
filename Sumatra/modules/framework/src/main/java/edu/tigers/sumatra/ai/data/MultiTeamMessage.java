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

import java.util.HashMap;
import java.util.Map;

import edu.dhbw.mannheim.tigers.sumatra.model.data.multi_team.MultiTeamCommunication.RobotPlan;
import edu.dhbw.mannheim.tigers.sumatra.model.data.multi_team.MultiTeamCommunication.TeamPlan;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class MultiTeamMessage
{
	private final TeamPlan	teamPlan;
	
	
	
	public MultiTeamMessage(final TeamPlan teamPlan)
	{
		this.teamPlan = teamPlan;
	}
	
	
	
	public TeamPlan getTeamPlan()
	{
		return teamPlan;
	}
	
	
	
	public Map<BotID, RobotPlan> getRobotPlans(final ETeamColor teamColor)
	{
		Map<BotID, RobotPlan> robotPlanMap = new HashMap<>();
		
		for (RobotPlan robotPlan : teamPlan.getPlansList())
		{
			robotPlanMap.put(BotID.createBotId(robotPlan.getRobotId(), teamColor), robotPlan);
		}
		
		return robotPlanMap;
	}
}
