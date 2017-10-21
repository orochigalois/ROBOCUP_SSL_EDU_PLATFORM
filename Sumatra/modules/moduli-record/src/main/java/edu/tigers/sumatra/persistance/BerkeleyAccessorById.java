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
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;



public class BerkeleyAccessorById<TYPE>
{
	@SuppressWarnings("unused")
	private static final Logger					log	= Logger.getLogger(BerkeleyAccessorById.class.getName());
	
	private final PrimaryIndex<Integer, TYPE>	elementById;
	
	
	
	public BerkeleyAccessorById(final EntityStore store, final Class<TYPE> clazz)
	{
		elementById = store.getPrimaryIndex(Integer.class, clazz);
	}
	
	
	
	public synchronized void save(final List<TYPE> logEvents)
	{
		if (elementById == null)
		{
			log.error("You are trying to save an logEvent to an old version of a database. How did you do this?");
			return;
		}
		for (TYPE event : logEvents)
		{
			elementById.put(event);
		}
	}
	
	
	
	public synchronized List<TYPE> load()
	{
		if (elementById == null)
		{
			// for compatibility
			log.info("Could not load log events");
			return new ArrayList<TYPE>(0);
		}
		List<TYPE> events = new ArrayList<TYPE>((int) elementById.count());
		EntityCursor<TYPE> cursor = elementById.entities();
		try
		{
			for (TYPE event : cursor)
			{
				events.add(event);
			}
		} finally
		{
			cursor.close();
		}
		return events;
	}
}
