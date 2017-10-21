/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.event.impl.MarkingDetector;
import edu.tigers.sumatra.ai.data.event.storage.GameEventStorageBotIDOptimized;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ids.BotID;



public class GameEventManager
{
	Map<EGameEvent, IGameEventStorage>	trackedEvents		= new HashMap<>();
	Map<EGameEvent, IGameEventDetector>	presentDetectors	= new HashMap<EGameEvent, IGameEventDetector>();
	Map<EGameEvent, Set<GameEvent>>		activeEvents		= new HashMap<>();
	
	
	
	public GameEventManager(final Set<EGameEvent> eventsToBeCreated)
	{
		if (eventsToBeCreated != null)
		{
			for (EGameEvent eGameEvent : eventsToBeCreated)
			{
				trackedEvents.put(eGameEvent, new GameEventStorageBotIDOptimized());
			}
			
			createDetectorsForEvents(trackedEvents.keySet());
		}
	}
	
	
	
	public void createDetectorsForEvents(final Set<EGameEvent> eventsToCreateDetectorFor)
	{
		for (EGameEvent gameEvent : eventsToCreateDetectorFor)
		{
			createDetector(gameEvent);
		}
	}
	
	
	
	public void createDetector(final EGameEvent eventToCreateDetectorFor)
	{
		if (!presentDetectors.containsKey(eventToCreateDetectorFor))
		{
			switch (eventToCreateDetectorFor)
			{
			// case BALL_POSSESSION:
			// presentDetectors.put(EGameEvent.BALL_POSSESSION, new BallPossessionDetector());
			// break;
			// case TACKLE:
			// presentDetectors.put(EGameEvent.TACKLE, new TackleDetector());
			// break;
				case MARKING:
					presentDetectors.put(EGameEvent.MARKING, new MarkingDetector());
					break;
				default:
					break;
			}
		}
	}
	
	
	
	public void detectEvents(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		long worldFrameID = baseAiFrame.getWorldFrame().getId();
		
		for (EGameEvent eGameEvent : presentDetectors.keySet())
		{
			IGameEventDetector detector = presentDetectors.get(eGameEvent);
			
			GameEventFrame detectedBots = detector.getActiveParticipant(newTacticalField, baseAiFrame);
			
			if (detectedBots != null)
			{
				Set<GameEvent> nowActiveEvents = handleActiveDetectedEvent(eGameEvent, worldFrameID,
						detectedBots);
				
				activeEvents.put(eGameEvent, nowActiveEvents);
			} else
			{
				Set<GameEvent> temporaryEvents = activeEvents.get(eGameEvent);
				
				if (temporaryEvents != null)
				{
					temporaryEvents.clear();
				}
			}
		}
	}
	
	
	private Set<GameEvent> handleActiveDetectedEvent(final EGameEvent eGameEvent,
			final long worldFrameID,
			final GameEventFrame detectedBots)
	{
		Set<GameEvent> previouslyActiveEvents = activeEvents.get(eGameEvent);
		Set<GameEvent> returnedActiveEvents = new HashSet<>();
		
		for (BotID bot : detectedBots.getMappedBots())
		{
			List<BotID> additionalBots = detectedBots.getinvolvedBotsForBot(bot);
			
			GameEvent activeEvent = getGameEventFromBotAndAccordingBots(previouslyActiveEvents, bot, additionalBots);
			
			if (activeEvent != null)
			{
				activeEvent.signalEventActiveAtFrame(worldFrameID);
			} else
			{
				activeEvent = new GameEvent(worldFrameID, bot, additionalBots);
				trackedEvents.get(eGameEvent).addEvent(activeEvent);
			}
			
			returnedActiveEvents.add(activeEvent);
		}
		
		return returnedActiveEvents;
	}
	
	
	private GameEvent getGameEventFromBotAndAccordingBots(final Set<GameEvent> activeEvents, final BotID bot,
			final List<BotID> additionalBots)
	{
		boolean hasSameBotsInvolved = false;
		
		if (activeEvents == null)
		{
			return null;
		}
		
		for (GameEvent event : activeEvents)
		{
			BotID affectedBot = event.getAffectedBot();
			List<BotID> myAdditionalBots = event.getAdditionalBots();
			
			if ((affectedBot != null) && (myAdditionalBots != null))
			{
				hasSameBotsInvolved = affectedBot.equals(bot) && additionalBots.equals(additionalBots);
			} else if ((affectedBot != null) && (myAdditionalBots == null))
			{
				hasSameBotsInvolved = affectedBot.equals(bot) && (additionalBots == null);
			} else if ((affectedBot == null) && (myAdditionalBots != null))
			{
				hasSameBotsInvolved = myAdditionalBots.equals(additionalBots) && (bot == null);
			} else
			{
				hasSameBotsInvolved = (bot == null) && (additionalBots == null);
			}
			
			if (hasSameBotsInvolved)
			{
				return event;
			}
		}
		
		return null;
	}
	
	
	
	public Map<EGameEvent, IGameEventStorage> getTrackedEvents()
	{
		return trackedEvents;
	}
	
	
	
	public Set<EGameEvent> getTrackedEventTypes()
	{
		return trackedEvents.keySet();
	}
	
	
	
	public IGameEventStorage getTrackedEventsForType(final EGameEvent ballPossession)
	{
		return trackedEvents.get(ballPossession);
	}
	
}
