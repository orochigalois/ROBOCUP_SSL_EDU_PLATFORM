/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.math;

import java.util.function.Function;



public interface IVector3 extends IVector
{
	@Override
	default int getNumDimensions()
	{
		return 3;
	}
	
	
	@Override
	Vector3 addNew(IVector vector);
	
	
	@Override
	Vector3 subtractNew(IVector vector);
	
	
	@Override
	Vector3 multiplyNew(IVector vector);
	
	
	@Override
	Vector3 multiplyNew(double f);
	
	
	@Override
	Vector3 normalizeNew();
	
	
	@Override
	Vector3 absNew();
	
	
	@Override
	Vector3 applyNew(Function<Double, Double> function);
	
	
	
	Vector3 turnAroundZNew(double angle);
}
