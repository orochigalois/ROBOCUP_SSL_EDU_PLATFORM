/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;

import java.io.Serializable;

import com.sleepycat.persist.model.Persistent;



@Persistent
public abstract class AObjectID implements Comparable<AObjectID>, Serializable
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long	serialVersionUID	= -1210556807036502590L;
	
	
	public static final int		UNINITIALIZED_ID	= 255;
	
	
	protected static final int	BALL_ID				= -1;
	
	
	public static final int		BOT_ID_MIN			= 0;
	
	public static final int		BOT_ID_MAX			= 11;
	
	public static final int		BOT_ID_MAX_BS		= 23;
	
	
	// --------------------------------------------------------------------------
	
	private int						number;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public AObjectID()
	{
		number = UNINITIALIZED_ID;
	}
	
	
	
	public AObjectID(final int number)
	{
		if (number == UNINITIALIZED_ID)
		{
			this.number = number;
		} else
		{
			if ((number == BALL_ID) || ((number >= BOT_ID_MIN) && (number <= BOT_ID_MAX)))
			{
				this.number = number;
			} else
			{
				throw new IllegalArgumentException(" This is not a valid ID/number: " + number);
			}
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public int compareTo(final AObjectID o)
	{
		return Integer.compare(getNumber(), o.getNumber());
	}
	
	
	@Override
	public String toString()
	{
		return "ObjectID[ " + getNumber() + "]";
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public int getNumber()
	{
		return number;
	}
	
	
	
	public boolean isUninitializedID()
	{
		return number == UNINITIALIZED_ID;
	}
	
	
	
	public boolean isBall()
	{
		if (number == BALL_ID)
		{
			return true;
		}
		return false;
	}
	
	
	
	public boolean isBot()
	{
		if ((number >= BOT_ID_MIN) && (number <= BOT_ID_MAX))
		{
			return true;
		}
		return false;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + number;
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
		AObjectID other = (AObjectID) obj;
		if (number != other.number)
		{
			return false;
		}
		return true;
	}
}
