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


public interface ITrajectory<RETURN_TYPE>
{
	
	RETURN_TYPE getPositionMM(double t);
	
	
	
	RETURN_TYPE getPosition(double t);
	
	
	
	RETURN_TYPE getVelocity(double t);
	
	
	
	RETURN_TYPE getAcceleration(double t);
	
	
	
	double getTotalTime();
	
	
	
	default RETURN_TYPE getNextDestination(final double t)
	{
		return getPositionMM(getTotalTime());
	}
}
