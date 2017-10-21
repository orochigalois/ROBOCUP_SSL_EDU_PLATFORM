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

import java.util.Comparator;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;



@Persistent
public class PenaltyStats
{
	private BotID	bot;
	int				penaltyScored		= 0;
	int				penaltyNotScored	= 0;
	
	
	@SuppressWarnings("unused")
	private PenaltyStats()
	{
	}
	
	
	
	public PenaltyStats(final BotID bot)
	{
		this.bot = bot;
	}
	
	
	
	public void addScoredGoal()
	{
		penaltyScored++;
	}
	
	
	
	public void addNotScoredGoal()
	{
		penaltyNotScored++;
	}
	
	
	
	public int getSummedScore()
	{
		return penaltyScored - penaltyNotScored;
	}
	
	
	
	public BotID getBotID()
	{
		return bot;
	}
	
	
	public static class PenaltyStatsComparator implements Comparator<PenaltyStats>
	{
		@Override
		public int compare(final PenaltyStats bot1, final PenaltyStats bot2)
		{
			return (int) Math.signum(bot2.getSummedScore() - bot1.getSummedScore());
		}
	}
}
