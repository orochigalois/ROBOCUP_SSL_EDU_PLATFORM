/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.presenter.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;



public class LogEventBuffer implements Iterable<LoggingEvent>
{
	private static final int							NUM_EVENTS				= 6;
	private static final int							PER_LEVEL_CAPACITY	= 1000;
	private final List<LoggingEvent>					eventStorage			= new ArrayList<LoggingEvent>(NUM_EVENTS
																									* PER_LEVEL_CAPACITY);
																									
	private final Map<Level, List<LoggingEvent>>	eventsPerLevel			= new HashMap<>();
																							
	private boolean										freeze					= false;
	private int												freezeIdx				= 0;
																							
																							
	
	public void append(final LoggingEvent ev)
	{
		List<LoggingEvent> levelEventStorage = eventsPerLevel.get(ev.getLevel());
		if (levelEventStorage == null)
		{
			levelEventStorage = new ArrayList<>(PER_LEVEL_CAPACITY);
			eventsPerLevel.put(ev.getLevel(), levelEventStorage);
		}
		if (levelEventStorage.size() >= PER_LEVEL_CAPACITY)
		{
			LoggingEvent oldEv = levelEventStorage.remove(0);
			boolean removed = eventStorage.remove(oldEv);
			assert removed;
			freezeIdx--;
		}
		levelEventStorage.add(ev);
		eventStorage.add(ev);
	}
	
	
	
	public void clear()
	{
		eventStorage.clear();
		for (List<LoggingEvent> l : eventsPerLevel.values())
		{
			l.clear();
		}
		freezeIdx = 0;
	}
	
	
	
	public int size()
	{
		return eventStorage.size();
	}
	
	
	
	public void setFreeze(final boolean freeze)
	{
		this.freeze = freeze;
		if (freeze)
		{
			freezeIdx = eventStorage.size();
		}
	}
	
	
	@Override
	public Iterator<LoggingEvent> iterator()
	{
		return new LogIterator();
	}
	
	private class LogIterator implements Iterator<LoggingEvent>
	{
		private int i = 0;
		
		
		@Override
		public boolean hasNext()
		{
			if (freeze && (i >= freezeIdx))
			{
				return false;
			}
			return i < eventStorage.size();
		}
		
		
		@Override
		public LoggingEvent next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			return eventStorage.get(i++);
		}
	}
}
