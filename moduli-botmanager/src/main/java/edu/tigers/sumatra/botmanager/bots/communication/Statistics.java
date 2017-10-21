/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots.communication;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class Statistics
{
	
	public int		payload	= 0;
	
	public int		raw		= 0;
	
	public int		packets	= 0;
	private long	lastReset;
						
						
	
	public Statistics()
	{
		lastReset = System.nanoTime();
	}
	
	
	
	public Statistics(final Statistics org)
	{
		payload = org.payload;
		raw = org.raw;
		packets = org.packets;
		lastReset = org.lastReset;
	}
	
	
	
	public void reset()
	{
		payload = 0;
		raw = 0;
		packets = 0;
		
		lastReset = System.nanoTime();
	}
	
	
	
	public long getLastResetTimestamp()
	{
		return lastReset;
	}
	
	
	
	public Statistics substract(final Statistics stat)
	{
		final Statistics ret = new Statistics();
		ret.payload = payload - stat.payload;
		ret.raw = raw - stat.raw;
		ret.packets = packets - stat.packets;
		
		return ret;
	}
	
	
	
	public Statistics add(final Statistics stat)
	{
		final Statistics ret = new Statistics();
		ret.payload = payload + stat.payload;
		ret.raw = raw + stat.raw;
		ret.packets = packets + stat.packets;
		
		return ret;
	}
	
	
	
	public double getOverheadPercentage()
	{
		if (raw == 0)
		{
			return 0;
		}
		
		return (1.0f - (((double) payload) / ((double) raw)));
	}
	
	
	
	public double getLoadPercentage(final double passedTime)
	{
		if (passedTime == 0)
		{
			return 0;
		}
		
		return ((raw) / (28800.0f * passedTime));
	}
	
	
	
	public double getLoadPercentageWithLastReset()
	{
		return getLoadPercentage((System.nanoTime() - lastReset) / 1000000000.0);
	}
}
