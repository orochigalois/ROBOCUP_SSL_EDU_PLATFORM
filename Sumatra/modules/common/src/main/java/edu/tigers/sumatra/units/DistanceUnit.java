/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.units;

import edu.tigers.sumatra.math.IVector2;



public enum DistanceUnit
{
	
	METERS(1f),
	
	
	MILLIMETERS(0.001f);
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final int	METER_TO_MILLIMETER	= 1000;
	private final double			meters;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private DistanceUnit(final double meters)
	{
		this.meters = meters;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public double toMeters(final double value)
	{
		return meters * value;
	}
	
	
	
	public double toMillimeters(final double value)
	{
		return (meters * value) * METER_TO_MILLIMETER;
	}
	
	
	
	public IVector2 toMeters(final IVector2 value)
	{
		return value.multiplyNew(meters);
	}
	
	
	
	public IVector2 toMillimeters(final IVector2 value)
	{
		return value.multiplyNew(meters * METER_TO_MILLIMETER);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final double getMeters()
	{
		return meters;
	}
}
