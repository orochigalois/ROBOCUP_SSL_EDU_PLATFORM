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

import edu.tigers.sumatra.math.AngleMath;



@Persistent
public class BangBangTrajectory1DOrient extends BangBangTrajectory1D
{
	
	@SuppressWarnings("unused")
	private BangBangTrajectory1DOrient()
	{
	}
	
	
	
	public BangBangTrajectory1DOrient(final double initialPos, final double finalPos, final double initialVel,
			final double maxAcc, final double maxBrk,
			final double maxVel)
	{
		this.initialPos = initialPos;
		this.finalPos = finalPos;
		this.initialVel = initialVel;
		this.maxAcc = maxAcc;
		this.maxBrk = maxBrk;
		this.maxVel = maxVel;
		
		double sDiffShort = AngleMath.normalizeAngle(finalPos - initialPos);
		double sDiffLong;
		if (sDiffShort < 0)
		{
			sDiffLong = sDiffShort + (2 * AngleMath.PI);
		} else
		{
			sDiffLong = sDiffShort - (2 * AngleMath.PI);
		}
		
		generateTrajectory(sDiffLong, initialVel, maxAcc, maxBrk, maxVel);
		double tLong = getTotalTime();
		generateTrajectory(sDiffShort, initialVel, maxAcc, maxBrk, maxVel);
		double tShort = getTotalTime();
		
		if (tLong < tShort)
		{
			generateTrajectory(sDiffLong, initialVel, maxAcc, maxBrk, maxVel);
		}
		
		calcVelPos(initialPos, initialVel);
	}
	
	
	@Override
	public Double getPositionMM(final double t)
	{
		return AngleMath.normalizeAngle(getValuesAtTime(t).pos);
	}
}
