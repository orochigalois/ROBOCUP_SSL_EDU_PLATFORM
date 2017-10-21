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
public class Vector3f extends AVector3
{
	private final double	x;
	private final double	y;
	private final double	z;
	
	
	
	public Vector3f(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	
	public Vector3f(final IVector2 xy, final double z)
	{
		x = xy.x();
		y = xy.y();
		this.z = z;
	}
	
	
	
	public Vector3f(final IVector2 original)
	{
		this(original, 0);
	}
	
	
	
	public Vector3f(final IVector3 original)
	{
		x = original.x();
		y = original.y();
		z = original.z();
	}
	
	
	
	public Vector3f()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	
	@Override
	public Vector3 getXYZVector()
	{
		return new Vector3(this);
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
	
	
	@Override
	public double z()
	{
		return z;
	}
}
