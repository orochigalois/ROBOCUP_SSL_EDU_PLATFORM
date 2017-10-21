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

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;



public class TimerInfo
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private final Map<ETimable, SortedMap<Long, Long>>	timings		= new ConcurrentHashMap<ETimable, SortedMap<Long, Long>>();
	private static final int									BUFFER_SIZE	= 1000;
	
	private final Object											lock			= new Object();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public TimerInfo()
	{
		
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void putTiming(final TimerIdentifier tId, final long value)
	{
		synchronized (lock)
		{
			SortedMap<Long, Long> map = getOrCreateTimings(tId.getTimable());
			Long existingValue = map.get(tId.getId());
			if (existingValue != null)
			{
				map.put(tId.getId(), value + existingValue);
			} else
			{
				map.put(tId.getId(), value);
			}
			if (map.size() > BUFFER_SIZE)
			{
				map.remove(map.firstKey());
			}
		}
	}
	
	
	private SortedMap<Long, Long> getOrCreateTimings(final ETimable timable)
	{
		SortedMap<Long, Long> map = timings.get(timable);
		if (map == null)
		{
			map = new ConcurrentSkipListMap<Long, Long>();
			timings.put(timable, map);
		}
		return map;
	}
	
	
	
	public SortedMap<Long, Long> getTimings(final ETimable timable)
	{
		SortedMap<Long, Long> copy;
		synchronized (lock)
		{
			SortedMap<Long, Long> map = getOrCreateTimings(timable);
			copy = new TreeMap<Long, Long>(map);
		}
		return copy;
	}
	
	
	
	public void clear()
	{
		synchronized (lock)
		{
			timings.clear();
		}
	}
	
	
	
	public SortedMap<Long, Long> getCombinedTimings(final ETimable timable, final int numFrames,
			final ETimerStatistic tStats)
	{
		SortedMap<Long, Long> timings = getTimings(timable);
		if (numFrames <= 1)
		{
			return timings;
		}
		SortedMap<Long, Long> avageres = new TreeMap<Long, Long>();
		int counter = 0;
		long sum = 0;
		long max = 0;
		long min = Long.MAX_VALUE;
		for (Map.Entry<Long, Long> entry : timings.entrySet())
		{
			if (counter >= numFrames)
			{
				long value = 0;
				switch (tStats)
				{
					case AVG:
						value = sum / counter;
						break;
					case MAX:
						value = max;
						break;
					case MIN:
						value = min;
						break;
				}
				avageres.put(entry.getKey(), value);
				sum = 0;
				min = Long.MAX_VALUE;
				max = 0;
				counter = 0;
			}
			if (entry.getValue() > max)
			{
				max = entry.getValue();
			}
			if (entry.getValue() < min)
			{
				min = entry.getValue();
			}
			sum += entry.getValue();
			counter++;
		}
		// the last frames will not be included.
		return avageres;
	}
}
