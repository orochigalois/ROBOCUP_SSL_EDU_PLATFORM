/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.sampler.velocity;

import java.util.Random;

import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3;



public class VelocityXywRandomSampler implements IVelocityXywSampler
{
	private Random		rnd			= new Random();
	private double		maxXyVel		= 1.5;
	private double		maxWVel		= 5;
	private final int	numSamples	= 50;
	private int			i				= 0;
	
	
	@Override
	public IVector3 getNextVelocity()
	{
		i++;
		double angle = rnd.nextDouble() * AngleMath.PI_TWO;
		double vel = rnd.nextDouble() * maxXyVel;
		IVector2 xyVel = new Vector2(angle).scaleTo(vel);
		double aVel = (rnd.nextGaussian() * maxWVel) / 2.0;
		IVector3 rndVel = new Vector3(xyVel, aVel);
		return rndVel;
	}
	
	
	
	@Override
	public int getNeededSamples()
	{
		return numSamples;
	}
	
	
	
	public void setSeed(final long seed)
	{
		rnd = new Random(seed);
	}
	
	
	
	public final double getMaxXyVel()
	{
		return maxXyVel;
	}
	
	
	
	public final void setMaxXyVel(final double maxXyVel)
	{
		this.maxXyVel = maxXyVel;
	}
	
	
	
	public final double getMaxWVel()
	{
		return maxWVel;
	}
	
	
	
	public final void setMaxWVel(final double maxWVel)
	{
		this.maxWVel = maxWVel;
	}
	
	
	@Override
	public boolean hasNext()
	{
		return i < numSamples;
	}
}
