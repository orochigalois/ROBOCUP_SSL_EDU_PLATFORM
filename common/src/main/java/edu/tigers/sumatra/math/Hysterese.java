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


public class Hysterese
{
	private final double	lowerThreshold;
	private final double	upperThreshold;
	
	private boolean		upper	= false;
	
	
	
	public Hysterese(final double lowerThreshold, final double upperThreshold)
	{
		super();
		this.lowerThreshold = lowerThreshold;
		this.upperThreshold = upperThreshold;
		assert lowerThreshold < upperThreshold;
	}
	
	
	
	public void update(final double value)
	{
		boolean lower = !upper;
		if (lower && (value > upperThreshold))
		{
			upper = true;
		}
		if (upper && (value < lowerThreshold))
		{
			upper = false;
		}
	}
	
	
	
	public boolean isLower()
	{
		return !upper;
	}
	
	
	
	public boolean isUpper()
	{
		return upper;
	}
	
}
