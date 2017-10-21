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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;



@Entity(version = 0)
public class BerkeleyLogEvent
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@PrimaryKey(sequence = "ID")
	private int				id;
	
	private final Long	timeStamp;
	private final String	level;
	private final String	thread;
	private final String	clazz;
	private final String	message;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	private BerkeleyLogEvent()
	{
		timeStamp = Long.valueOf(0);
		level = "TRACE";
		thread = "";
		clazz = "";
		message = "";
	}
	
	
	
	public BerkeleyLogEvent(final LoggingEvent event)
	{
		timeStamp = event.getTimeStamp();
		level = event.getLevel().toString();
		thread = event.getThreadName();
		clazz = event.getClass().getName();
		message = event.getMessage().toString();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public final LoggingEvent getLoggingEvent()
	{
		LoggingEvent event = new LoggingEvent(clazz, Logger.getRootLogger(), timeStamp, getLevel(), message, thread,
				null, null, null, null);
		return event;
	}
	
	
	
	public final Long getTimeStamp()
	{
		return timeStamp;
	}
	
	
	
	public final Level getLevel()
	{
		return Level.toLevel(level);
	}
	
	
	
	public final String getThread()
	{
		return thread;
	}
	
	
	
	public final String getClazz()
	{
		return clazz;
	}
	
	
	
	public final String getMessage()
	{
		return message;
	}
	
}
