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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.basestation.BaseStationPanel;
import edu.tigers.sumatra.views.ISumatraView;



public class BotCenterPanel extends JPanel implements ISumatraView
{
	
	private static final long					serialVersionUID	= -7749317503520671162L;
																				
	private final OverviewPanel				botOverview;
	private final BotConfigOverviewPanel	botOverviewPanel;
	private final List<BaseStationPanel>	baseStationPanels	= new ArrayList<>();
	private final BcFeaturesPanel				featurePanel;
	private final JTabbedPane					tabbedPane;
														
														
	
	public BotCenterPanel()
	{
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		botOverview = new OverviewPanel();
		botOverviewPanel = new BotConfigOverviewPanel();
		featurePanel = new BcFeaturesPanel();
	}
	
	
	
	public void addBaseStationTab(final BaseStationPanel panel)
	{
		baseStationPanels.add(panel);
		tabbedPane.addTab(panel.getName(), setupScrollPane(panel));
		repaint();
	}
	
	
	
	public void createPanel()
	{
		tabbedPane.addTab("Bot Overview", setupScrollPane(botOverview));
		tabbedPane.addTab("Bot Details", botOverviewPanel);
		
		// tabbedPane.addTab("Features", setupScrollPane(featurePanel));
		
		add(tabbedPane, BorderLayout.CENTER);
		repaint();
	}
	
	
	
	public void clearPanel()
	{
		remove(tabbedPane);
		tabbedPane.removeAll();
		repaint();
	}
	
	
	private Component setupScrollPane(final Component comp)
	{
		JScrollPane scrollPane = new JScrollPane(comp);
		scrollPane.setPreferredSize(new Dimension(0, 0));
		return scrollPane;
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
	
	
	
	public BotConfigOverviewPanel getBotOverviewPanel()
	{
		return botOverviewPanel;
	}
	
	
	
	public List<BaseStationPanel> getBaseStationPanels()
	{
		return baseStationPanels;
	}
	
	
	
	public final OverviewPanel getOverviewPanel()
	{
		return botOverview;
	}
	
	
	
	public final BcFeaturesPanel getFeaturePanel()
	{
		return featurePanel;
	}
}
