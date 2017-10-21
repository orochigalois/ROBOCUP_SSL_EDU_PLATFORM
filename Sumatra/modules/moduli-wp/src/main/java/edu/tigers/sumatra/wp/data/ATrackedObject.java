/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.io.Serializable;
import java.util.Comparator;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.AObjectID;
import edu.tigers.sumatra.math.IVector2;



@Persistent(version = 2)
public abstract class ATrackedObject implements ITrackedObject
{
	private final long	timestamp;
	
	
	
	protected ATrackedObject()
	{
		timestamp = 0;
	}
	
	
	
	public ATrackedObject(final long timestamp)
	{
		this.timestamp = timestamp;
	}
	
	
	
	public ATrackedObject(final ITrackedObject o)
	{
		this(o.getTimestamp());
	}
	
	
	@Override
	public String toString()
	{
		return "[TrackedObject; pos = " + getPos() + " vel = " + getVel() + "]";
	}
	
	
	
	@Override
	public abstract IVector2 getPos();
	
	
	
	@Override
	public abstract IVector2 getVel();
	
	
	
	@Override
	public abstract AObjectID getBotId();
	
	
	
	public static class TrackedObjectComparator implements Comparator<ITrackedObject>, Serializable
	{
		
		private static final long	serialVersionUID	= -5304247749124149706L;
		
		
		@Override
		public int compare(final ITrackedObject o1, final ITrackedObject o2)
		{
			return o1.getBotId().compareTo(o2.getBotId());
		}
	}
	
	
	
	@Override
	public long getTimestamp()
	{
		return timestamp;
	}
}
