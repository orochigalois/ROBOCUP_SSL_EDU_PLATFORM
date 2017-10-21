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

import org.apache.commons.math3.linear.RealVector;



public class VectorN extends AVectorN
{
	private final double[] data;
	
	
	
	public VectorN(final int dim)
	{
		data = new double[dim];
	}
	
	
	
	public VectorN(final double[] data)
	{
		assert data != null;
		this.data = data;
	}
	
	
	
	public VectorN(final IVector vector)
	{
		data = new double[vector.getNumDimensions()];
		for (int i = 0; i < vector.getNumDimensions(); i++)
		{
			data[i] = vector.get(i);
		}
	}
	
	
	
	public VectorN(final RealVector rv)
	{
		data = new double[rv.getDimension()];
		for (int i = 0; i < rv.getDimension(); i++)
		{
			data[i] = rv.getEntry(i);
		}
	}
	
	
	
	public void set(final int i, final double value)
	{
		data[i] = value;
	}
	
	
	
	public VectorN add(final IVector vec)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] += vec.get(i);
		}
		return this;
	}
	
	
	
	public VectorN sub(final IVector vec)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] -= vec.get(i);
		}
		return this;
	}
	
	
	
	public VectorN multiply(final IVector vec)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] *= vec.get(i);
		}
		return this;
	}
	
	
	
	public VectorN multiply(final double factor)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] *= factor;
		}
		return this;
	}
	
	
	
	public VectorN apply(final Function<Double, Double> function)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] = function.apply(get(i));
		}
		return this;
	}
	
	
	@Override
	public double x()
	{
		return data[0];
	}
	
	
	@Override
	public double y()
	{
		return data[1];
	}
	
	
	@Override
	public double get(final int i)
	{
		return data[i];
	}
	
	
	@Override
	public int getNumDimensions()
	{
		return data.length;
	}
}
