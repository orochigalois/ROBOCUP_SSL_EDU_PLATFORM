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

import java.util.concurrent.TimeUnit;



public class StaticSimulationClock implements IClock
{
	private long	nanotime		= 0;
	private long	milliOffset	= System.currentTimeMillis();
	
	
	
	public final void syncWithRealTime()
	{
		milliOffset = System.currentTimeMillis();
		nanotime = System.nanoTime();
	}
	
	
	
	public final void setNanoTime(final long nanotime)
	{
		this.nanotime = nanotime;
	}
	
	
	@Override
	public long currentTimeMillis()
	{
		return milliOffset + TimeUnit.NANOSECONDS.toMillis(nanotime);
	}
	
	
	@Override
	public long nanoTime()
	{
		return nanotime;
	}
	
	
	
	public void step(final long nanos)
	{
		nanotime += nanos;
	}
	
	
	@Override
	public void sleep(final long millis)
	{
		throw new IllegalStateException("Sleeping is not supported for this implementation.");
	}
}
