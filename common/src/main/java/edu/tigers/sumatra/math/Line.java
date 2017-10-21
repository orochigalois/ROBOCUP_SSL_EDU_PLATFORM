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



@Persistent(version = 1)
public class Line extends ALine
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	private final IVector2	supportVector;
	
	
	private final IVector2	directionVector;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	protected Line()
	{
		supportVector = new Vector2();
		directionVector = new Vector2();
	}
	
	
	
	public Line(final ILine l)
	{
		supportVector = new Vector2(l.supportVector());
		directionVector = new Vector2(l.directionVector());
	}
	
	
	
	public Line(final double slope, final double yIntercept)
	{
		supportVector = new Vector2(0, yIntercept);
		directionVector = new Vector2(1, slope);
	}
	
	
	
	public Line(final IVector2 sV, final IVector2 dV)
	{
		supportVector = new Vector2(sV);
		// if (dV.isZeroVector())
		// {
		// throw new IllegalArgumentException("Tried to create a line with a zero-direction vector.");
		// }
		directionVector = new Vector2(dV);
	}
	
	
	
	public static Line newLine(final IVector2 p1, final IVector2 p2)
	{
		IVector2 supportVector = new Vector2f(p1);
		// if (p1.equals(p2))
		// {
		// throw new IllegalArgumentException("Points may not be equal: " + p1);
		// }
		IVector2 directionVector = p2.subtractNew(p1);
		return new Line(supportVector, directionVector);
	}
	
	
	@Override
	public IVector2 supportVector()
	{
		return supportVector;
	}
	
	
	@Override
	public IVector2 directionVector()
	{
		return directionVector;
	}
}
