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

import org.junit.Ignore;
import org.junit.Test;

import junit.framework.AssertionFailedError;



@Ignore
public class FpsCounterTest
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final FpsCounter	fpsCounter	= new FpsCounter();
	private static final int	NUM_FRAMES	= 500;
	private static final int	SLEEP_TIME	= 16;
	
	private static final int	TOLERANCE	= 10;
														
														
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Test
	public void testSimple()
	{
		for (int i = 0; i < NUM_FRAMES; i++)
		{
			fpsCounter.newFrame(System.nanoTime());
			
			try
			{
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		double desiredFps = 1000.0 / SLEEP_TIME;
		double avgFps = fpsCounter.getAvgFps();
		if ((avgFps < (desiredFps - TOLERANCE)) || (avgFps > (desiredFps + TOLERANCE)))
		{
			throw new AssertionFailedError("FPS is not correct. avg:" + avgFps + " desired:" + desiredFps);
		}
	}
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
