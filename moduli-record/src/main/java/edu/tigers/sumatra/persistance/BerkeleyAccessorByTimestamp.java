/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.je.CursorConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;



public class BerkeleyAccessorByTimestamp<FRAME_TYPE>
{
	@SuppressWarnings("unused")
	private static final Logger						log						= Logger
			.getLogger(BerkeleyAccessorByTimestamp.class.getName());
	
	private final PrimaryIndex<Long, FRAME_TYPE>	frameByTimestamp;
	
	private static final long							EXPECTED_FRAME_RATE	= 16;
	
	
	
	public BerkeleyAccessorByTimestamp(final EntityStore store, final Class<FRAME_TYPE> clazz)
	{
		frameByTimestamp = store.getPrimaryIndex(Long.class, clazz);
	}
	
	
	
	public void saveFrames(final Collection<FRAME_TYPE> frames)
	{
		for (FRAME_TYPE frame : frames)
		{
			if (frame == null)
			{
				log.error("null frame! sth is wrong...");
			} else
			{
				try
				{
					frameByTimestamp.put(frame);
				} catch (Exception err)
				{
					log.error("Could not save frame.", err);
				}
			}
		}
	}
	
	
	
	public synchronized FRAME_TYPE get(final long tCur)
	{
		Long key = getKey(tCur);
		if (key == null)
		{
			return null;
		}
		return frameByTimestamp.get(key);
	}
	
	
	
	public synchronized Long getKey(final long tCur)
	{
		long t = tCur - (EXPECTED_FRAME_RATE / 2);
		EntityCursor<Long> keys = frameByTimestamp.keys(null, t, true, null, true,
				CursorConfig.READ_UNCOMMITTED);
		try
		{
			Long key = keys.first();
			if (key == null)
			{
				EntityCursor<Long> cursor = frameByTimestamp.keys(null, CursorConfig.READ_UNCOMMITTED);
				key = cursor.first();
				cursor.close();
			}
			return key;
		} finally
		{
			keys.close();
		}
	}
	
	
	
	public synchronized Long getNextKey(final long key)
	{
		EntityCursor<Long> keys = frameByTimestamp.keys(null, key, false, null, true,
				CursorConfig.READ_UNCOMMITTED);
		try
		{
			Long nextKey = keys.first();
			return nextKey;
		} finally
		{
			keys.close();
		}
	}
	
	
	
	public synchronized Long getPreviousKey(final long key)
	{
		EntityCursor<Long> keys = frameByTimestamp.keys(null, null, true, key, false,
				CursorConfig.READ_UNCOMMITTED);
		try
		{
			Long nextKey = keys.last();
			return nextKey;
		} finally
		{
			keys.close();
		}
	}
	
	
	
	public int size()
	{
		return (int) frameByTimestamp.count();
	}
	
	
	
	public Long getFirstKey()
	{
		EntityCursor<Long> cursor = frameByTimestamp.keys(null,
				CursorConfig.READ_UNCOMMITTED);
		Long key = cursor.first();
		cursor.close();
		return key;
	}
	
	
	
	public Long getLastKey()
	{
		EntityCursor<Long> cursor = frameByTimestamp.keys(null,
				CursorConfig.READ_UNCOMMITTED);
		Long key = cursor.last();
		cursor.close();
		return key;
	}
	
	
	
	public List<Long> getKeys()
	{
		EntityCursor<Long> cursor = frameByTimestamp.keys(null,
				CursorConfig.READ_UNCOMMITTED);
		try
		{
			List<Long> keys = new ArrayList<>(cursor.count());
			return keys;
		} finally
		{
			cursor.close();
		}
	}
}
