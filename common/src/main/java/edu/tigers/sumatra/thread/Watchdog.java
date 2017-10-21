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

import java.util.ArrayList;
import java.util.List;



public class Watchdog
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private final List<IWatchdogObserver>	observers		= new ArrayList<IWatchdogObserver>();
	private boolean								reset				= false;
	private int										period			= 1000;
	private Thread									watchdogThread	= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Watchdog()
	{
	}
	
	
	
	public Watchdog(final int period)
	{
		this.period = period;
	}
	
	
	
	public Watchdog(final int period, final IWatchdogObserver o)
	{
		this.period = period;
		
		addObserver(o);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	private void addObserver(final IWatchdogObserver o)
	{
		synchronized (observers)
		{
			observers.add(o);
		}
	}
	
	
	
	public int getPeriod()
	{
		return period;
	}
	
	
	
	public void setPeriod(final int period)
	{
		this.period = period;
	}
	
	
	
	public void start(final IWatchdogObserver o)
	{
		stop();
		
		addObserver(o);
		
		watchdogThread = new Thread(new WatchdogRun(), "Watchdog " + o.getName());
		watchdogThread.start();
	}
	
	
	
	public void reset()
	{
		reset = true;
	}
	
	
	
	public void stop()
	{
		if (watchdogThread != null)
		{
			watchdogThread.interrupt();
			watchdogThread = null;
		}
	}
	
	
	
	public boolean isActive()
	{
		return (watchdogThread != null);
	}
	
	
	protected void timeout()
	{
		watchdogThread = null;
		
		synchronized (observers)
		{
			for (final IWatchdogObserver o : observers)
			{
				o.onWatchdogTimeout();
			}
		}
	}
	
	protected class WatchdogRun implements Runnable
	{
		@Override
		public void run()
		{
			while (!Thread.currentThread().isInterrupted())
			{
				reset = false;
				
				try
				{
					Thread.sleep(period);
				} catch (final InterruptedException err)
				{
					Thread.currentThread().interrupt();
				}
				
				if (!reset)
				{
					timeout();
					Thread.currentThread().interrupt();
				}
			}
			
			observers.clear();
		}
	}
}
