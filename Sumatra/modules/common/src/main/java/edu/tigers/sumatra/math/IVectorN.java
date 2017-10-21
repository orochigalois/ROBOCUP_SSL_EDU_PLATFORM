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



public interface IVectorN extends IVector
{
	@Override
	VectorN addNew(IVector vector);
	
	
	@Override
	VectorN subtractNew(IVector vector);
	
	
	@Override
	VectorN multiplyNew(IVector vector);
	
	
	@Override
	VectorN multiplyNew(double f);
	
	
	@Override
	VectorN normalizeNew();
	
	
	@Override
	VectorN absNew();
	
	
	@Override
	VectorN applyNew(Function<Double, Double> function);
}
