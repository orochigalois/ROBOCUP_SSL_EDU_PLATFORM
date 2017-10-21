/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.defense.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.metis.defense.data.DefensePoint;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;
import edu.tigers.sumatra.ai.pandora.plays.defense.algorithms.interfaces.IDefensePointCalc;
import edu.tigers.sumatra.ai.pandora.roles.defense.DefenderRole;



public class SimpleDefensePointCalc implements IDefensePointCalc
{
	@Override
	public Map<DefenderRole, DefensePoint> getDefenderDistribution(final MetisAiFrame frame,
			final List<DefenderRole> defenders, final List<FoeBotData> foeBots)
	{
		Map<DefenderRole, DefensePoint> distribution = new HashMap<>();
		for (int i = 0; i < foeBots.size(); i++)
		{
			List<DefenderRole> defs = new ArrayList<>();
			defs.add(defenders.get(i));
			distribution.put(defenders.get(0), new DefensePoint(foeBots.get(i).getBot2goalNearestToBot(), foeBots.get(i)
					.getFoeBot()));
		}
		
		return distribution;
	}
}
