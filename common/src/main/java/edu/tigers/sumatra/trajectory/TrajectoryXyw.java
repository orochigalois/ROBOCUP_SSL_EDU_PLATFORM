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

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



@Persistent
public class TrajectoryXyw implements ITrajectory<IVector3>
{
	private final ITrajectory<IVector2>	trajXy;
	private final ITrajectory<Double>	trajW;
													
													
	@SuppressWarnings("unused")
	private TrajectoryXyw()
	{
		this(null, null);
	}
	
	
	
	public TrajectoryXyw(final ITrajectory<IVector2> trajXy, final ITrajectory<Double> trajW)
	{
		this.trajXy = trajXy;
		this.trajW = trajW;
	}
	
	
	@Override
	public IVector3 getPositionMM(final double t)
	{
		return new Vector3(trajXy.getPositionMM(t), trajW.getPositionMM(t));
	}
	
	
	@Override
	public IVector3 getPosition(final double t)
	{
		return new Vector3(trajXy.getPosition(t), trajW.getPosition(t));
	}
	
	
	@Override
	public IVector3 getVelocity(final double t)
	{
		return new Vector3(trajXy.getVelocity(t), trajW.getVelocity(t));
	}
	
	
	@Override
	public IVector3 getAcceleration(final double t)
	{
		return new Vector3(trajXy.getAcceleration(t), trajW.getAcceleration(t));
	}
	
	
	@Override
	public double getTotalTime()
	{
		return Math.max(trajXy.getTotalTime(), trajW.getTotalTime());
	}
}
