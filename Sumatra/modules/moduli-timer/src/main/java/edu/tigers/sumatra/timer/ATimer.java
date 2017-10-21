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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.tigers.moduli.AModule;



public abstract class ATimer extends AModule
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	public static final String				MODULE_TYPE	= "ATimer";
	
	public static final String				MODULE_ID	= "timer";
	
	
	private final List<ITimerObserver>	observers	= new CopyOnWriteArrayList<ITimerObserver>();
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addObserver(final ITimerObserver observer)
	{
		synchronized (observers)
		{
			observers.add(observer);
		}
	}
	
	
	
	public void removeObserver(final ITimerObserver observer)
	{
		synchronized (observers)
		{
			observers.remove(observer);
		}
	}
	
	
	
	public final synchronized List<ITimerObserver> getObservers()
	{
		return observers;
	}
}
