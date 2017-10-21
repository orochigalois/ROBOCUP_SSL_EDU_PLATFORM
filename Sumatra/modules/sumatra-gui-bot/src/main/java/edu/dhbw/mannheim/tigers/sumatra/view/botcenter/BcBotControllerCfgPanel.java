/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.SelectControllerPanel;



public class BcBotControllerCfgPanel extends JPanel
{
	
	private static final long				serialVersionUID			= 2164531377681154919L;
	
	private final SelectControllerPanel	selectControllerPanel	= new SelectControllerPanel();
	
	
	
	public BcBotControllerCfgPanel()
	{
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("general", setupScrollPane(selectControllerPanel));
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	
	private Component setupScrollPane(final Component comp)
	{
		JScrollPane scrollPane = new JScrollPane(comp);
		scrollPane.setPreferredSize(new Dimension(0, 0));
		return scrollPane;
	}
	
	
	
	public SelectControllerPanel getSelectControllerPanel()
	{
		return selectControllerPanel;
	}
}
