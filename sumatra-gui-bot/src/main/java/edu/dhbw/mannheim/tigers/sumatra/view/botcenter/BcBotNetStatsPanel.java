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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.tigers.sumatra.botmanager.bots.communication.Statistics;
import net.miginfocom.swing.MigLayout;



public class BcBotNetStatsPanel extends JPanel
{
	
	private static final long	serialVersionUID	= 7642097043545288964L;
	
	private final JTextField	rxData				= new JTextField();
	private final JTextField	txData				= new JTextField();
	private final JTextField	rxPackets			= new JTextField();
	private final JTextField	txPackets			= new JTextField();
	
	
	
	public BcBotNetStatsPanel()
	{
		setLayout(new MigLayout("wrap 3", "[50][100,fill]10[100,fill]", "[]20[][]"));
		add(new JLabel("Stats"));
		add(new JLabel("Packets"));
		add(new JLabel("Bytes"));
		add(new JLabel("RX"));
		add(rxPackets);
		add(rxData);
		add(new JLabel("TX"));
		add(txPackets);
		add(txData);
	}
	
	
	
	public void setTxStat(final Statistics stat)
	{
		txPackets.setText(Integer.toString(stat.packets));
		txData.setText(Integer.toString(stat.payload));
	}
	
	
	
	public void setRxStat(final Statistics stat)
	{
		rxPackets.setText(Integer.toString(stat.packets));
		rxData.setText(Integer.toString(stat.payload));
	}
}
