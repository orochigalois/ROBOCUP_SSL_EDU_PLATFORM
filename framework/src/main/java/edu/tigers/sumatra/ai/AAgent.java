/*
 * *********************************************************
 * Copyright (c) 2017 智动天地(北京)科技有限公司
 * All rights reserved.
 * Project: 标准平台决策开发系统
 * Authors:
 * 智动天地(北京)科技有限公司
 * *********************************************************
 */

package edu.tigers.sumatra.ai;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.tigers.moduli.AModule;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.IWorldFrameObserver;


public abstract class AAgent extends AModule implements IWorldFrameObserver
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	public static final String								MODULE_TYPE					= "AAgent";
	
	public static final String								MODULE_ID_YELLOW			= "ai_yellow";
	
	public static final String								MODULE_ID_BLUE				= "ai_blue";
	
	// --- config ---
	
	public static final String								AI_CONFIG_PATH				= "./config/ai/";
	
	
	// --- geometry ---
	
	public static final String								KEY_GEOMETRY_CONFIG		= AAgent.class.getName() + ".geometry";
	
	public static final String								GEOMETRY_CONFIG_PATH		= "./config/geometry/";
	
	public static final String								VALUE_GEOMETRY_CONFIG	= "grSim.xml";
	
	
	private final List<IAIObserver>						observers					= new CopyOnWriteArrayList<>();
	
	private ETeamColor										teamColor					= ETeamColor.UNINITIALIZED;
	
	private final List<IVisualizationFrameObserver>	visObservers				= new CopyOnWriteArrayList<>();
	
	
	public void addVisObserver(final IVisualizationFrameObserver observer)
	{
		visObservers.add(observer);
	}
	
	
	public void removeVisObserver(final IVisualizationFrameObserver observer)
	{
		visObservers.remove(observer);
	}
	
	
	public void addObserver(final IAIObserver o)
	{
		observers.add(o);
	}
	
	
	public void removeObserver(final IAIObserver o)
	{
		observers.remove(o);
	}
	
	
	public final List<IAIObserver> getObservers()
	{
		return observers;
	}
	
	
	public final ETeamColor getTeamColor()
	{
		return teamColor;
	}
	
	
	protected final void setTeamColor(final ETeamColor color)
	{
		teamColor = color;
	}
	
	
	public abstract void reset();
	
	
	protected void notifyNewAIInfoFrame(final AIInfoFrame lastAIInfoframe)
	{
		for (final IAIObserver o : observers)
		{
			o.onNewAIInfoFrame(lastAIInfoframe);
		}
	}
	
	
	protected void notifyNewAIInfoFrameVisualize(final VisualizationFrame lastAIInfoframe)
	{
		if (lastAIInfoframe == null)
		{
			return;
		}
		for (final IVisualizationFrameObserver o : visObservers)
		{
			o.onNewVisualizationFrame(lastAIInfoframe);
		}
	}
	
	
	protected void notifyAIStopped(final ETeamColor teamColor)
	{
		for (final IVisualizationFrameObserver o : visObservers)
		{
			o.onClearVisualizationFrame(teamColor);
		}
	}
	
	
	protected void notifyNewAIExceptionVisualize(final Throwable ex, final VisualizationFrame frame,
			final VisualizationFrame prevFrame)
	{
		for (final IVisualizationFrameObserver o : visObservers)
		{
			o.onAIException(ex, frame, prevFrame);
		}
	}
}
