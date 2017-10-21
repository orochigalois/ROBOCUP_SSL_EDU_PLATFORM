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
public class TrajWTimeImpl<RETURN_TYPE, TRAJ extends ITrajectory<RETURN_TYPE>>
{
	private final TRAJ	trajectory;
	private final long	tStart;
	
	
	@SuppressWarnings("unused")
	protected TrajWTimeImpl()
	{
		trajectory = null;
		tStart = 0;
	}
	
	
	
	public TrajWTimeImpl(final TRAJ trajectory, final long tStart)
	{
		super();
		assert trajectory != null;
		this.trajectory = trajectory;
		this.tStart = tStart;
	}
	
	
	
	public TRAJ getTrajectory()
	{
		return trajectory;
	}
	
	
	
	public long gettStart()
	{
		return tStart;
	}
	
	
	
	public double getTrajectoryTime(final long tCur)
	{
		return Math.max(0, Math.min((tCur - tStart) / 1e9, trajectory.getTotalTime()));
	}
	
	
	
	public double getRemainingTrajectoryTime(final long tCur)
	{
		return Math.max(0, trajectory.getTotalTime() - getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getPositionMM(final long tCur)
	{
		return trajectory.getPositionMM(getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getPosition(final long tCur)
	{
		return trajectory.getPosition(getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getVelocity(final long tCur)
	{
		return trajectory.getVelocity(getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getAcceleration(final long tCur)
	{
		return trajectory.getAcceleration(getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getNextDestination(final long tCur)
	{
		return trajectory.getNextDestination(getTrajectoryTime(tCur));
	}
	
	
	
	public RETURN_TYPE getFinalDestination()
	{
		return trajectory.getPositionMM(trajectory.getTotalTime());
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[traj=");
		builder.append(trajectory);
		builder.append(", tStart=");
		builder.append(tStart);
		builder.append("]");
		return builder.toString();
	}
}
