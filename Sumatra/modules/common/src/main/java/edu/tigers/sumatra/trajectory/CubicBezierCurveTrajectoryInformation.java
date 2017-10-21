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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.math.AngleMath;



public class CubicBezierCurveTrajectoryInformation
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	protected static final Logger	log					= Logger.getLogger(CubicBezierCurveTrajectoryInformation.class
																			.getName());
																			
	private final List<Double>		parameterEndTimes	= new ArrayList<Double>();
	private final List<Double>		realEndTimes		= new ArrayList<Double>();
																	
	private static double			maxVelocity			= 2.0;
																	
	private static double			maxRotation			= AngleMath.PI_HALF;
																	
																	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public CubicBezierCurveTrajectoryInformation()
	{
	}
	
	
	
	public double getTotalTime()
	{
		return realEndTimes.get(realEndTimes.size() - 1);
	}
	
	
	
	public double getParameter(final double time)
	{
		if (time > realEndTimes.get(realEndTimes.size() - 1))
		{
			return 1;
		}
		for (int i = 0; i < (realEndTimes.size() - 1); i++)
		{
			double lowerBound = realEndTimes.get(i);
			double upperBound = realEndTimes.get(i + 1);
			
			if ((time > lowerBound) && (time <= upperBound))
			{
				double distBounds = upperBound - lowerBound;
				double progress = (time - lowerBound) / distBounds;
				double param = parameterEndTimes.get(i) +
						((parameterEndTimes.get(i + 1) - parameterEndTimes.get(i)) * progress);
				return param;
			} else if (Math.abs(time - lowerBound) < 0.001)
			{
				return parameterEndTimes.get(i);
			}
		}
		log.error("given value out of bounds! CubicBezierTrajectory - param: " + time + " bounds: " + realEndTimes.get(0)
				+ " " + realEndTimes.get(realEndTimes.size() - 1));
		return 1;
	}
	
	
	
	public double getMaxVelocity()
	{
		return maxVelocity;
	}
	
	
	
	public double getMaxRotation()
	{
		return maxRotation;
	}
	
	
	
	public List<Double> getParameterEndTimes()
	{
		return parameterEndTimes;
	}
	
	
	
	public List<Double> getRealEndTimes()
	{
		return realEndTimes;
	}
}
