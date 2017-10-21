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

import java.util.EnumSet;
import java.util.Set;

import com.github.g3force.configurable.ConfigRegistration;

import edu.tigers.autoreferee.engine.events.IGameEventDetector;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public abstract class AGameEventDetector implements IGameEventDetector
{
	private final Set<EGameStateNeutral>	activeStates;
	
	
	
	public AGameEventDetector(final EGameStateNeutral gamestate)
	{
		this(EnumSet.of(gamestate));
	}
	
	
	
	public AGameEventDetector(final Set<EGameStateNeutral> activeStates)
	{
		this.activeStates = activeStates;
	}
	
	
	@Override
	public boolean isActiveIn(final EGameStateNeutral state)
	{
		return activeStates.contains(state);
	}
	
	
	protected static void registerClass(final Class<?> clazz)
	{
		ConfigRegistration.registerClass("autoreferee", clazz);
	}
}
