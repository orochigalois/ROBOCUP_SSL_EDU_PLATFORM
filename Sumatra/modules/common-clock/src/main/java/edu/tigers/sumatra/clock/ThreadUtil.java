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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;



public final class ThreadUtil
{
	
	private ThreadUtil()
	{
	
	}
	
	
	
	public static void parkNanosSafe(final long sleepTotal)
	{
		final long sleepStart = System.nanoTime();
		long stillSleep = sleepTotal;
		
		do
		{
			LockSupport.parkNanos(stillSleep);
			long timeSinceStart = System.nanoTime() - sleepStart;
			stillSleep = sleepTotal - timeSinceStart;
		} while (stillSleep > 0);
	}
	
	
	
	public static boolean parkNanosSafe(final long sleepTotal, final AtomicBoolean cancelSwitch)
	{
		final long sleepStart = System.nanoTime();
		long stillSleep = sleepTotal;
		
		boolean sleptEnough = false;
		
		do
		{
			// Sleep
			LockSupport.parkNanos(stillSleep);
			
			// Check:
			if (cancelSwitch.get())
			{
				return sleptEnough;
			}
			
			// Check if sleptEnough
			long timeSinceStart = System.nanoTime() - sleepStart;
			stillSleep = sleepTotal - timeSinceStart;
			sleptEnough = stillSleep <= 0;
			
		} while (!sleptEnough);
		
		return sleptEnough;
	}
}
