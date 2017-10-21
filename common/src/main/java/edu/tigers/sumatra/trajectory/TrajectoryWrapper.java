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
public class TrajectoryWrapper<RETURN_TYPE> implements ITrajectory<RETURN_TYPE>
{
	private final ITrajectory<RETURN_TYPE>	traj;
	private final double							tStart;
	private final double							tEnd;
	
	
	@SuppressWarnings("unused")
	private TrajectoryWrapper()
	{
		traj = null;
		tStart = 0;
		tEnd = 0;
	}
	
	
	
	public TrajectoryWrapper(final ITrajectory<RETURN_TYPE> traj, final double tStart, final double tEnd)
	{
		this.traj = traj;
		this.tStart = tStart;
		this.tEnd = tEnd;
	}
	
	
	private double getTime(final double t)
	{
		return Math.min(Math.max(0, t) + tStart, tEnd);
	}
	
	
	@Override
	public RETURN_TYPE getPositionMM(final double t)
	{
		return traj.getPositionMM(getTime(t));
	}
	
	
	@Override
	public RETURN_TYPE getPosition(final double t)
	{
		return traj.getPosition(getTime(t));
	}
	
	
	@Override
	public RETURN_TYPE getVelocity(final double t)
	{
		return traj.getVelocity(getTime(t));
	}
	
	
	@Override
	public RETURN_TYPE getAcceleration(final double t)
	{
		return traj.getAcceleration(getTime(t));
	}
	
	
	@Override
	public double getTotalTime()
	{
		return tEnd - tStart;
	}
	
}
