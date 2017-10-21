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


public interface ILine
{
	
	IVector2 supportVector();
	
	
	
	IVector2 directionVector();
	
	
	
	double getSlope() throws MathException;
	
	
	
	double getYIntercept() throws MathException;
	
	
	
	double getYValue(double x) throws MathException;
	
	
	
	double getXValue(double y) throws MathException;
	
	
	
	ILine getOrthogonalLine();
	
	
	
	boolean isHorizontal();
	
	
	
	boolean isVertical();
	
	
	
	boolean isPointInFront(IVector2 point);
	
	
	
	boolean isPointOnLine(final IVector2 point, final double margin);
}
