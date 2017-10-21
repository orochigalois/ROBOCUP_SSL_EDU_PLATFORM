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

import java.util.ArrayList;
import java.util.List;



public final class SumatraMath
{
	
	public static final double		EQUAL_TOL		= 0.000001;
																
	
	public static final double		PI					= Math.PI;
																
	// --------------------------------------------------------------------------
	// --- Factorial ------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final int		FACTORIAL_MAX	= 10;
	private static final long[]	FACTORIALS		= new long[FACTORIAL_MAX + 1];
																
																
	// Static initialization of the Lookup-array
	static
	{
		long n = 1;
		FACTORIALS[0] = n;
		for (int i = 1; i <= FACTORIAL_MAX; i++)
		{
			n *= i;
			FACTORIALS[i] = n;
		}
	}
	
	
	private SumatraMath()
	{
	
	}
	
	
	// --------------------------------------------------------------------------
	// --- Basic conversion functions -------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static double sqrt(final double number)
	{
		return Math.sqrt(number);
	}
	
	
	
	public static double abs(final double number)
	{
		return Math.abs(number);
	}
	
	
	
	public static double cos(final double number)
	{
		return Math.cos(number);
	}
	
	
	
	public static double sin(final double number)
	{
		return Math.sin(number);
	}
	
	
	
	public static double exp(final double exponent)
	{
		return Math.exp(exponent);
	}
	
	
	
	public static boolean isZero(final double x, final double epsilon)
	{
		if ((x > -epsilon) && (x < epsilon))
		{
			return true;
		}
		return false;
	}
	
	
	
	public static boolean isZero(final double x)
	{
		return isZero(x, EQUAL_TOL);
	}
	
	
	
	public static long faculty(final int n) throws MathException
	{
		if (n > FACTORIAL_MAX)
		{
			throw new MathException("AIMath.faculty is limited to FACTORIAL_MAX; if you need more, change it! ;-)");
		} else if (n < 0)
		{
			throw new MathException("AIMath.faculty: Can't calculate faculty of a negative number!");
		} else
		{
			return FACTORIALS[n];
		}
	}
	
	
	
	public static double sign(final double f)
	{
		return f < 0 ? -1 : 1;
	}
	
	
	
	public static boolean isPositive(final double f)
	{
		return f >= 0 ? true : false;
	}
	
	
	
	public static boolean allTheSame(final boolean... values)
	{
		if (values.length == 0)
		{
			return true;
		}
		final boolean ref = values[0];
		for (final boolean b : values)
		{
			if (b != ref)
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	public static double square(final double x)
	{
		return x * x;
	}
	
	
	
	public static double cubic(final double x)
	{
		return x * x * x;
	}
	
	
	
	public static double min(final double... values)
	{
		if (values.length == 0)
		{
			throw new IllegalArgumentException("No values");
		}
		
		double minimum = values[0];
		
		for (final double f : values)
		{
			if (f < minimum)
			{
				minimum = f;
			}
		}
		
		return minimum;
	}
	
	
	
	public static double max(final double... values)
	{
		if (values.length == 0)
		{
			throw new IllegalArgumentException("No values");
		}
		
		double maximum = values[0];
		
		for (final double f : values)
		{
			if (f > maximum)
			{
				maximum = f;
			}
		}
		
		return maximum;
	}
	
	
	
	public static boolean hasDigitsAfterDecimalPoint(final double number)
	{
		final double numberInt = Math.ceil(number);
		
		if (isEqual(number, numberInt))
		{
			return false;
		}
		return true;
	}
	
	
	
	public static boolean isEqual(final double a, final double b, final double tolerance)
	{
		return Math.abs(a - b) < tolerance;
	}
	
	
	
	public static boolean isEqual(final double a, final double b)
	{
		return Math.abs(a - b) < EQUAL_TOL;
	}
	
	
	
	public static boolean isBetween(final double x, final double min, final double max)
	{
		boolean result;
		if (max > min)
		{
			result = (x >= min) && (x <= max);
		} else
		{
			result = (x >= max) && (x <= min);
		}
		
		return result;
	}
	
	
	
	public static double map(double x, final double in_min, final double in_max, final double out_min,
			final double out_max)
	{
		if (x < in_min)
		{
			x = in_min;
		}
		
		if (x < in_min)
		{
			x = in_max;
		}
		
		return (((x - in_min) * (out_max - out_min)) / (in_max - in_min)) + out_min;
	}
	
	
	
	public static double mean(final List<Double> values)
	{
		double sum = 0;
		for (Double f : values)
		{
			sum += f;
		}
		return sum / values.size();
	}
	
	
	
	public static double meanInt(final List<Integer> values)
	{
		double sum = 0;
		for (Integer f : values)
		{
			sum += f;
		}
		return sum / values.size();
	}
	
	
	
	public static double variance(final List<Double> values)
	{
		double mu = mean(values);
		List<Double> val2 = new ArrayList<Double>(values.size());
		for (Double f : values)
		{
			double diff = f - mu;
			val2.add(diff * diff);
		}
		return mean(val2);
	}
	
	
	
	public static double std(final List<Double> values)
	{
		return sqrt(variance(values));
	}
}