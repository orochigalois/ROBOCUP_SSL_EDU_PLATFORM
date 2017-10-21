/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.defense.algorithms.helpers;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.metis.defense.data.DefensePoint;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;
import edu.tigers.sumatra.ai.pandora.roles.defense.DefenderRole;



public class PassiveAgressivePointCalc implements IPointOnLine
{
	
	@Override
	public DefensePoint getPointOnLine(final DefensePoint defPoint, final MetisAiFrame frame,
			final DefenderRole defender)
	{
		FoeBotData foeBotData = defPoint.getFoeBotData();
		
		if (null == foeBotData)
		{
			return defPoint;
		}
		
		return new DefensePoint(foeBotData.getBot2goalNearestToBot(), foeBotData);
	}
}
