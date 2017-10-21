/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.g3force.s2vconverter.String2ValueConverter;
import com.sleepycat.persist.model.Persistent;



@Persistent
public class Function1dPoly implements IFunction1D
{
	static
	{
		String2ValueConverter.getDefault().addConverter(new FunctionConverter());
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final double[] a;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	private Function1dPoly()
	{
		a = new double[0];
	}
	
	
	
	public Function1dPoly(final double[] a)
	{
		this.a = Arrays.copyOf(a, a.length);
	}
	
	
	
	public static IFunction1D constant(final double a)
	{
		return new Function1dPoly(new double[] { a });
	}
	
	
	
	public static IFunction1D linear(final double a, final double b)
	{
		return new Function1dPoly(new double[] { a, b });
	}
	
	
	
	public static IFunction1D quadratic(final double a, final double b, final double c)
	{
		return new Function1dPoly(new double[] { a, b, c });
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public double eval(final double... x)
	{
		double xx = 1;
		double result = a[0];
		for (int i = 1; i < a.length; i++)
		{
			xx *= x[0];
			result += a[i] * xx;
		}
		return result;
	}
	
	
	@Override
	public List<Double> getParameters()
	{
		List<Double> params = new ArrayList<Double>();
		
		for (double f : a)
		{
			params.add(f);
		}
		return params;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Function1DPoly[");
		builder.append(a[0]);
		for (int i = 1; i < a.length; i++)
		{
			builder.append(',').append(a[i]);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	@Override
	public EFunction getIdentifier()
	{
		return EFunction.POLY_1D;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
