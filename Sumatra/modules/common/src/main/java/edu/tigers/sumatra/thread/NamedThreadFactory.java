/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;



public class NamedThreadFactory implements ThreadFactory
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final String							name;
	private final UncaughtExceptionHandler	handler;
	
	private int										counter	= 0;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public NamedThreadFactory(final String name, final UncaughtExceptionHandler handler)
	{
		this.name = name;
		this.handler = handler;
	}
	
	
	
	public NamedThreadFactory(final String name)
	{
		this(name, null);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public Thread newThread(final Runnable r)
	{
		String postFix = "";
		if (counter > 0)
		{
			postFix = "-" + counter;
		}
		Thread thread = new Thread(r, name + postFix);
		if (handler != null)
		{
			thread.setUncaughtExceptionHandler(handler);
		}
		counter++;
		return thread;
	}
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
