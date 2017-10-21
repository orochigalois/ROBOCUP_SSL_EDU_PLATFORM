/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.clock;


@Deprecated
public final class SumatraClock
{
	private static IClock clock = new RealTimeClock();
	
	
	private SumatraClock()
	{
	}
	
	
	
	public static void setClock(final IClock clock)
	{
		SumatraClock.clock = clock;
	}
	
	
	
	public static long currentTimeMillis()
	{
		return clock.currentTimeMillis();
	}
	
	
	
	public static long nanoTime()
	{
		return clock.nanoTime();
	}
	
	
	
	public static void sleep(final long millis)
	{
		clock.sleep(millis);
	}
	
	
	
	public static final IClock getClock()
	{
		return clock;
	}
}
