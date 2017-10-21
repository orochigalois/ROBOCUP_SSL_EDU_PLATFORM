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

import java.util.function.Function;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class Vector3 extends AVector3
{
	private double	x;
	private double	y;
	private double	z;
						
						
	
	public Vector3()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	
	
	public Vector3(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	
	public Vector3(final IVector2 xy, final double z)
	{
		set(xy, z);
	}
	
	
	
	public Vector3(final IVector original)
	{
		set(original);
	}
	
	
	
	public Vector3(final double[] arr)
	{
		if (arr.length != 3)
		{
			throw new IllegalArgumentException("Invalid input size");
		}
		x = arr[0];
		y = arr[1];
		z = arr[2];
	}
	
	
	
	public static IVector3 valueOf(final String value)
	{
		return AVector.valueOf(value).getXYZVector();
	}
	
	
	
	public void set(final IVector2 original, final double z)
	{
		x = original.x();
		y = original.y();
		this.z = z;
	}
	
	
	
	public void set(final IVector original)
	{
		x = original.x();
		y = original.y();
		z = original.z();
	}
	
	
	
	public void set(final int i, final double value)
	{
		switch (i)
		{
			case 0:
				x = value;
				break;
			case 1:
				y = value;
				break;
			case 2:
				z = value;
				break;
			default:
				throw new IllegalArgumentException("Invalid index: " + i);
		}
	}
	
	
	
	public Vector3 add(final IVector vector)
	{
		x += vector.x();
		y += vector.y();
		z += vector.z();
		return this;
	}
	
	
	
	public Vector3 subtract(final IVector vector)
	{
		x -= vector.x();
		y -= vector.y();
		z -= vector.z();
		return this;
	}
	
	
	
	public Vector3 multiply(final double f)
	{
		x *= f;
		y *= f;
		z *= f;
		
		return this;
	}
	
	
	
	public Vector3 mutiply(final IVector vec)
	{
		x *= vec.x();
		y *= vec.y();
		z *= vec.z();
		
		return this;
	}
	
	
	
	public Vector3 apply(final Function<Double, Double> function)
	{
		x = function.apply(x);
		y = function.apply(y);
		z = function.apply(z);
		return this;
	}
	
	
	@Override
	public Vector3 getXYZVector()
	{
		return this;
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
