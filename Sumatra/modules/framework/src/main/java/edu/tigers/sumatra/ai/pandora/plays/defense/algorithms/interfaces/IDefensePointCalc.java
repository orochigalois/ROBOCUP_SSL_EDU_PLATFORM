/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.defense.algorithms.interfaces;

import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.metis.defense.data.DefensePoint;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;
import edu.tigers.sumatra.ai.pandora.roles.defense.DefenderRole;



public interface IDefensePointCalc
{
	
	Map<DefenderRole, DefensePoint> getDefenderDistribution(MetisAiFrame frame,
			List<DefenderRole> defenders, List<FoeBotData> foeBotDataList);
}
