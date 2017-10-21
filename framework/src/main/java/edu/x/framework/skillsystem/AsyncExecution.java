/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;



public class AsyncExecution
{
	@SuppressWarnings("unused")
	private static final Logger	log	= Logger.getLogger(AsyncExecution.class.getName());
	
	private final ExecutorService	threadPool;
	private Future<?>					future;
	
	
	
	public AsyncExecution(final ExecutorService threadPool)
	{
		this.threadPool = threadPool;
	}
	
	
	
	public void executeAsynchronously(final Runnable run)
	{
		boolean done = (future != null) && future.isDone();
		
		if ((future != null) && done)
		{
			try
			{
				future.get();
			} catch (InterruptedException err)
			{
				log.error("Interrupted...", err);
			} catch (ExecutionException err)
			{
				log.error("Error during path calculation.", err);
			}
		}
		
		if ((future == null) || done)
		{
			// submit new task, if no other is running
			future = threadPool.submit(run);
		}
	}
}
