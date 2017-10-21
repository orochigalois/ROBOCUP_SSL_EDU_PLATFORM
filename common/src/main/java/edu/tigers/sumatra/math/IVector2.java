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



public interface IVector2 extends IVector
{
	@Override
	default int getNumDimensions()
	{
		return 2;
	}
	
	
	@Override
	Vector2 addNew(IVector vector);
	
	
	@Override
	Vector2 subtractNew(IVector vector);
	
	
	@Override
	Vector2 multiplyNew(IVector vector);
	
	
	@Override
	Vector2 multiplyNew(double f);
	
	
	@Override
	Vector2 normalizeNew();
	
	
	@Override
	Vector2 absNew();
	
	
	@Override
	Vector2 applyNew(Function<Double, Double> function);
	
	
	
	Vector2 scaleToNew(double newLength);
	
	
	
	Vector2 turnNew(double angle);
	
	
	
	Vector2 turnToNew(double angle);
	
	
	
	boolean isVertical();
	
	
	
	boolean isHorizontal();
	
	
	
	double getAngle();
	
	
	
	double getAngle(double defAngle);
	
	
	
	double scalarProduct(IVector2 v);
	
	
	
	Vector2 getNormalVector();
	
	
	
	Vector2 turnAroundNew(final IVector2 axis, final double angle);
}