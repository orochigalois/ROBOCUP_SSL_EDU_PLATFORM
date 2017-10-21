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

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;



public final class TrajectoryMath
{
	private static final double DT_PRECISE = 0.01;
	
	
	private TrajectoryMath()
	{
	}
	
	
	
	public static double timeAfterDrivenWay(final ITrajectory<IVector3> spline, final double drivenWay)
	{
		return timeAfterDrivenWay(spline, drivenWay, DT_PRECISE);
	}
	
	
	
	public static double timeAfterDrivenWay(final ITrajectory<IVector3> spline, final double drivenWay, final double dt)
	{
		double length = 0;
		double drivenWayMeters = drivenWay / 1000;
		for (double t = 0.0; t < spline.getTotalTime(); t += dt)
		{
			length += spline.getVelocity(t).getLength2() * dt;
			if (length >= drivenWayMeters)
			{
				return t;
			}
		}
		return spline.getTotalTime();
	}
	
	
	
	public static double length(final ITrajectory<IVector3> spline)
	{
		return length(spline, DT_PRECISE);
	}
	
	
	
	public static double length(final ITrajectory<IVector3> spline, final double dt)
	{
		double length = 0;
		for (double t = 0.0; t < spline.getTotalTime(); t += dt)
		{
			length += spline.getVelocity(t).getLength2() * dt;
		}
		return length * 1000;
	}
	
	
	
	public static double timeNearest2Point(final ITrajectory<IVector3> spline, final IVector2 p)
	{
		return timeNearest2Point(spline, p, 0.1);
	}
	
	
	
	public static double timeNearest2Point(final ITrajectory<IVector3> spline, final IVector2 p, final double dt)
	{
		double nearestDist = Double.MAX_VALUE;
		double time = 0;
		assert Double.isFinite(spline.getTotalTime());
		for (double t = 0; t < spline.getTotalTime(); t += dt)
		{
			IVector2 p2 = spline.getPositionMM(t).getXYVector();
			double dist = GeoMath.distancePP(p, p2);
			if (dist < nearestDist)
			{
				nearestDist = dist;
				time = t;
			}
		}
		return time;
	}
}
