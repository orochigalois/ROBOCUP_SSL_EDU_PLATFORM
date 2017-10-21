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

import java.util.Map;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.tigers.sumatra.ids.BotID;
import net.miginfocom.swing.MigLayout;



public class OverviewPanel extends JPanel
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long			serialVersionUID	= -3183090653608159807L;
	private final Map<BotID, JPanel>	botPanels			= new TreeMap<BotID, JPanel>(BotID.getComparator());
	private boolean						active				= false;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public OverviewPanel()
	{
		setLayout(new MigLayout("fill", "", ""));
		
		setActive(false);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void setActive(final boolean active)
	{
		this.active = active;
		
		updatePanels();
	}
	
	
	
	public void addBotPanel(final BotID botID, final JPanel panel)
	{
		synchronized (botPanels)
		{
			botPanels.put(botID, panel);
		}
		updatePanels();
	}
	
	
	
	public void removeBotPanel(final BotID botID)
	{
		JPanel panel = botPanels.get(botID);
		if (panel != null)
		{
			panel.setEnabled(false);
		}
		
		updatePanels();
	}
	
	
	
	public void removeAllBotPanels()
	{
		synchronized (botPanels)
		{
			botPanels.clear();
		}
		updatePanels();
	}
	
	
	
	public JPanel getBotPanel(final BotID botId)
	{
		return botPanels.get(botId);
	}
	
	
	private void updatePanels()
	{
		final JPanel panel = this;
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				removeAll();
				
				synchronized (botPanels)
				{
					if (active && !botPanels.isEmpty())
					{
						for (final JPanel panel : botPanels.values())
						{
							if (panel.isEnabled())
							{
								add(panel, "wrap, gapbottom 0");
							}
						}
					} else
					{
						add(new JLabel("No bots connected."), "wrap");
					}
				}
				
				add(Box.createGlue(), "push");
				
				SwingUtilities.updateComponentTreeUI(panel);
			}
		});
	}
}
