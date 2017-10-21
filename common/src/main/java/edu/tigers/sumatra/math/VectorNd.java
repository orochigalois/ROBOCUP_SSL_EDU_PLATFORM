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

import java.util.Arrays;
import java.util.function.Function;



public class VectorNd extends AVectorN
{
	private double[] data;
	
	
	
	public VectorNd()
	{
		this(0);
	}
	
	
	
	public VectorNd(final int dim)
	{
		data = new double[dim];
	}
	
	
	
	public VectorNd(final double... data)
	{
		this.data = data;
	}
	
	
	
	public VectorNd(final IVector vector)
	{
		data = new double[vector.getNumDimensions()];
		for (int i = 0; i < vector.getNumDimensions(); i++)
		{
			data[i] = vector.get(i);
		}
	}
	
	
	
	public void set(final int i, final double value)
	{
		if (i >= getNumDimensions())
		{
			data = Arrays.copyOf(data, i + 1);
		}
		data[i] = value;
	}
	
	
	
	public VectorNd add(final IVector vec)
	{
		for (int i = 0; i < Math.max(getNumDimensions(), vec.getNumDimensions()); i++)
		{
			set(i, get(i) + vec.get(i));
		}
		return this;
	}
	
	
	
	public VectorNd sub(final IVector vec)
	{
		for (int i = 0; i < Math.max(getNumDimensions(), vec.getNumDimensions()); i++)
		{
			set(i, get(i) - vec.get(i));
		}
		return this;
	}
	
	
	
	public VectorNd multiply(final IVector vec)
	{
		for (int i = 0; i < Math.max(getNumDimensions(), vec.getNumDimensions()); i++)
		{
			set(i, get(i) * vec.get(i));
		}
		return this;
	}
	
	
	
	public VectorNd multiply(final double factor)
	{
		for (int i = 0; i < getNumDimensions(); i++)
		{
			data[i] *= factor;
		}
		return this;
	}
	
	
	
	public VectorNd apply(final Function<Double, Double> function)
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
		if (i < getNumDimensions())
		{
			return data[i];
		}
		return 0;
	}
	
	
	@Override
	public int getNumDimensions()
	{
		return data.length;
	}
}
