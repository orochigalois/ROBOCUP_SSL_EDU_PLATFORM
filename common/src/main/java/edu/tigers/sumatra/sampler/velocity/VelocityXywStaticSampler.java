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

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class VelocityXywStaticSampler implements IVelocityXywSampler
{
	private int							sampleIteration	= 0;
	private int							numSamples			= 20;
	
	private final List<IVector3>	velocities			= new ArrayList<>();
	
	
	
	public VelocityXywStaticSampler()
	{
		// velocities.add(new Vector3(2.0, 0, 0));
		
		for (double s = 1.5; s < 2.7; s += 0.1)
		{
			for (int i = 0; i < 3; i++)
			{
				velocities.add(new Vector3(s, 0, 0));
			}
		}
		numSamples = velocities.size();
	}
	
	
	@Override
	public IVector3 getNextVelocity()
	{
		return velocities.get((sampleIteration++) % velocities.size());
	}
	
	
	
	@Override
	public int getNeededSamples()
	{
		return numSamples;
	}
	
	
	
	public final int getSampleIteration()
	{
		return sampleIteration;
	}
	
	
	
	public final void setSampleIteration(final int sampleIteration)
	{
		this.sampleIteration = sampleIteration;
	}
	
	
	@Override
	public boolean hasNext()
	{
		return sampleIteration < numSamples;
	}
}
