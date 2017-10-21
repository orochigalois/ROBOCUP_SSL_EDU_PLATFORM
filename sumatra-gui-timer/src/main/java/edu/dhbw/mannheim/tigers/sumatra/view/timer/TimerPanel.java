/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.timer;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;

import edu.tigers.sumatra.views.ISumatraView;
import net.miginfocom.swing.MigLayout;



public class TimerPanel extends JPanel implements ISumatraView
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long		serialVersionUID	= -4840668605222003132L;
	
	private final TimerChartPanel	chartPanel;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TimerPanel()
	{
		setLayout(new MigLayout("fill, inset 0", "", ""));
		
		chartPanel = new TimerChartPanel();
		chartPanel.setVisible(false);
		
		add(chartPanel, "grow");
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public void onShown()
	{
		chartPanel.setVisible(true);
	}
	
	
	@Override
	public void onHidden()
	{
		// chartPanel.setVisible(false);
	}
	
	
	@Override
	public void onFocused()
	{
		chartPanel.setVisible(true);
	}
	
	
	@Override
	public void onFocusLost()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TimerChartPanel getChartPanel()
	{
		return chartPanel;
	}
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		return null;
	}
}
