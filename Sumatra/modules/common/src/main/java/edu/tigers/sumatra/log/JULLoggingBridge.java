/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.log;


import java.io.IOException;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.apache.log4j.Logger;



public class JULLoggingBridge extends Handler
{
	
	
	private final boolean	classname;
	
	private final boolean	format;
	
	
	
	public static void install()
	{
		install(new JULLoggingBridge(true, true));
	}
	
	
	
	public static void install(final JULLoggingBridge handler)
	{
		LogManager.getLogManager().reset();
		LogManager.getLogManager().getLogger("").addHandler(handler);
	}
	
	
	
	public static void uninstall() throws IOException
	{
		LogManager.getLogManager().readConfiguration();
	}
	
	
	
	public JULLoggingBridge(final boolean classname, final boolean format)
	{
		this.classname = classname;
		this.format = format;
	}
	
	
	
	@Override
	public void close()
	{
		// Empty
	}
	
	
	
	@Override
	public void flush()
	{
		// Empty
	}
	
	
	
	protected Logger getPublisher(final LogRecord record)
	{
		String name = null;
		if (classname)
		{
			name = record.getSourceClassName();
		}
		if (name == null)
		{
			name = record.getLoggerName();
		}
		if (name == null)
		{
			name = JULLoggingBridge.class.getName();
		}
		return Logger.getLogger(name);
	}
	
	
	
	@Override
	public final synchronized Level getLevel()
	{
		return Level.ALL;
	}
	
	
	
	@Override
	public void publish(final LogRecord record)
	{
		
		if (record == null)
		{
			return;
		}
		
		final Logger publisher = getPublisher(record);
		// can be null!
		final Throwable thrown = record.getThrown();
		// can be null!
		String message = record.getMessage();
		if (format && (getFormatter() != null))
		{
			try
			{
				message = getFormatter().format(record);
			} catch (final Exception ex)
			{
				reportError(null, ex, ErrorManager.FORMAT_FAILURE);
				return;
			}
		}
		if (message == null)
		{
			return;
		}
		
		if (record.getLevel().intValue() <= Level.FINEST.intValue())
		{
			publisher.trace(message, thrown);
			return;
		}
		
		if (record.getLevel() == Level.FINER)
		{
			publisher.debug(message, thrown);
			return;
		}
		if (record.getLevel() == Level.FINE)
		{
			publisher.debug(message, thrown);
			return;
		}
		
		if (record.getLevel() == Level.CONFIG)
		{
			publisher.info(message, thrown);
			return;
		}
		if (record.getLevel() == Level.INFO)
		{
			publisher.info(message, thrown);
			return;
		}
		
		if (record.getLevel() == Level.WARNING)
		{
			publisher.warn(message);
			return;
		}
		
		if (record.getLevel().intValue() >= Level.SEVERE.intValue())
		{
			publisher.error(message, thrown);
			return;
		}
		
		
		
		publishFallback(record, publisher);
	}
	
	
	
	protected void publishFallback(final LogRecord record, final Logger publisher)
	{
		publisher.debug(record.getMessage(), record.getThrown());
	}
	
}
