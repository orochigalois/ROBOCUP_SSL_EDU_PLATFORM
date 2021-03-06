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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.tigers.sumatra.botmanager.bots.PingStats;
import net.miginfocom.swing.MigLayout;



public class BcBotPingPanel extends JPanel
{
	
	private static final long							serialVersionUID	= 4531411392390831333L;
	
	private final JTextField							minDelay				= new JTextField();
	private final JTextField							avgDelay				= new JTextField();
	private final JTextField							maxDelay				= new JTextField();
	private final JTextField							lostPings			= new JTextField();
	private final JTextField							numPings				= new JTextField("10");
	private final JTextField							pingPayload			= new JTextField("0");
	private final JButton								startStopPing		= new JButton("Start");
	
	private final List<IBcBotPingPanelObserver>	observers			= new CopyOnWriteArrayList<IBcBotPingPanelObserver>();
	
	
	
	public BcBotPingPanel()
	{
		setLayout(new MigLayout("wrap 2", "[50][100,fill]"));
		
		startStopPing.addActionListener(new StartStopPing());
		
		add(new JLabel("Num Pings:"));
		add(numPings);
		add(new JLabel("Payload:"));
		add(pingPayload);
		add(startStopPing, "span 2");
		add(new JLabel("Min Delay:"));
		add(minDelay);
		add(new JLabel("Avg Delay:"));
		add(avgDelay);
		add(new JLabel("Max Delay:"));
		add(maxDelay);
		add(new JLabel("Lost Pings:"));
		add(lostPings);
	}
	
	
	
	public void addObserver(final IBcBotPingPanelObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final IBcBotPingPanelObserver observer)
	{
		observers.remove(observer);
	}
	
	
	
	public void setPingStats(final PingStats stats)
	{
		minDelay.setText(String.format(Locale.ENGLISH, "%1.3f", stats.minDelay));
		avgDelay.setText(String.format(Locale.ENGLISH, "%1.3f", stats.avgDelay));
		maxDelay.setText(String.format(Locale.ENGLISH, "%1.3f", stats.maxDelay));
		lostPings.setText(Integer.toString(stats.lostPings));
	}
	
	
	private void notifyStartPing(final int numPings, final int payloadSize)
	{
		for (IBcBotPingPanelObserver observer : observers)
		{
			observer.onStartPing(numPings, payloadSize);
		}
	}
	
	
	private void notifyStopPing()
	{
		for (IBcBotPingPanelObserver observer : observers)
		{
			observer.onStopPing();
		}
	}
	
	private class StartStopPing implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			if (startStopPing.getText().equals("Start"))
			{
				int num = 0;
				int payload = 0;
				
				try
				{
					num = Integer.valueOf(numPings.getText());
					payload = Integer.valueOf(pingPayload.getText());
				} catch (final NumberFormatException err)
				{
					return;
				}
				
				notifyStartPing(num, payload);
				
				startStopPing.setText("Stop");
			} else
			{
				notifyStopPing();
				
				startStopPing.setText("Start");
			}
		}
	}
	
	
	
	public static interface IBcBotPingPanelObserver
	{
		
		void onStartPing(final int numPings, final int payloadSize);
		
		
		
		void onStopPing();
	}
}
