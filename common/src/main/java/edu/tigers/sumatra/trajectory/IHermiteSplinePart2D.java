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

import edu.tigers.sumatra.math.IVector2;



public interface IHermiteSplinePart2D extends ITrajectory<IVector2>
{
	
	IVector2 value(final double t);
	
	
	
	IVector2 firstDerivative(final double t);
	
	
	
	IVector2 secondDerivative(final double t);
	
	
	
	IVector2 thirdDerivative(final double t);
	
	
	
	double getMaxFirstDerivative();
	
	
	
	double getMaxSecondDerivative();
	
	
	
	double getEndTime();
	
	
	@Override
	default IVector2 getPositionMM(final double t)
	{
		return value(t).multiplyNew(1000);
	}
	
	
	@Override
	default IVector2 getPosition(final double t)
	{
		return value(t);
	}
	
	
	@Override
	default IVector2 getVelocity(final double t)
	{
		return firstDerivative(t);
	}
	
	
	@Override
	default IVector2 getAcceleration(final double t)
	{
		return secondDerivative(t);
	}
	
	
	@Override
	default double getTotalTime()
	{
		return getEndTime();
	}
}
