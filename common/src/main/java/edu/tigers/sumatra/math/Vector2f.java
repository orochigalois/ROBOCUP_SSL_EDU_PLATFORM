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
public class Vector2f extends AVector2
{
	private final double	x;
	private final double	y;
	
	
	
	public Vector2f(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	
	public Vector2f(final IVector2 xy)
	{
		x = xy.x();
		y = xy.y();
	}
	
	
	
	public Vector2f()
	{
		x = 0;
		y = 0;
	}
	
	
	@Override
	public Vector2 getXYVector()
	{
		return new Vector2(this);
	}
	
	
	@Override
	public double x()
	{
		return x;
	}
	
	
	@Override
	public double y()
	{
		return y;
	}
}
