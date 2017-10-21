/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import edu.tigers.moduli.IModuliStateObserver;
import edu.tigers.moduli.Moduli;
import edu.tigers.moduli.listenerVariables.ModulesState;



public final class ModuliStateAdapter implements PropertyChangeListener
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final Logger					log			= Logger.getLogger(ModuliStateAdapter.class.getName());
	private Moduli										model			= null;
	private static ModuliStateAdapter			instance		= null;
	private final List<IModuliStateObserver>	observers	= new CopyOnWriteArrayList<IModuliStateObserver>();
																			
																			
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	private ModuliStateAdapter()
	{
		model = SumatraModel.getInstance();
		model.getModulesState().addChangeListener(this);
	}
	
	
	
	public static synchronized ModuliStateAdapter getInstance()
	{
		if (instance == null)
		{
			instance = new ModuliStateAdapter();
		}
		
		return instance;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addObserver(final IModuliStateObserver o)
	{
		observers.add(o);
	}
	
	
	
	public void removeObserver(final IModuliStateObserver o)
	{
		observers.remove(o);
	}
	
	
	
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		// --- property "stateApplication" ---
		if (evt.getSource() == model.getModulesState())
		{
			
			final ModulesState newState = (ModulesState) evt.getNewValue();
			
			observers.stream().parallel().forEach(o -> {
				try
				{
					log.trace("Notify " + o.getClass());
					o.onModuliStateChanged(newState);
				} catch (Exception err)
				{
					log.error("Exception while changing moduli state in class " + o.getClass().getName(), err);
				}
			});
		}
	}
}
