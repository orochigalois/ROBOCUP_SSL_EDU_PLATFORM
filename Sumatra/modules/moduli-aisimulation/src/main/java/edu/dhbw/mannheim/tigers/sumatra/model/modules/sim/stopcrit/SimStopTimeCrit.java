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

import java.util.concurrent.TimeUnit;



public class SimStopTimeCrit extends ASimStopCriterion
{
	private final long timeout;
	
	
	
	public SimStopTimeCrit(final long timeout)
	{
		this.timeout = timeout;
	}
	
	
	@Override
	protected boolean checkStopSimulation()
	{
		long runtimeNs = getRuntime(getLatestFrame().getWorldFrame().getTimestamp());
		return (TimeUnit.NANOSECONDS.toMillis(runtimeNs) >= timeout);
	}
	
	
	
	public final long getTimeout()
	{
		return timeout;
	}
}
