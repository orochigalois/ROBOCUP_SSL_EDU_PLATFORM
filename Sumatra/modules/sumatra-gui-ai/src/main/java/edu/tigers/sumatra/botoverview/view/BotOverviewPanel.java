/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botoverview.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;

import edu.tigers.sumatra.ai.data.BotAiInformation;
import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.botoverview.BotOverviewTableModel;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.views.ISumatraView;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;
import net.miginfocom.swing.MigLayout;



public class BotOverviewPanel extends JPanel implements ISumatraView
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final long				serialVersionUID	= -8536401073164037476L;
	private final BotOverviewTableModel	model;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public BotOverviewPanel()
	{
		setLayout(new MigLayout("fill, inset 0"));
		model = new BotOverviewTableModel();
		JXTable table = new JXTable(model);
		table.setColumnControlVisible(true);
		table.updateUI();
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, "top, grow");
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void update(final VisualizationFrame frame)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				WorldFrame wf = frame.getWorldFrame();
				IBotIDMap<ITrackedBot> bots = wf.getTigerBotsVisible();
				
				// remove vanished bots
				for (BotID botId : model.getBots())
				{
					if ((botId.getTeamColor() == wf.getTeamColor()) && !bots.containsKey(botId))
					{
						model.removeBot(botId);
					}
				}
				
				// add ai info for all known bots
				for (ITrackedBot bot : bots.values())
				{
					BotID botId = bot.getBotId();
					BotAiInformation aiInfo = frame.getAiInfos().get(botId);
					if (aiInfo != null)
					{
						BotOverviewColumn column = new BotOverviewColumn(aiInfo);
						model.putBot(botId, column);
					} else
					{
						model.removeBot(botId);
					}
				}
			}
		});
	}
	
	
	@Override
	public List<JMenu> getCustomMenus()
	{
		final List<JMenu> menus = new ArrayList<JMenu>();
		return menus;
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
	
	
}
