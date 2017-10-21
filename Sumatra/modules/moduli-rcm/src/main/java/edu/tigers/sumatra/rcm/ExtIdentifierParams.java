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

import java.util.Locale;



public class ExtIdentifierParams
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private double	minValue;
	private double	maxValue;
	private double	chargeTime;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public ExtIdentifierParams(final double minValue, final double maxValue, final double chargeTime)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.chargeTime = chargeTime;
	}
	
	
	
	public static ExtIdentifierParams createDefault()
	{
		return new ExtIdentifierParams(0, 0, 0);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public static ExtIdentifierParams valueOf(final String str)
	{
		String[] strParts = str.split(",");
		if (strParts.length != 3)
		{
			throw new IllegalArgumentException("Invalid string: " + str);
		}
		return new ExtIdentifierParams(Double.valueOf(strParts[0]), Double.valueOf(strParts[1]),
				Double.valueOf(strParts[2]));
	}
	
	
	
	public String getParseableString()
	{
		return String.format(Locale.ENGLISH, "%.4f,%.4f,%.4f", minValue, maxValue, chargeTime);
	}
	
	
	@Override
	public String toString()
	{
		return String.format(Locale.ENGLISH, "[%.2f;%.2f;%.2f]", minValue, maxValue, chargeTime);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public final double getMinValue()
	{
		return minValue;
	}
	
	
	
	public final double getMaxValue()
	{
		return maxValue;
	}
	
	
	
	public final double getChargeTime()
	{
		return chargeTime;
	}
	
	
	
	public final void setMinValue(final double minValue)
	{
		this.minValue = minValue;
	}
	
	
	
	public final void setMaxValue(final double maxValue)
	{
		this.maxValue = maxValue;
	}
	
	
	
	public final void setChargeTime(final double chargeTime)
	{
		this.chargeTime = chargeTime;
	}
}
