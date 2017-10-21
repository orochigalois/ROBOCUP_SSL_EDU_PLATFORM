/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.statistics;

import java.util.Map;

import edu.tigers.sumatra.ai.data.MatchStatistics;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public abstract class AStats
{
	
	public abstract void saveStatsToMatchStatistics(MatchStatistics matchStatistics);
	
	
	
	public abstract void onStatisticUpdate(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame);
	
	
	protected int getBotHardwareID(final BotID tigerToGet, final BaseAiFrame baseAiFrame)
	{
		ITrackedBot tBot = baseAiFrame.getWorldFrame().getBot(tigerToGet);
		int tigerHwId = -1;
		if (tBot != null)
		{
			tigerHwId = tBot.getBot().getHardwareId();
		}
		
		return tigerHwId;
	}
	
	
	protected void incrementEntryForBotIDInMap(final BotID key, final Map<BotID, Integer> mapToIncrease)
	{
		Integer entryAtBotID = mapToIncrease.get(key);
		
		if (entryAtBotID == null)
		{
			mapToIncrease.put(key, 1);
		} else
		{
			mapToIncrease.put(key, entryAtBotID + 1);
		}
	}
	
}
