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



@Persistent(version = 1)
public abstract class AVector3 extends AVector implements IVector3
{
	
	public static final Vector3f	X_AXIS		= new Vector3f(1, 0, 0);
	
	public static final Vector3f	Y_AXIS		= new Vector3f(0, 1, 0);
	
	public static final Vector3f	Z_AXIS		= new Vector3f(0, 0, 1);
	
	public static final Vector3f	ZERO_VECTOR	= new Vector3f(0, 0, 0);
	
	
	
	@Override
	public double get(final int i)
	{
		switch (i)
		{
			case 0:
				return x();
			case 1:
				return y();
			case 2:
				return z();
		}
		throw new IllegalArgumentException("Invalid index: " + i);
	}
	
	
	@Override
	public synchronized Vector3 addNew(final IVector vector)
	{
		Vector3 result = new Vector3(this);
		if (vector != null)
		{
			result.add(vector);
		} else
		{
			throw new NullPointerException("Input vector is null");
		}
		
		return result;
	}
	
	
	@Override
	public synchronized Vector3 subtractNew(final IVector vector)
	{
		Vector3 result = new Vector3(this);
		if (vector != null)
		{
			result.subtract(vector);
		} else
		{
			throw new NullPointerException("Input vector is null");
		}
		
		return result;
	}
	
	
	@Override
	public synchronized Vector3 multiplyNew(final double f)
	{
		return new Vector3(x() * f, y() * f, z() * f);
	}
	
	
	@Override
	public synchronized Vector3 multiplyNew(final IVector vector)
	{
		final Vector3 result = new Vector3(
				x() * vector.x(),
				y() * vector.y(),
				z() * vector.z());
		
		return result;
	}
	
	
	@Override
	public synchronized Vector3 absNew()
	{
		return applyNew(f -> Math.abs(f));
	}
	
	
	@Override
	public synchronized Vector3 normalizeNew()
	{
		if (isZeroVector())
		{
			return new Vector3(this);
		}
		final double length = getLength();
		return new Vector3(x() / length, y() / length, z() / length);
	}
	
	
	@Override
	public synchronized Vector3 turnAroundZNew(final double angle)
	{
		double x2 = (x() * AngleMath.cos(angle)) - (y() * AngleMath.sin(angle));
		double y2 = (y() * AngleMath.cos(angle)) + (x() * AngleMath.sin(angle));
		
		return new Vector3(x2, y2, z());
	}
	
	
	@Override
	public synchronized Vector3 applyNew(final Function<Double, Double> function)
	{
		Vector3 result = new Vector3(
				function.apply(x()),
				function.apply(y()),
				function.apply(z()));
		return result;
	}
	
	
	@Override
	public Vector2 getXYVector()
	{
		return new Vector2(x(), y());
	}
}
