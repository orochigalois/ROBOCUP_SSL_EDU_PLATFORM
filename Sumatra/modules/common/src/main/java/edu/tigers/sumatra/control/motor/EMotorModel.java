/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.control.motor;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;
import com.github.g3force.instanceables.InstanceableParameter;



public enum EMotorModel implements IInstanceableEnum
{
	
	MATRIX(new InstanceableClass(MatrixMotorModel.class)),
	
	COMBINATION(new InstanceableClass(CombinationMotorModel.class)),
	
	RANDOM(new InstanceableClass(RandomMotorModel.class)),
	
	GP_MATLAB(new InstanceableClass(GpMatlabMotorModel.class)),
	
	INTERPOLATION(new InstanceableClass(InterpolationMotorModel.class, new InstanceableParameter(String.class, "file",
			"gp2.interpol")));
	
	private final InstanceableClass	impl;
	
	
	
	private EMotorModel(final InstanceableClass impl)
	{
		this.impl = impl;
	}
	
	
	@Override
	public InstanceableClass getInstanceableClass()
	{
		return impl;
	}
}
