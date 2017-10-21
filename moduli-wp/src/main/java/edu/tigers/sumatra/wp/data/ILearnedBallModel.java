/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import edu.tigers.sumatra.math.IVector2;



public interface ILearnedBallModel
{
	
	public IVector2 getPosByTime(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			double time);
			
			
	
	public double getDistByTime(final IVector2 currentVel, final IVector2 initialVel, double time);
	
	
	
	public IVector2 getPosByVel(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			final double velocity);
			
			
	
	public double getDistByVel(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			double velocity);
			
			
	
	public double getTimeByDist(final double currentVel, final double initialVel, final double dist);
	
	
	
	public double getTimeByVel(final double currentVel, final double initialVel, final double velocity);
	
	
	
	public double getVelByDist(final double currentVel, final double initialVel, final double dist);
	
	
	
	public double getVelForTime(final double endVel, final double time);
	
	
	
	public double getVelForDist(final double dist, final double endVel);
	
}
