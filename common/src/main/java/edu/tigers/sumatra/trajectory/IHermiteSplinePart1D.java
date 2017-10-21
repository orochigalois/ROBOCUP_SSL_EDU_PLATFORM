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


public interface IHermiteSplinePart1D extends ITrajectory<Double>
{
	
	double[] getA();
	
	
	
	Double value(final double t);
	
	
	
	Double firstDerivative(final double t);
	
	
	
	Double secondDerivative(final double t);
	
	
	
	Double thirdDerivative(final double t);
	
	
	
	double getMaxFirstDerivative();
	
	
	
	double getMaxSecondDerivative();
	
	
	
	double getEndTime();
	
	
	@Override
	default Double getPositionMM(final double t)
	{
		return value(t) * 1000;
	}
	
	
	@Override
	default Double getPosition(final double t)
	{
		return value(t);
	}
	
	
	@Override
	default Double getVelocity(final double t)
	{
		return firstDerivative(t);
	}
	
	
	@Override
	default Double getAcceleration(final double t)
	{
		return secondDerivative(t);
	}
	
	
	@Override
	default double getTotalTime()
	{
		return getEndTime();
	}
}
