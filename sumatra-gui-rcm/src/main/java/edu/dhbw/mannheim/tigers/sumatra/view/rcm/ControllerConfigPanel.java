/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.rcm;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.dhbw.mannheim.tigers.sumatra.presenter.rcm.IRCMConfigChangedObserver;
import edu.tigers.sumatra.rcm.RcmActionMap;
import edu.tigers.sumatra.rcm.RcmActionMap.ERcmControllerConfig;



public class ControllerConfigPanel extends JPanel
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final long										serialVersionUID	= -7083335280756862591L;
	private final Map<ERcmControllerConfig, JTextField>	textFields			= new HashMap<ERcmControllerConfig, JTextField>();
	private final List<IRCMConfigChangedObserver>			observers			= new CopyOnWriteArrayList<IRCMConfigChangedObserver>();
	
	
	
	public void addObserver(final IRCMConfigChangedObserver observer)
	{
		synchronized (observers)
		{
			observers.add(observer);
		}
	}
	
	
	
	public void removeObserver(final IRCMConfigChangedObserver observer)
	{
		synchronized (observers)
		{
			observers.remove(observer);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public ControllerConfigPanel()
	{
	}
	
	
	
	public final void updateConfig(final RcmActionMap config)
	{
		textFields.clear();
		removeAll();
		for (Map.Entry<ERcmControllerConfig, Double> entry : config.getConfigValues().entrySet())
		{
			JLabel label = new JLabel(entry.getKey().name() + ": ");
			JTextField txtField = new JTextField(entry.getValue().toString(), 4);
			txtField.addFocusListener(new ChangeListener(txtField, entry.getKey()));
			textFields.put(entry.getKey(), txtField);
			add(label);
			add(txtField);
		}
		
	}
	
	
	private class ChangeListener implements FocusListener
	{
		private final JTextField				txtField;
		private final ERcmControllerConfig	config;
		
		
		
		public ChangeListener(final JTextField txtField, final ERcmControllerConfig config)
		{
			super();
			this.txtField = txtField;
			this.config = config;
		}
		
		
		@Override
		public void focusGained(final FocusEvent e)
		{
		}
		
		
		@Override
		public void focusLost(final FocusEvent e)
		{
			try
			{
				double value = Double.valueOf(txtField.getText());
				synchronized (observers)
				{
					for (IRCMConfigChangedObserver o : observers)
					{
						o.onConfigChanged(config, value);
					}
				}
			} catch (NumberFormatException err)
			{
			}
		}
	}
}
