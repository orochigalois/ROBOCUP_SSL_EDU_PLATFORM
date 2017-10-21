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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.CommandPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.ConsolePanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.MovePanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.SkillsPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.SystemMatchFeedbackPanel;
import edu.tigers.sumatra.ids.BotID;



public class BotConfigOverviewPanel extends JPanel
{
	
	private static final long										serialVersionUID			= 1320083400713333902L;
	private final JComboBox<BotID>								cmbBots						= new JComboBox<>();
	
	private final BcBotNetStatsPanel								bcBotNetStatsPanel		= new BcBotNetStatsPanel();
	private final BcBotPingPanel									bcBotPingPanel				= new BcBotPingPanel();
	private final BcBotKickerPanel								bcBotKickerPanel			= new BcBotKickerPanel();
	private final BcBotControllerCfgPanel						bcBotControllerCfgPanel	= new BcBotControllerCfgPanel();
	private final MovePanel											movePanel					= new MovePanel();
	private final SkillsPanel										skillsPanel					= new SkillsPanel();
	private final ConsolePanel										consolePanel				= new ConsolePanel();
	private final CommandPanel										commandPanel				= new CommandPanel();
	private final SystemMatchFeedbackPanel						systemFeedbackPanel		= new SystemMatchFeedbackPanel();
	private final BotConfigPanel									configPanel					= new BotConfigPanel();
	
	
	private final List<IBotConfigOverviewPanelObserver>	observers					= new CopyOnWriteArrayList<IBotConfigOverviewPanelObserver>();
	
	
	
	public void addObserver(final IBotConfigOverviewPanelObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final IBotConfigOverviewPanelObserver observer)
	{
		observers.remove(observer);
	}
	
	
	private void notifyBotIdSelected(final BotID botId)
	{
		for (IBotConfigOverviewPanelObserver observer : observers)
		{
			observer.onBotIdSelected(botId);
		}
	}
	
	
	
	public BotConfigOverviewPanel()
	{
		setLayout(new BorderLayout());
		JPanel selBotPanel = new JPanel();
		selBotPanel.add(new JLabel("Choose Bot: "));
		cmbBots.setPreferredSize(new Dimension(150, 25));
		cmbBots.addItemListener(new BotIdSelectedActionListener());
		cmbBots.addItem(BotID.get());
		selBotPanel.add(cmbBots);
		add(selBotPanel, BorderLayout.NORTH);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("network", setupScrollPane(bcBotNetStatsPanel));
		tabbedPane.addTab("status", setupScrollPane(systemFeedbackPanel));
		tabbedPane.addTab("kicker", setupScrollPane(bcBotKickerPanel));
		tabbedPane.addTab("move", setupScrollPane(movePanel));
		tabbedPane.addTab("skills", setupScrollPane(skillsPanel));
		tabbedPane.addTab("controller", setupScrollPane(bcBotControllerCfgPanel));
		tabbedPane.addTab("console", consolePanel);
		tabbedPane.addTab("ping", setupScrollPane(bcBotPingPanel));
		tabbedPane.addTab("config", setupScrollPane(configPanel));
		// tabbedPane.addTab("command", setupScrollPane(commandPanel));
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	
	private Component setupScrollPane(final Component comp)
	{
		JScrollPane scrollPane = new JScrollPane(comp);
		scrollPane.setPreferredSize(new Dimension(0, 0));
		return scrollPane;
	}
	
	
	
	public JComboBox<BotID> getCmbBots()
	{
		return cmbBots;
	}
	
	
	
	public BcBotNetStatsPanel getBcBotNetStatsPanel()
	{
		return bcBotNetStatsPanel;
	}
	
	
	
	public BcBotPingPanel getBcBotPingPanel()
	{
		return bcBotPingPanel;
	}
	
	
	
	public BcBotKickerPanel getBcBotKickerPanel()
	{
		return bcBotKickerPanel;
	}
	
	
	
	public BcBotControllerCfgPanel getBcBotControllerCfgPanel()
	{
		return bcBotControllerCfgPanel;
	}
	
	
	
	public MovePanel getMovePanel()
	{
		return movePanel;
	}
	
	
	
	public SkillsPanel getSkillsPanel()
	{
		return skillsPanel;
	}
	
	
	
	public ConsolePanel getConsolePanel()
	{
		return consolePanel;
	}
	
	
	
	public CommandPanel getCommandPanel()
	{
		return commandPanel;
	}
	
	
	
	public final SystemMatchFeedbackPanel getSystemStatusPanel()
	{
		return systemFeedbackPanel;
	}
	
	
	
	public final BotConfigPanel getConfigPanel()
	{
		return configPanel;
	}
	
	
	
	public static interface IBotConfigOverviewPanelObserver
	{
		
		void onBotIdSelected(BotID botId);
	}
	
	private class BotIdSelectedActionListener implements ItemListener
	{
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			BotID botId = (BotID) e.getItem();
			if (botId != null)
			{
				notifyBotIdSelected(botId);
			}
		}
	}
}
