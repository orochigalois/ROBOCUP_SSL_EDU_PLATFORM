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


public class SimulationClock implements IClock
{
	private long			offsetMillis;
	private long			offsetNanos;
	private final double	dt;
	
	
	
	public SimulationClock(final double dt)
	{
		this.dt = dt;
		syncWithRealTime();
	}
	
	
	
	public final void syncWithRealTime()
	{
		offsetMillis = System.currentTimeMillis();
		offsetNanos = System.nanoTime();
	}
	
	
	@Override
	public long currentTimeMillis()
	{
		long elapsed = (long) ((System.currentTimeMillis() - offsetMillis) * dt);
		return offsetMillis + elapsed;
	}
	
	
	@Override
	public long nanoTime()
	{
		long elapsed = (long) ((System.nanoTime() - offsetNanos) * dt);
		return offsetNanos + elapsed;
	}
	
	
	@Override
	public void sleep(final long millis)
	{
		ThreadUtil.parkNanosSafe((long) (millis * dt * 1e6));
	}
}
