/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import net.miginfocom.swing.MigLayout;
import edu.tigers.autoref.view.main.StartStopPanel.IStartStopPanelObserver;
import edu.tigers.autoreferee.AutoRefModule.AutoRefState;
import edu.tigers.autoreferee.engine.IAutoRefEngine.AutoRefMode;
import edu.tigers.sumatra.components.BasePanel;



public class StartStopPanel extends BasePanel<IStartStopPanelObserver>
{
	
	public interface IStartStopPanelObserver
	{
		
		void onStartButtonPressed();
		
		
		
		void onStopButtonPressed();
		
		
		
		void onPauseButtonPressed();
		
		
		
		void onResumeButtonPressed();
		
	}
	
	
	private static final long			serialVersionUID	= 1L;
	
	private JButton						startButton			= null;
	private JButton						stopButton			= null;
	private JButton						pauseButton			= null;
	private JButton						resumeButton		= null;
	private JComboBox<AutoRefMode>	refModeBox			= null;
	
	
	
	public StartStopPanel()
	{
		setLayout(new MigLayout("", "[16%][16%][16%][16%][16%][16%]", ""));
		
		startButton = new JButton("Start");
		startButton.setEnabled(false);
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				informObserver(obs -> obs.onStartButtonPressed());
			}
		});
		
		stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				informObserver(obs -> obs.onStopButtonPressed());
			}
		});
		
		pauseButton = new JButton("Pause");
		pauseButton.setEnabled(false);
		pauseButton.addActionListener(e -> informObserver(obs -> obs.onPauseButtonPressed()));
		
		resumeButton = new JButton("Resume");
		resumeButton.setEnabled(false);
		resumeButton.addActionListener(e -> informObserver(obs -> obs.onResumeButtonPressed()));
		
		
		refModeBox = new JComboBox<>(AutoRefMode.values());
		
		add(refModeBox, "span 2, grow");
		add(startButton, "span 2, grow");
		add(stopButton, "span 2, grow, wrap");
		add(pauseButton, "span 3, grow");
		add(resumeButton, "span 3, grow");
		
		setBorder(BorderFactory.createTitledBorder("Start/Stop"));
	}
	
	
	
	public void setModeBoxEnabled(final boolean enabled)
	{
		refModeBox.setEnabled(enabled);
	}
	
	
	
	public void setStartButtonEnabled(final boolean enabled)
	{
		startButton.setEnabled(enabled);
	}
	
	
	
	public void setStopButtonEnabled(final boolean enabled)
	{
		stopButton.setEnabled(enabled);
	}
	
	
	
	public void setPauseButtonEnabled(final boolean enabled)
	{
		pauseButton.setEnabled(enabled);
	}
	
	
	
	public void setResumeButtonEnabled(final boolean enabled)
	{
		resumeButton.setEnabled(enabled);
	}
	
	
	
	public AutoRefMode getModeSetting()
	{
		return (AutoRefMode) refModeBox.getSelectedItem();
	}
	
	
	@Override
	public void setPanelEnabled(final boolean enabled)
	{
		if (enabled == false)
		{
			Arrays.asList(startButton, pauseButton, resumeButton, stopButton, refModeBox)
					.forEach(comp -> comp.setEnabled(false));
		}
	}
	
	
	
	public void setState(final AutoRefState state)
	{
		boolean startEnabled = false;
		boolean stopEnabled = false;
		boolean pauseEnabled = false;
		switch (state)
		{
			case RUNNING:
				stopEnabled = true;
				pauseEnabled = true;
				break;
			case PAUSED:
				stopEnabled = true;
				break;
			case STOPPED:
				startEnabled = true;
				break;
			default:
				break;
		}
		
		setStartButtonEnabled(startEnabled);
		setStopButtonEnabled(stopEnabled);
		setModeBoxEnabled(startEnabled);
		setPauseButtonEnabled(stopEnabled && pauseEnabled);
		setResumeButtonEnabled(stopEnabled && !pauseEnabled);
	}
}
