/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.trajectory;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class TrajectoryWithTime<RETURN_TYPE> extends TrajWTimeImpl<RETURN_TYPE, ITrajectory<RETURN_TYPE>>
{
	@SuppressWarnings("unused")
	private TrajectoryWithTime()
	{
		super();
	}
	
	
	
	public TrajectoryWithTime(final ITrajectory<RETURN_TYPE> trajectory, final long tStart)
	{
		super(trajectory, tStart);
	}
}
