/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.timer;

import java.util.Comparator;



public class TimerIdentifier
{
	private final ETimable	timable;
	private final long		id;
	private final int			customId;
	
	
	
	public TimerIdentifier(final ETimable timable, final long id)
	{
		this.timable = timable;
		this.id = id;
		customId = 0;
	}
	
	
	
	public TimerIdentifier(final ETimable timable, final long id, final int customId)
	{
		this.timable = timable;
		this.id = id;
		this.customId = customId;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (id ^ (id >>> 32));
		result = (prime * result) + customId;
		result = (prime * result) + ((timable == null) ? 0 : timable.hashCode());
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
		TimerIdentifier other = (TimerIdentifier) obj;
		if (id != other.id)
		{
			return false;
		}
		if (timable != other.timable)
		{
			return false;
		}
		if (customId != other.customId)
		{
			return false;
		}
		return true;
	}
	
	
	
	public static Comparator<TimerIdentifier> getComparator()
	{
		return new Comparator<TimerIdentifier>()
		{
			
			@Override
			public int compare(final TimerIdentifier o1, final TimerIdentifier o2)
			{
				int cmpid = Long.compare(o1.id, o2.id);
				if (cmpid == 0)
				{
					return o1.timable.compareTo(o2.timable);
				}
				return cmpid;
			}
		};
	}
	
	
	
	public final ETimable getTimable()
	{
		return timable;
	}
	
	
	
	public final long getId()
	{
		return id;
	}
	
	
	
	public final int getCustomId()
	{
		return customId;
	}
}
