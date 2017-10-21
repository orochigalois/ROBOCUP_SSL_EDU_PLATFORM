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

import java.io.Serializable;
import java.util.Comparator;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class ValuePoint extends Vector2
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static final Comparator<? super ValuePoint>	VALUE_HIGH_COMPARATOR	= new ValueHighComparator();
	
	public static final Comparator<? super ValuePoint>	VALUE_LOW_COMPARATOR		= new ValueLowComparator();
	
	public double													value							= 0;
																										
																										
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	protected ValuePoint()
	{
	}
	
	
	
	public ValuePoint(final double x, final double y)
	{
		super(x, y);
	}
	
	
	
	public ValuePoint(final IVector2 vec, final double value)
	{
		super(vec);
		this.value = value;
	}
	
	
	
	public ValuePoint(final IVector2 vec)
	{
		super(vec);
	}
	
	
	
	public ValuePoint(final double x, final double y, final double value)
	{
		super(x, y);
		this.value = value;
	}
	
	
	
	public ValuePoint(final ValuePoint copy)
	{
		super(copy);
		value = copy.value;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public double getValue()
	{
		return value;
	}
	
	
	
	public void setValue(final double value)
	{
		this.value = value;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public synchronized int hashCode()
	{
		long temp;
		temp = Double.doubleToLongBits(value);
		return (int) (temp ^ (temp >>> 32));
	}
	
	
	@Override
	public synchronized String toString()
	{
		return "(x=" + x() + ",y=" + y() + ",val=" + value + ")";
	}
	
	
	@Override
	public synchronized boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (!super.equals(obj) || (getClass() != obj.getClass()))
		{
			return false;
		}
		
		final ValuePoint other = (ValuePoint) obj;
		if (!SumatraMath.isEqual(value, other.value))
		{
			return false;
		}
		return true;
	}
	
	
	private static class ValueHighComparator implements Comparator<ValuePoint>, Serializable
	{
		
		
		private static final long serialVersionUID = 1794858044291002364L;
		
		
		@Override
		public int compare(final ValuePoint v1, final ValuePoint v2)
		{
			if (v1.value < v2.value)
			{
				return 1;
			} else if (v1.value > v2.value)
			{
				return -1;
			} else
			{
				return 0;
			}
		}
	}
	
	
	private static class ValueLowComparator implements Comparator<ValuePoint>, Serializable
	{
		
		
		private static final long serialVersionUID = 1794858044291002364L;
		
		
		@Override
		public int compare(final ValuePoint v1, final ValuePoint v2)
		{
			if (v1.value > v2.value)
			{
				return 1;
			} else if (v1.value < v2.value)
			{
				return -1;
			} else
			{
				return 0;
			}
		}
	}
}