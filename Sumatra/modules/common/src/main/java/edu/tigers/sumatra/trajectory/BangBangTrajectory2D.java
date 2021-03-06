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
import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public class BangBangTrajectory2D implements ITrajectory<IVector2>
{
	private BangBangTrajectory1D	x;
	private BangBangTrajectory1D	y;
											
											
	@SuppressWarnings("unused")
	private BangBangTrajectory2D()
	{
	}
	
	
	
	public BangBangTrajectory2D(final IVector2 initialPos, final IVector2 finalPos,
			final IVector2 initialVel, final double maxAcc, final double maxBrk, final double maxVel)
	{
		generateTrajectory(initialPos, finalPos, initialVel, maxAcc, maxBrk, maxVel);
	}
	
	
	
	public BangBangTrajectory2D(final BangBangTrajectory1D x, final BangBangTrajectory1D y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	private void generateTrajectory(final IVector2 s0, final IVector2 s1, final IVector2 v0, final double acc,
			final double brk,
			final double vmax)
	{
		double inc = Math.PI / 8.0;
		double alpha = Math.PI / 4.0;
		
		// binary search, some iterations (fixed)
		while (inc > 0.0001)
		{
			double cA = Math.cos(alpha);
			double sA = Math.sin(alpha);
			
			x = new BangBangTrajectory1D(s0.x(), s1.x(), v0.x(), acc * cA, brk * cA, vmax * cA);
			y = new BangBangTrajectory1D(s0.y(), s1.y(), v0.y(), acc * sA, brk * sA, vmax * sA);
			
			if (x.getTotalTime() > y.getTotalTime())
			{
				alpha = alpha - inc;
			} else
			{
				alpha = alpha + inc;
			}
			
			inc *= 0.5;
		}
	}
	
	
	
	public BangBangTrajectory1D getX()
	{
		return x;
	}
	
	
	
	public BangBangTrajectory1D getY()
	{
		return y;
	}
	
	
	@Override
	public Vector2 getPositionMM(final double t)
	{
		return new Vector2(x.getPositionMM(t), y.getPositionMM(t));
	}
	
	
	@Override
	public Vector2 getPosition(final double t)
	{
		return new Vector2(x.getPosition(t), y.getPosition(t));
	}
	
	
	@Override
	public Vector2 getVelocity(final double t)
	{
		return new Vector2(x.getVelocity(t), y.getVelocity(t));
	}
	
	
	@Override
	public Vector2 getAcceleration(final double t)
	{
		return new Vector2(x.getAcceleration(t), y.getAcceleration(t));
	}
	
	
	
	@Override
	public double getTotalTime()
	{
		return SumatraMath.max(x.getTotalTime(), y.getTotalTime());
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("x:\n");
		sb.append(x.toString());
		sb.append("y:\n");
		sb.append(y.toString());
		return sb.toString();
	}
}
