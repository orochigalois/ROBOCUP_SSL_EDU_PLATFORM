/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.valueobjects;

import java.io.Serializable;
import java.util.Comparator;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.math.ValuePoint;



@Persistent
public class ValueBot
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private BotID													bot						= null;
	private double													value						= 0;
	
	
	public static final Comparator<? super ValueBot>	VALUEHIGHCOMPARATOR	= new ValueHighComparator();
	
	public static final Comparator<? super ValueBot>	VALUELOWCOMPARATOR	= new ValueLowComparator();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@SuppressWarnings("unused")
	private ValueBot()
	{
	}
	
	
	
	public ValueBot(final BotID bot, final double value)
	{
		this.bot = bot;
		this.value = value;
	}
	
	
	
	public ValueBot(final BotID bot)
	{
		this.bot = bot;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public String toString()
	{
		return "(Bot=" + bot.toString() + ",val=" + value + ")";
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ValueBot other = (ValueBot) obj;
		if (!SumatraMath.isEqual(value, other.value))
		{
			return false;
		}
		return true;
	}
	
	
	
	private static class ValueHighComparator implements Comparator<ValueBot>, Serializable
	{
		
		
		private static final long	serialVersionUID	= 1794858044291002364L;
		
		
		@Override
		public int compare(final ValueBot v1, final ValueBot v2)
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
	
	
	private static class ValueLowComparator implements Comparator<ValueBot>, Serializable
	{
		
		
		private static final long	serialVersionUID	= 1794858044291002364L;
		
		
		@Override
		public int compare(final ValueBot v1, final ValueBot v2)
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
	
	
	
	public BotID getBotID()
	{
		return bot;
	}
	
	
}
