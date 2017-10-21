/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.presenter;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.IConfigClient;
import com.github.g3force.configurable.IConfigObserver;

import edu.tigers.autoref.model.ballspeed.BallSpeedModel;
import edu.tigers.autoref.view.ballspeed.BallSpeedPanel;
import edu.tigers.autoref.view.ballspeed.IBallSpeedPanelListener;
import edu.tigers.autoref.view.generic.FixedTimeRangeChartPanel;
import edu.tigers.autoreferee.AutoRefConfig;
import edu.tigers.moduli.IModuliStateObserver;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.views.ISumatraView;
import edu.tigers.sumatra.views.ISumatraViewPresenter;
import edu.tigers.sumatra.wp.AWorldPredictor;
import edu.tigers.sumatra.wp.IWorldFrameObserver;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class BallSpeedPresenter implements ISumatraViewPresenter, IWorldFrameObserver, IModuliStateObserver,
		IBallSpeedPanelListener, ActionListener
{
	private enum PauseState
	{
		
		MANUAL,
		
		AUTO,
		
		RUNNING
	}
	
	
	private static final int	chartUpdatePeriod		= 50;
	
	
	private int						timeRange				= 20;
	private boolean				pauseWhenNotRunning	= false;
	private boolean				pauseRequested			= false;
	private boolean				resumeRequested		= false;
	private PauseState			chartState				= PauseState.RUNNING;
	
	private long					curTime					= 0L;
	private BallSpeedModel		model						= new BallSpeedModel();
	
	private Timer					chartTimer;
	private BallSpeedPanel		panel;
	
	
	
	public BallSpeedPresenter()
	{
		panel = new BallSpeedPanel(getTimeRange(), TimeUnit.MILLISECONDS.toNanos(chartUpdatePeriod));
		panel.addObserver(this);
		panel.setMaxBallVelocityLine(AutoRefConfig.getMaxBallVelocity());
		
		chartTimer = new Timer(chartUpdatePeriod, this);
		chartTimer.setDelay(chartUpdatePeriod);
		
		ConfigRegistration.registerConfigurableCallback("autoreferee", new IConfigObserver()
		{
			@Override
			public void afterApply(final IConfigClient configClient)
			{
				panel.setMaxBallVelocityLine(AutoRefConfig.getMaxBallVelocity());
			}
		});
	}
	
	
	@Override
	public Component getComponent()
	{
		return panel;
	}
	
	
	@Override
	public ISumatraView getSumatraView()
	{
		return panel;
	}
	
	
	
	private long getTimeRange()
	{
		return TimeUnit.SECONDS.toNanos(timeRange);
	}
	
	
	@Override
	public void onModuliStateChanged(final ModulesState state)
	{
		Optional<AWorldPredictor> optPredictor = getPredictor();
		
		if (state == ModulesState.ACTIVE)
		{
			optPredictor.ifPresent(predictor -> {
				predictor.addWorldFrameConsumer(this);
				chartTimer.start();
			});
		} else if (state == ModulesState.RESOLVED)
		{
			chartTimer.stop();
			optPredictor.ifPresent(predictor -> predictor.removeWorldFrameConsumer(this));
		}
	}
	
	
	@Override
	public void onNewWorldFrame(final WorldFrameWrapper wFrameWrapper)
	{
		EventQueue.invokeLater(() -> model.update(wFrameWrapper));
	}
	
	
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		updateChart();
		model.reset();
	}
	
	
	private void updateChart()
	{
		updateChartState();
		
		if (chartState == PauseState.RUNNING)
		{
			curTime += TimeUnit.MILLISECONDS.toNanos(chartUpdatePeriod);
			panel.addPoint(curTime, model.getLastBallSpeed());
		}
	}
	
	
	
	private void updateChartState()
	{
		if (model.hasGameStateChanged() && pauseWhenNotRunning && (chartState != PauseState.MANUAL))
		{
			
			if (model.getLastState() == EGameStateNeutral.RUNNING)
			{
				chartState = PauseState.RUNNING;
			} else
			{
				chartState = PauseState.AUTO;
			}
		}
		
		
		if (pauseRequested)
		{
			chartState = PauseState.MANUAL;
			pauseRequested = false;
		}
		
		if (resumeRequested)
		{
			chartState = PauseState.RUNNING;
			resumeRequested = false;
		}
	}
	
	
	private Optional<AWorldPredictor> getPredictor()
	{
		try
		{
			AWorldPredictor predictor = (AWorldPredictor) SumatraModel.getInstance().getModule(AWorldPredictor.MODULE_ID);
			return Optional.of(predictor);
		} catch (ModuleNotFoundException e)
		{
		}
		return Optional.empty();
	}
	
	
	@Override
	public void pauseButtonPressed()
	{
		pauseRequested = true;
	}
	
	
	@Override
	public void resumeButtonPressed()
	{
		resumeRequested = true;
	}
	
	
	@Override
	public void stopChartValueChanged(final boolean value)
	{
		pauseWhenNotRunning = value;
		
		
		if (pauseWhenNotRunning)
		{
			if ((model.getLastState() != EGameStateNeutral.RUNNING) && (chartState == PauseState.RUNNING))
			{
				chartState = PauseState.AUTO;
			}
		} else
		{
			if (chartState == PauseState.AUTO)
			{
				chartState = PauseState.RUNNING;
			}
		}
	}
	
	
	@Override
	public void timeRangeSliderValueChanged(final int value)
	{
		timeRange = value;
		panel.setTimeRange(getTimeRange());
		curTime = 0;
	}
	
	
}
