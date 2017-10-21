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
import edu.tigers.sumatra.ml.model.ALearnedModel;



public class ErforcianLearnedBallModel extends ALearnedModel implements ILearnedBallModel
{
	
	
	protected ErforcianLearnedBallModel(final String base, final String identifier)
	{
		super(base, identifier);
	}
	
	
	@Override
	public IVector2 getPosByTime(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			final double time)
	{
		return null;
	}
	
	
	@Override
	public double getDistByTime(final IVector2 currentVel, final IVector2 initialVel, final double time)
	{
		return 0;
	}
	
	
	@Override
	public IVector2 getPosByVel(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			final double velocity)
	{
		return null;
	}
	
	
	@Override
	public double getDistByVel(final IVector2 currentPos, final IVector2 currentVel, final IVector2 initialVel,
			final double velocity)
	{
		return 0;
	}
	
	
	@Override
	public double getTimeByDist(final double currentVel, final double initialVel, final double dist)
	{
		return 0;
	}
	
	
	@Override
	public double getTimeByVel(final double currentVel, final double initialVel, final double velocity)
	{
		return 0;
	}
	
	
	@Override
	public double getVelByDist(final double currentVel, final double initialVel, final double dist)
	{
		return 0;
	}
	
	
	@Override
	public double getVelForTime(final double endVel, final double time)
	{
		return 0;
	}
	
	
	@Override
	public double getVelForDist(final double dist, final double endVel)
	{
		return 0;
	}
	
	
	@Override
	protected double[] getDefaultParams()
	{
		return null;
	}
	
}
