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

import com.sleepycat.persist.model.Persistent;



@Persistent
public abstract class ALine implements ILine
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	ALine()
	{
		
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public String toString()
	{
		return "Line: (" + supportVector().x() + "," + supportVector().y() + ") + v * (" + directionVector().x() + ","
				+ directionVector().y() + ")";
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public double getSlope() throws MathException
	{
		if (isVertical())
		{
			// Line is parallel to y-axis
			throw new MathException("Can't calculate the slope of a vertical line!");
		}
		if (directionVector().isZeroVector())
		{
			throw new MathException("Can not calculate the slope of a line with zero direction vector!");
		}
		return directionVector().y() / directionVector().x();
	}
	
	
	@Override
	public double getYIntercept() throws MathException
	{
		if (isVertical())
		{
			// Line is parallel to y-axis
			throw new MathException("Can't calculate y-intercept of a vertical line!");
		}
		if (directionVector().isZeroVector())
		{
			// Line is parallel to y-axis
			throw new MathException("Can't calculate y-intercept of a line with zero direction vector!");
		}
		double factor = (-supportVector().x()) / directionVector().x();
		double yIntercept = (factor * directionVector().y()) + supportVector().y();
		return yIntercept;
	}
	
	
	@Override
	public double getYValue(final double x) throws MathException
	{
		return (x * getSlope()) + getYIntercept();
	}
	
	
	@Override
	public double getXValue(final double y) throws MathException
	{
		if (isVertical())
		{
			return supportVector().x();
		} else if (isHorizontal())
		{
			throw new MathException("Can't calculate x-value of a horizontal line!");
		} else
		{
			return (y - getYIntercept()) / getSlope();
		}
	}
	
	
	@Override
	public ILine getOrthogonalLine()
	{
		return new Line(supportVector(), directionVector().getNormalVector());
	}
	
	
	@Override
	public boolean isVertical()
	{
		return directionVector().isVertical();
	}
	
	
	@Override
	public boolean isHorizontal()
	{
		return directionVector().isHorizontal();
	}
	
	
	@Override
	public boolean isPointInFront(final IVector2 point)
	{
		Vector2 b = point.subtractNew(supportVector());
		
		// angle above 90deg
		if (directionVector().normalizeNew().scalarProduct(b.normalize()) < 0)
		{
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public boolean isPointOnLine(final IVector2 point, final double margin)
	{
		IVector2 lp = GeoMath.leadPointOnLine(point, this);
		double dist = GeoMath.distancePP(point, lp);
		if (dist > margin)
		{
			return false;
		}
		double lineLength = directionVector().getLength();
		if (GeoMath.distancePP(lp, supportVector()) > lineLength)
		{
			return false;
		}
		if (GeoMath.distancePP(lp, supportVector().addNew(directionVector())) > lineLength)
		{
			return false;
		}
		return true;
	}
}
