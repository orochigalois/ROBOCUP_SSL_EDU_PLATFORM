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


import org.apache.commons.math3.linear.RealVector;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class Vector2 extends AVector2
{
	
	private double	x;
	
	private double	y;
						
						
	
	public Vector2()
	{
		setX(0);
		setY(0);
	}
	
	
	
	public Vector2(final double x, final double y)
	{
		setX(x);
		setY(y);
	}
	
	
	
	public Vector2(final double angle)
	{
		setX(1);
		setY(0);
		turnTo(angle);
	}
	
	
	
	public Vector2(final IVector2 original)
	{
		set(original);
	}
	
	
	
	public Vector2(final RealVector vector)
	{
		setX(vector.getEntry(0));
		setY(vector.getEntry(1));
	}
	
	
	
	public static IVector2 valueOf(final String value)
	{
		return AVector.valueOf(value).getXYVector();
	}
	
	
	
	public Vector2 set(final IVector2 original)
	{
		setX(original.x());
		setY(original.y());
		return this;
	}
	
	
	
	public Vector2 setX(final double x)
	{
		this.x = x;
		return this;
	}
	
	
	
	public Vector2 setY(final double y)
	{
		this.y = y;
		return this;
	}
	
	
	
	public void set(final int i, final double value)
	{
		switch (i)
		{
			case 0:
				setX(value);
				break;
			case 1:
				setY(value);
				break;
			default:
				throw new IllegalArgumentException("Invalid index: " + i);
		}
	}
	
	
	
	public Vector2 addX(final double x)
	{
		setX(x() + x);
		return this;
	}
	
	
	
	public Vector2 addY(final double y)
	{
		setY(y() + y);
		return this;
	}
	
	
	
	public Vector2 subX(final double x)
	{
		setX(x() - x);
		return this;
	}
	
	
	
	public Vector2 subY(final double y)
	{
		setY(y() - y);
		return this;
	}
	
	
	
	public Vector2 multX(final double xFactor)
	{
		setX(x() * xFactor);
		return this;
	}
	
	
	
	public Vector2 multY(final double yFactor)
	{
		setY(y() * yFactor);
		return this;
	}
	
	
	
	public Vector2 add(final IVector vector)
	{
		setX(x() + vector.x());
		setY(y() + vector.y());
		return this;
	}
	
	
	
	public Vector2 subtract(final IVector vector)
	{
		setX(x() - vector.x());
		setY(y() - vector.y());
		return this;
	}
	
	
	
	public Vector2 multiply(final double factor)
	{
		setX(x() * factor);
		setY(y() * factor);
		return this;
	}
	
	
	
	public Vector2 scaleTo(final double newLength)
	{
		final double oldLength = getLength2();
		if (oldLength != 0)
		{
			return multiply(newLength / oldLength);
		}
		return this;
	}
	
	
	
	public Vector2 turn(final double angle)
	{
		final double cosA = AngleMath.cos(angle);
		final double sinA = AngleMath.sin(angle);
		
		final double x2 = (x() * cosA) - (y() * sinA);
		final double y2 = (y() * cosA) + (x() * sinA);
		
		setX(x2);
		setY(y2);
		
		return this;
	}
	
	
	
	public Vector2 turnTo(final double angle)
	{
		final double len = getLength2();
		setY(AngleMath.sin(angle) * len);
		setX(AngleMath.cos(angle) * len);
		return this;
	}
	
	
	
	public Vector2 normalize()
	{
		if (!isZeroVector())
		{
			final double length = getLength2();
			x /= length;
			y /= length;
		}
		return this;
	}
	
	
	@Override
	public Vector2 getXYVector()
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
}
