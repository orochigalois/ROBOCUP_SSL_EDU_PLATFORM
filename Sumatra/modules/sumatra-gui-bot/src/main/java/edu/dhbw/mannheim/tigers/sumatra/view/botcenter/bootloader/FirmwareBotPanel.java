/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bootloader;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.tigers.sumatra.botmanager.bots.Bootloader.EProcessorID;
import edu.tigers.sumatra.ids.BotID;
import net.miginfocom.swing.MigLayout;



public class FirmwareBotPanel extends JPanel
{
	
	private static final long	serialVersionUID	= 3866974299385225909L;
																
	private JLabel					botId					= new JLabel();
	private final JTextField	processor			= new JTextField();
	private final JProgressBar	progress				= new JProgressBar(0, 100);
																
																
	
	public FirmwareBotPanel()
	{
		setLayout(new MigLayout("wrap 4", "[100,fill]10[100,fill]10[200,fill]"));
		
		botId = new JLabel("None", SwingConstants.CENTER);
		botId.setFont(botId.getFont().deriveFont(20.0f));
		
		progress.setStringPainted(true);
		
		add(botId);
		add(processor);
		add(progress);
	}
	
	
	
	public void setProcessorId(final EProcessorID id)
	{
		EventQueue.invokeLater(() -> {
			processor.setText(id.toString());
		});
	}
	
	
	
	public void setProgress(final long current, final long total)
	{
		final double percentage = (current * 100.0) / total;
		
		EventQueue.invokeLater(() -> {
			progress.setValue((int) percentage);
			progress.setString(current + " / " + total);
		});
	}
	
	
	
	public void setBotId(final BotID id)
	{
		EventQueue.invokeLater(() -> {
			botId.setForeground(id.getTeamColor().getColor());
			botId.setText("" + id.getNumber());
		});
	}
}