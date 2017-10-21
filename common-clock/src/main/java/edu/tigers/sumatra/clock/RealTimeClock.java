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


public class RealTimeClock implements IClock
{
	
	@Override
	public long currentTimeMillis()
	{
		return System.currentTimeMillis();
	}
	
	
	@Override
	public long nanoTime()
	{
		return System.nanoTime();
	}
	
	
	@Override
	public void sleep(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException err)
		{
		}
	}
	
}
