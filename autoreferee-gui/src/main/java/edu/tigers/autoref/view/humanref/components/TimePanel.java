/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.humanref.components;

import java.awt.Font;
import java.time.Duration;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.tigers.autoref.view.generic.JFormatLabel;
import edu.tigers.sumatra.Referee.SSL_Referee.Stage;



public class TimePanel extends JPanel
{
	
	
	private static final long			serialVersionUID	= -5276347698642647549L;
	
	private JFormatLabel<Stage>		stageLabel;
	private JFormatLabel<Duration>	timeLabel;
	
	
	
	public TimePanel(final Font font)
	{
		setupUI(font);
	}
	
	
	private void setupUI(final Font font)
	{
		stageLabel = new JFormatLabel<>(new StageFormatter());
		stageLabel.setAlignmentX(SwingConstants.CENTER);
		stageLabel.setText("No Stage");
		
		timeLabel = new JFormatLabel<>(null);
		timeLabel.setFormatter(new DurationFormatter(Duration.ofSeconds(30), timeLabel.getForeground()));
		
		timeLabel.setAlignmentX(SwingConstants.CENTER);
		timeLabel.setText("00:00");
		
		setTextFont(font);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(stageLabel);
		add(timeLabel);
	}
	
	
	
	public void setTextFont(final Font font)
	{
		stageLabel.setFont(font.deriveFont(font.getSize() / 2.0f));
		timeLabel.setFont(font);
	}
	
	
	
	public void setTimeLeft(final Duration duration)
	{
		timeLabel.setValue(duration);
	}
	
	
	
	public void setStage(final Stage stage)
	{
		stageLabel.setValue(stage);
	}
	
}
