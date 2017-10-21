/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit;

import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;



public abstract class ASimStopCriterion
{
	private AIInfoFrame	firstFrame;
	private AIInfoFrame	latestFrame;
	private long			starttime;
								
								
	
	public boolean checkStopSimulation(final AIInfoFrame aiFrame)
	{
		if (firstFrame == null)
		{
			firstFrame = aiFrame;
			starttime = aiFrame.getWorldFrame().getTimestamp();
			return false;
		}
		latestFrame = aiFrame;
		return checkStopSimulation();
	}
	
	
	protected abstract boolean checkStopSimulation();
	
	
	
	protected long getRuntime(final long timestamp)
	{
		return timestamp - starttime;
	}
	
	
	
	protected AIInfoFrame getFirstFrame()
	{
		return firstFrame;
	}
	
	
	
	protected AIInfoFrame getLatestFrame()
	{
		return latestFrame;
	}
	
	
	
	protected long getStarttime()
	{
		return starttime;
	}
}
