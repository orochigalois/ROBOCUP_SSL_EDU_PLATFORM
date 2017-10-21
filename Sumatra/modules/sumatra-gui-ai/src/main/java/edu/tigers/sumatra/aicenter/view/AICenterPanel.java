/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JPanel;

import edu.tigers.sumatra.views.ISumatraView;



public class AICenterPanel extends JPanel implements ISumatraView
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long	serialVersionUID	= 8132550010453691515L;
	
	private ModuleControlPanel	modulesPanel		= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public AICenterPanel()
	{
		setLayout(new BorderLayout());
		
		modulesPanel = new ModuleControlPanel();
		add(modulesPanel, BorderLayout.CENTER);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public ModuleControlPanel getModulesPanel()
	{
		return modulesPanel;
	}
	
	
	
	public void clearView()
	{
		modulesPanel.onStop();
	}
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		final List<JMenu> menus = new ArrayList<JMenu>();
		return menus;
	}
	
	
	@Override
	public void onFocused()
	{
	}
	
	
	@Override
	public void onFocusLost()
	{
	}
	
	
	@Override
	public void onShown()
	{
	}
	
	
	@Override
	public void onHidden()
	{
	}
	
	
	
	public final JCheckBox getChkAiActive()
	{
		return modulesPanel.getChkAiActive();
	}
}
