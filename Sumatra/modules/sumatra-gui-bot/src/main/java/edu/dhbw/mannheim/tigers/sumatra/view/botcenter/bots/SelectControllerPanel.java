/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerCtrlSetControllerType.EControllerType;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemQuery.EQueryType;
import net.miginfocom.swing.MigLayout;



public class SelectControllerPanel extends JPanel
{
	
	public interface ISelectControllerPanelObserver
	{
		
		void onNewControllerSelected(EControllerType type);
		
		
		
		void onQuery(EQueryType queryType);
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long									serialVersionUID	= -1550931078238006617L;
	
	private JComboBox<EControllerType>						controller			= null;
	
	private final List<ISelectControllerPanelObserver>	observers			= new CopyOnWriteArrayList<ISelectControllerPanelObserver>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public SelectControllerPanel()
	{
		setLayout(new MigLayout("wrap 1", "", ""));
		
		controller = new JComboBox<EControllerType>(EControllerType.values());
		controller.addActionListener(new SaveControllerType());
		
		JButton btnQuery = new JButton("Query controller type");
		btnQuery.addActionListener(new QueryListener());
		
		add(controller);
		add(btnQuery);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addObserver(final ISelectControllerPanelObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final ISelectControllerPanelObserver observer)
	{
		observers.remove(observer);
	}
	
	
	private void notifyNewControllerSelected(final EControllerType type)
	{
		for (ISelectControllerPanelObserver observer : observers)
		{
			observer.onNewControllerSelected(type);
		}
	}
	
	
	private void notifyQuery(final EQueryType queryType)
	{
		for (ISelectControllerPanelObserver observer : observers)
		{
			observer.onQuery(queryType);
		}
	}
	
	
	
	public void setControllerType(final EControllerType t)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				if (controller.getSelectedIndex() != t.getId())
				{
					controller.setSelectedIndex(t.getId());
				}
			}
		});
	}
	
	private class SaveControllerType implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			notifyNewControllerSelected((EControllerType) controller.getSelectedItem());
		}
	}
	
	
	private class QueryListener implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			notifyQuery(EQueryType.CTRL_TYPE);
		}
	}
}
