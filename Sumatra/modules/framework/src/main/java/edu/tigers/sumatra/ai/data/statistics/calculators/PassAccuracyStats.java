/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.statistics.calculators;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ai.data.MatchStatistics;
import edu.tigers.sumatra.ai.data.MatchStatistics.EAvailableStatistic;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.statistics.AStats;
import edu.tigers.sumatra.ids.BotID;



public class PassAccuracyStats extends AStats
{
	private final Integer			millisecondsToBePassTarget		= 500;
	
	BotID									oldPassTarget						= null;
	
	private boolean					targetCounted						= false;
	private int							timeBeingPassTarget				= 0;
	
	private Map<BotID, Integer>	countBeingActivePassTarget		= new HashMap<>();
	private Integer					countActivePassTargetsGeneral	= 0;
	
	
	@Override
	public void saveStatsToMatchStatistics(final MatchStatistics matchStatistics)
	{
		StatisticData countActivePassTargets = new StatisticData(countBeingActivePassTarget,
				countActivePassTargetsGeneral);
		matchStatistics.putStatisticData(EAvailableStatistic.PassTarget, countActivePassTargets);
	}
	
	
	@Override
	public void onStatisticUpdate(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		
		
		
		BotID newPassTarget = baseAiFrame.getPrevFrame().getAICom().getOffensiveRolePassTargetID();
		
		if ((oldPassTarget != newPassTarget) && (newPassTarget != null))
		{
			timeBeingPassTarget = 0;
			targetCounted = false;
		} else if (newPassTarget != null)
		{
			long timeBetweenFrames = getTimeBetweenFrames(baseAiFrame);
			
			timeBeingPassTarget += timeBetweenFrames;
			
			if (isNewActivePassTarget())
			{
				targetCounted = true;
				countActivePassTargetsGeneral++;
				incrementEntryForBotIDInMap(newPassTarget, countBeingActivePassTarget);
			}
		}
		
		oldPassTarget = newPassTarget;
		
	}
	
	
	private long getTimeBetweenFrames(final BaseAiFrame baseAiFrame)
	{
		return baseAiFrame.getSimpleWorldFrame().getTimestamp()
				- baseAiFrame.getPrevFrame().getSimpleWorldFrame().getTimestamp();
	}
	
	
	private boolean isNewActivePassTarget()
	{
		return (timeBeingPassTarget > millisecondsToBePassTarget) && (targetCounted == false);
	}
}
