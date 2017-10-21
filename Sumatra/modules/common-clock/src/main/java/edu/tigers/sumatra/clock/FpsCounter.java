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


public class FpsCounter
{
	private static final int	UPDATE_FREQ	= 10;
	private long					lastTime		= 0;
	private double					fps			= 0;
	private int						counter		= 0;
	private int						updateFreq	= UPDATE_FREQ;
	private long					totalFrames	= 0;
														
														
	
	public FpsCounter()
	{
	}
	
	
	
	public FpsCounter(final int updateFreq)
	{
		this();
		this.updateFreq = updateFreq;
	}
	
	
	
	public boolean newFrame(final long timestamp)
	{
		boolean fpsChanged = false;
		if (counter >= updateFreq)
		{
			double newFps = updateFreq / ((timestamp - lastTime) / 1e9);
			fpsChanged = Math.abs(fps - newFps) > 0.01;
			fps = newFps;
			lastTime = timestamp;
			counter = 0;
		}
		counter++;
		totalFrames++;
		return fpsChanged;
	}
	
	
	
	public double getAvgFps()
	{
		return fps;
	}
	
	
	
	public final long getTotalFrames()
	{
		return totalFrames;
	}
}
