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



public class ChargeButtonComponent implements Component
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final double	THRESHOLD			= 0.1;
	private final Component			comp;
	private final double				chargeTime;
	private long						timeLastPressed	= 0;
																	
																	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public ChargeButtonComponent(final Component comp, final double chargeTime)
	{
		this.comp = comp;
		this.chargeTime = chargeTime;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public float getDeadZone()
	{
		return comp.getDeadZone();
	}
	
	
	@Override
	public Identifier getIdentifier()
	{
		return comp.getIdentifier();
	}
	
	
	@Override
	public String getName()
	{
		return comp.getName();
	}
	
	
	@Override
	public float getPollData()
	{
		final float poll = comp.getPollData();
		if (chargeTime < 0.001)
		{
			return poll;
		}
		if (poll > THRESHOLD)
		{
			if (timeLastPressed == 0)
			{
				timeLastPressed = System.nanoTime();
			}
			return 0;
		} else if (timeLastPressed != 0)
		{
			double timeDiff = (System.nanoTime() - timeLastPressed) / 1e9;
			timeDiff = Math.min(timeDiff, chargeTime);
			timeLastPressed = 0;
			return (float) (timeDiff / chargeTime);
		}
		timeLastPressed = 0;
		return 0;
	}
	
	
	@Override
	public boolean isAnalog()
	{
		return comp.isAnalog();
	}
	
	
	@Override
	public boolean isRelative()
	{
		return comp.isRelative();
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
