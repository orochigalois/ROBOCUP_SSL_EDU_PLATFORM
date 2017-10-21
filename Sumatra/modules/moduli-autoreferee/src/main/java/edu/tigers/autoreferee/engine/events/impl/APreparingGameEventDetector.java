/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public abstract class APreparingGameEventDetector extends AGameEventDetector
{
	
	private boolean	firstUpdate	= true;
	
	
	
	public APreparingGameEventDetector(final EGameStateNeutral gamestate)
	{
		super(gamestate);
	}
	
	
	
	public APreparingGameEventDetector(final Set<EGameStateNeutral> gamestates)
	{
		super(gamestates);
	}
	
	
	@Override
	public final Optional<IGameEvent> update(final IAutoRefFrame frame, final List<IGameEvent> violations)
	{
		if (firstUpdate)
		{
			prepare(frame);
			firstUpdate = false;
		}
		return doUpdate(frame, violations);
	}
	
	
	protected abstract void prepare(IAutoRefFrame frame);
	
	
	protected abstract Optional<IGameEvent> doUpdate(IAutoRefFrame frame, List<IGameEvent> violations);
	
	
	@Override
	public final void reset()
	{
		firstUpdate = true;
		doReset();
	}
	
	
	protected void doReset()
	{
	}
	
}
