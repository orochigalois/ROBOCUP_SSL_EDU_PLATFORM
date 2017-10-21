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
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.log4j.Logger;

import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.sumatra.clock.Dormouse;



public class SumatraTimer extends ATimer implements ITimer
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final Logger					log			= Logger.getLogger(SumatraTimer.class.getName());
																			
																			
	private Thread										dormouse;
	private final Map<TimerIdentifier, Long>	startTimes	= new ConcurrentHashMap<TimerIdentifier, Long>();
	private final TimerInfo							timings		= new TimerInfo();
																			
																			
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public SumatraTimer(final SubnodeConfiguration subnodeConfiguration)
	{
	
	}
	
	
	// --------------------------------------------------------------------------
	// --- life-cycle -----------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void initModule() throws InitModuleException
	{
		dormouse = new Thread(Dormouse.getInstance(), "Dormouse");
		dormouse.start();
	}
	
	
	@Override
	public void startModule() throws StartModuleException
	{
	}
	
	
	@Override
	public void stopModule()
	{
		dormouse.interrupt();
		
		synchronized (getObservers())
		{
			getObservers().clear();
		}
		
		timings.clear();
		startTimes.clear();
	}
	
	
	@Override
	public void deinitModule()
	{
	}
	
	
	private void addTiming(final TimerIdentifier tId, final long time)
	{
		timings.putTiming(tId, time);
	}
	
	
	
	@Override
	public void stop(final ETimable timable, final long id)
	{
		stop(timable, id, 0);
	}
	
	
	
	@Override
	public void stop(final ETimable timable, final long id, final int customId)
	{
		long stopTime = System.nanoTime();
		TimerIdentifier timerId = new TimerIdentifier(timable, id, customId);
		Long startTime = startTimes.remove(timerId);
		if (startTime == null)
		{
			log.error("Asynchron timer: " + timable + " stop called before start or multiple times.");
			return;
		}
		addTiming(timerId, (stopTime - startTime));
	}
	
	
	
	@Override
	public void start(final ETimable timable, final long id)
	{
		start(timable, id, 0);
	}
	
	
	
	@Override
	public void start(final ETimable timable, final long id, final int customId)
	{
		startTimes.put(new TimerIdentifier(timable, id, customId), System.nanoTime());
	}
	
	
	
	public TimerInfo getTimerInfo()
	{
		return timings;
	}
}
