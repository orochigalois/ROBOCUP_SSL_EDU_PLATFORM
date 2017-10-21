/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/


package edu.tigers.sumatra.rcm;

import net.java.games.input.Component;



public class DynamicAxis implements Component
{
	// --------------------------------------------------------------------------
	// --- instance variables ---------------------------------------------------
	// --------------------------------------------------------------------------
	private final Component	axis;
	private final double		min;
	private final double		max;
	
	
	// --------------------------------------------------------------------------
	// --- constructor(s) -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public DynamicAxis(final Component comp, final double min, final double max)
	{
		axis = comp;
		this.min = min;
		this.max = max;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public float getDeadZone()
	{
		return axis.getDeadZone();
	}
	
	
	@Override
	public Identifier getIdentifier()
	{
		return axis.getIdentifier();
	}
	
	
	@Override
	public String getName()
	{
		return axis.getName();
	}
	
	
	@Override
	public float getPollData()
	{
		final float poll = axis.getPollData();
		float poll2 = (float) ((poll - min) / (max - min));
		poll2 = Math.max(0, poll2);
		poll2 = Math.min(1, poll2);
		return poll2;
	}
	
	
	@Override
	public boolean isAnalog()
	{
		return axis.isAnalog();
	}
	
	
	@Override
	public boolean isRelative()
	{
		return axis.isRelative();
	}
}
