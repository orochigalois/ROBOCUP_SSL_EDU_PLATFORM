/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.replay;

import java.util.Collections;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;

import edu.tigers.sumatra.views.ISumatraView;
import net.miginfocom.swing.MigLayout;



public class ReplayPanel extends JPanel implements ISumatraView
{
	private static final long			serialVersionUID	= 2558215591773007411L;
	
	private final ReplayControlPanel	controlPanel		= new ReplayControlPanel();
	
	
	
	public ReplayPanel()
	{
		setLayout(new MigLayout("fill"));
		add(controlPanel, "wrap");
	}
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		return Collections.emptyList();
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
	
	
	
	public final ReplayControlPanel getControlPanel()
	{
		return controlPanel;
	}
}
