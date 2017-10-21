/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.lookandfeel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;



public final class LookAndFeelStateAdapter implements PropertyChangeListener
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static LookAndFeelStateAdapter			instance		= null;
	private final Set<ILookAndFeelStateObserver>	observers	= new HashSet<ILookAndFeelStateObserver>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	private LookAndFeelStateAdapter()
	{
		UIManager.addPropertyChangeListener(this);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public static synchronized LookAndFeelStateAdapter getInstance()
	{
		if (instance == null)
		{
			instance = new LookAndFeelStateAdapter();
		}
		return instance;
	}
	
	
	
	public void addObserver(final ILookAndFeelStateObserver observer)
	{
		synchronized (observers)
		{
			observers.add(observer);
		}
	}
	
	
	
	public void removeObserver(final ILookAndFeelStateObserver observer)
	{
		synchronized (observers)
		{
			observers.remove(observer);
		}
	}
	
	
	private void notifyLookAndFeelChanged()
	{
		synchronized (observers)
		{
			for (final ILookAndFeelStateObserver observer : observers)
			{
				observer.onLookAndFeelChanged();
			}
		}
	}
	
	
	@Override
	public void propertyChange(final PropertyChangeEvent e)
	{
		if (e.getPropertyName().equals("lookAndFeel"))
		{
			notifyLookAndFeelChanged();
		}
	}
}
