/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.clock;


import org.apache.log4j.Logger;



public final class Dormouse implements Runnable
{
	// Logger
	private static final Logger		log		= Logger.getLogger(Dormouse.class.getName());
	
	private static volatile Dormouse	instance	= null;
	
	
	
	public static Dormouse getInstance()
	{
		if (instance == null)
		{
			instance = new Dormouse();
		}
		return instance;
	}
	
	
	private Dormouse()
	{
		
	}
	
	
	@Override
	public void run()
	{
		Thread.currentThread().setName("Dormouse");
		log.trace("Dormouse started...");
		try
		{
			Thread.sleep(Long.MAX_VALUE);
		} catch (final InterruptedException err)
		{
			// This line is intentionally left blank
		}
		log.trace("Dormouse stopped.");
	}
}
