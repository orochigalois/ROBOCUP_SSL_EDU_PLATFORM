/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.general;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.event.EGameEvent;
import edu.tigers.sumatra.ai.data.event.GameEventManager;
import edu.tigers.sumatra.ai.data.event.GameEvents;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;



public class GameEventCalc extends ACalculator
{
	GameEventManager	gameEventManager;
	
	
	
	public GameEventCalc()
	{
		Set<EGameEvent> eventsToListenTo = new HashSet<EGameEvent>(Arrays.asList(EGameEvent.values()));
		
		gameEventManager = new GameEventManager(eventsToListenTo);
	}
	
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		gameEventManager.detectEvents(newTacticalField, baseAiFrame);
		
		GameEvents gameEvents = newTacticalField.getGameEvents();
		gameEvents.storedEvents = gameEventManager.getTrackedEvents();
	}
}
