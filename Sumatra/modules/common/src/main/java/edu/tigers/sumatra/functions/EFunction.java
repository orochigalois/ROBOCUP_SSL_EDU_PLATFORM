/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.functions;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;
import com.github.g3force.instanceables.InstanceableParameter;



public enum EFunction implements IInstanceableEnum
{
	
	POLY_1D("poly", new InstanceableClass(Function1dPoly.class, new InstanceableParameter(double[].class, "", ""))),
	
	
	POLY_2D("poly2", new InstanceableClass(Function2dPoly.class, new InstanceableParameter(double[].class, "", ""))), ;
	
	private final String					id;
	private final InstanceableClass	instClass;
	
	
	private EFunction(final String id, final InstanceableClass instClass)
	{
		this.id = id;
		this.instClass = instClass;
	}
	
	
	@Override
	public InstanceableClass getInstanceableClass()
	{
		return instClass;
	}
	
	
	
	public final String getId()
	{
		return id;
	}
}
