/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;



public class DataSync<TYPE>
{
	private List<DataStore>	buffer	= new LinkedList<>();
	private final int			bufferSize;
	
	
	
	public DataSync(final int bufferSize)
	{
		this.bufferSize = bufferSize;
	}
	
	
	
	public synchronized void add(final long timestamp, final TYPE data)
	{
		DataStore store = new DataStore();
		store.timestamp = timestamp;
		store.data = data;
		if (buffer.size() >= bufferSize)
		{
			buffer.remove(0);
		}
		buffer.add(store);
	}
	
	
	
	public synchronized Optional<TYPE> get(final long timestamp)
	{
		DataStore candidate = null;
		for (DataStore ds : buffer)
		{
			if (ds.timestamp > timestamp)
			{
				if (candidate == null)
				{
					candidate = ds;
				} else
				{
					long diff1 = Math.abs(candidate.timestamp - timestamp);
					long diff2 = Math.abs(ds.timestamp - timestamp);
					if (diff1 > diff2)
					{
						return Optional.of(ds.data);
					}
					return Optional.of(candidate.data);
				}
			}
		}
		if (candidate != null)
		{
			return Optional.of(candidate.data);
		}
		if (!buffer.isEmpty())
		{
			return Optional.of(buffer.get(buffer.size() - 1).data);
		}
		return Optional.empty();
	}
	
	
	private class DataStore
	{
		long	timestamp;
		TYPE	data;
		
	}
}
