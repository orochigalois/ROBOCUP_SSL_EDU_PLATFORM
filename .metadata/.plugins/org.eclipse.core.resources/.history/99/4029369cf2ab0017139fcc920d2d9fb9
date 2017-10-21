/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.referee;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.tigers.sumatra.views.ISumatraView;
import net.miginfocom.swing.MigLayout;



public class RefereePanel extends JPanel implements ISumatraView
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long				serialVersionUID	= 5362158568331526086L;
	
	private final ShowRefereeMsgPanel	showRefereeMsgPanel;
	private final CreateRefereeMsgPanel	createRefereeMsgPanel;
	private final AutoRefereePanel		autoRefereePanel;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public RefereePanel()
	{
		setLayout(new MigLayout("wrap 1, inset 0", "[grow, fill]", ""));
		JTabbedPane tabs = new JTabbedPane();
		showRefereeMsgPanel = new ShowRefereeMsgPanel();
		createRefereeMsgPanel = new CreateRefereeMsgPanel();
		autoRefereePanel = new AutoRefereePanel();
		tabs.addTab("Messages", showRefereeMsgPanel);
		tabs.addTab("Create own", createRefereeMsgPanel);
		tabs.addTab("Auto Referee", autoRefereePanel);
		this.add(tabs);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void start()
	{
		showRefereeMsgPanel.init();
		createRefereeMsgPanel.init();
		autoRefereePanel.init();
	}
	
	
	
	public void stop()
	{
		showRefereeMsgPanel.deinit();
		createRefereeMsgPanel.deinit();
		autoRefereePanel.deinit();
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ShowRefereeMsgPanel getShowRefereeMsgPanel()
	{
		return showRefereeMsgPanel;
	}
	
	
	
	public CreateRefereeMsgPanel getCreateRefereeMsgPanel()
	{
		return createRefereeMsgPanel;
	}
	
	
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
}
