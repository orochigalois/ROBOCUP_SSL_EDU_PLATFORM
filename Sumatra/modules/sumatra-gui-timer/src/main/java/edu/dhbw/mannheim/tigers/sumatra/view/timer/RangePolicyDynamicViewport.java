/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.timer;

import info.monitorenter.gui.chart.rangepolicies.ARangePolicy;
import info.monitorenter.util.Range;



public class RangePolicyDynamicViewport extends ARangePolicy
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final long	serialVersionUID	= 8636803160639955055L;
	private final double			width;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public RangePolicyDynamicViewport(final double width)
	{
		this.width = width;
		setRange(new Range(0, width));
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public double getMax(final double chartMin, final double chartMax)
	{
		return Math.max(chartMax, width);
	}
	
	
	@Override
	public double getMin(final double chartMin, final double chartMax)
	{
		return Math.max(chartMax - width, 0.0);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
