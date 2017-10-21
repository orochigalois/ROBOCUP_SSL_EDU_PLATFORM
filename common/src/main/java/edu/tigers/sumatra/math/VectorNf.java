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



public class VectorNf extends AVectorN
{
	private final double[] data;
	
	
	
	public VectorNf(final int dim)
	{
		data = new double[dim];
	}
	
	
	
	public VectorNf(final double... data)
	{
		this.data = Arrays.copyOf(data, data.length);
	}
	
	
	
	public VectorNf(final IVector vector)
	{
		data = new double[vector.getNumDimensions()];
		for (int i = 0; i < vector.getNumDimensions(); i++)
		{
			data[i] = vector.get(i);
		}
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
