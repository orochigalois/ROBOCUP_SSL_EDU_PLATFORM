/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;

import edu.tigers.sumatra.views.ISumatraView;
import edu.tigers.sumatra.visualizer.view.field.FieldPanel;
import edu.tigers.sumatra.visualizer.view.field.IFieldPanel;
import net.miginfocom.swing.MigLayout;



public class VisualizerPanel extends JPanel implements ISumatraView
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long				serialVersionUID	= 2686191777355388548L;
	
	private final FieldPanel				fieldPanel;
	private final RobotsPanel				robotsPanel;
	private final VisualizerOptionsMenu	menuBar;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public VisualizerPanel()
	{
		setLayout(new BorderLayout());
		menuBar = new VisualizerOptionsMenu();
		JPanel panel = new JPanel();
		add(menuBar, BorderLayout.PAGE_START);
		add(panel, BorderLayout.CENTER);
		
		// --- set layout ---
		panel.setLayout(new MigLayout("fill, inset 0", "[min!][max][right]", "[top]"));
		
		// --- init panels ---
		robotsPanel = new RobotsPanel();
		fieldPanel = new FieldPanel();
		
		panel.add(robotsPanel);
		panel.add(fieldPanel, "grow, top");
	}
	
	
	// --------------------------------------------------------------------------
	// --- ISumatraView ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		return null;
	}
	
	
	@Override
	public void onShown()
	{
	}
	
	
	@Override
	public void onHidden()
	{
	}
	
	
	@Override
	public void onFocused()
	{
	}
	
	
	@Override
	public void onFocusLost()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public IFieldPanel getFieldPanel()
	{
		return fieldPanel;
	}
	
	
	
	public RobotsPanel getRobotsPanel()
	{
		return robotsPanel;
	}
	
	
	
	public VisualizerOptionsMenu getOptionsMenu()
	{
		return menuBar;
	}
}
