/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.humanref;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import edu.tigers.autoref.view.humanref.components.GameEventPanel;
import edu.tigers.autoreferee.engine.events.IGameEvent;



public class PassiveHumanRefPanel extends BaseHumanRefPanel
{
	private static final long		serialVersionUID	= -3474020682223691827L;
	
	private JPanel						containerPanel;
	private List<GameEventPanel>	eventPanels			= new ArrayList<>();
	
	
	
	public PassiveHumanRefPanel()
	{
		
	}
	
	
	@Override
	protected void setupGUI()
	{
		containerPanel = new JPanel();
		containerPanel.setLayout(new MigLayout("fillx, wrap 1", "[fill]", ""));
		
		super.setupGUI();
	}
	
	
	@Override
	protected void fillHorizontalLayout()
	{
		super.fillHorizontalLayout();
		fillPanel();
	}
	
	
	@Override
	protected void fillVerticalLayout()
	{
		super.fillVerticalLayout();
		fillPanel();
	}
	
	
	private void fillPanel()
	{
		JScrollPane scrollPane = new EventsPane(containerPanel);
		JPanel contentPanel = getContentPanel();
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	
	private void ensureEventListSize(final int size)
	{
		int diff = eventPanels.size() - size;
		if (diff > 0)
		{
			for (int i = 0; i < diff; i++)
			{
				GameEventPanel panel = eventPanels.get(eventPanels.size());
				eventPanels.remove(panel);
				containerPanel.remove(panel);
			}
		} else if (diff < 0)
		{
			for (int i = 0; i < Math.abs(diff); i++)
			{
				GameEventPanel panel = new GameEventPanel(largeFont, regularFont);
				eventPanels.add(panel);
				containerPanel.add(panel);
			}
		}
	}
	
	
	
	public void setEvents(final List<IGameEvent> events)
	{
		int size = events.size();
		ensureEventListSize(size);
		for (int i = 0; i < size; i++)
		{
			eventPanels.get(i).setEvent(events.get(i));
		}
		repaint();
	}
	
	
	
	private static class EventsPane extends JScrollPane
	{
		
		
		private static final long	serialVersionUID	= 757531195811568553L;
		
		
		
		public EventsPane(final Component view)
		{
			super(view, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			setBorder(BorderFactory.createEmptyBorder());
		}
		
		
		
		@Override
		public void paint(final Graphics g)
		{
			super.paint(g);
			
			if ((g instanceof Graphics2D))
			{
				Graphics2D g2 = (Graphics2D) g;
				
				Color opaque = getBackground();
				Color transparent = new Color(255, 255, 255, 0);
				
				int containerWidth = getWidth();
				int containerHeight = getHeight();
				int width = containerWidth;
				int height = (int) (containerHeight * 0.2f);
				int x = 0;
				int y = containerHeight - height;
				
				if (height > 0)
				{
					LinearGradientPaint paint = new LinearGradientPaint(x, y, x, y + height, new float[] { 0.0f, 0.7f },
							new Color[] { transparent, opaque });
					g2.setPaint(paint);
					g2.fillRect(x, y, width, height);
				}
			}
		}
	}
}
