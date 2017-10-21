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

import java.io.Serializable;
import java.util.Comparator;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.TrackedBot;



@Persistent
public class BotDistance implements Serializable
{
	
	public static final Comparator<BotDistance>	ASCENDING			= new Comparator<BotDistance>()
																						{
																							@Override
																							public int compare(final BotDistance d1,
																									final BotDistance d2)
																							{
																								return Double
																										.compare(d1.getDist(), d2.getDist());
																							}
																						};
	
	public static final Comparator<BotDistance>	DESCENDING			= new Comparator<BotDistance>()
																						{
																							@Override
																							public int compare(final BotDistance d1,
																									final BotDistance d2)
																							{
																								return Double
																										.compare(d2.getDist(), d1.getDist());
																							}
																						};
	
	
	
	private static final long							serialVersionUID	= 1131465265437655081L;
	
	
	public static final BotDistance					NULL_BOT_DISTANCE	= new BotDistance(null, Double.MAX_VALUE);
	
	
	
	private ITrackedBot									bot;
	
	private double											dist;
	
	
	@SuppressWarnings("unused")
	private BotDistance()
	{
	}
	
	
	
	public BotDistance(final ITrackedBot bot, final double dist)
	{
		this.bot = bot;
		this.dist = dist;
	}
	
	
	
	public ITrackedBot getBot()
	{
		return bot;
	}
	
	
	
	public double getDist()
	{
		return dist;
	}
}
