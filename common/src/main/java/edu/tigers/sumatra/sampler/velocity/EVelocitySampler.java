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

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;



public enum EVelocitySampler implements IInstanceableEnum
{
	
	RANDOM(new InstanceableClass(VelocityXywRandomSampler.class)),
	
	CONTINOUS(new InstanceableClass(VelocityXywContinousSampler.class)),
	
	STATIC(new InstanceableClass(VelocityXywStaticSampler.class));
	
	private final InstanceableClass	impl;
	
	
	
	private EVelocitySampler(final InstanceableClass impl)
	{
		this.impl = impl;
	}
	
	
	@Override
	public InstanceableClass getInstanceableClass()
	{
		return impl;
	}
}
