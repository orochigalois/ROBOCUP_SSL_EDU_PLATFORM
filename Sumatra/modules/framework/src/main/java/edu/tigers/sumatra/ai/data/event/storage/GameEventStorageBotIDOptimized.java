/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.data.event.GameEvent;
import edu.tigers.sumatra.ai.data.event.IGameEventStorage;
import edu.tigers.sumatra.ai.data.statistics.calculators.StatisticData;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



@Persistent
public class GameEventStorageBotIDOptimized implements IGameEventStorage
{
	private transient Map<BotID, List<GameEvent>>	events		= new HashMap<>();
	
	private Integer											countEvents	= 0;
	
	
	
	public GameEventStorageBotIDOptimized()
	{
	}
	
	
	
	@Override
	public StatisticData toStatisticData()
	{
		StatisticData data = new StatisticData(getMapWithCountsOfEvents(), countEvents);
		return data;
	}
	
	
	private Map<BotID, Integer> getMapWithCountsOfEvents()
	{
		Map<BotID, Integer> mapWithCounts = new HashMap<>();
		
		for (BotID bot : events.keySet())
		{
			int countEvents = events.get(bot).size();
			
			mapWithCounts.put(bot, countEvents);
		}
		
		return mapWithCounts;
	}
	
	
	@Override
	public List<GameEvent> getEventsForSingleBot(final BotID bot)
	{
		return events.get(bot);
	}
	
	
	@Override
	public List<GameEvent> getEventsForTeamColor(final ETeamColor teamColor)
	{
		return null;
	}
	
	
	
	@Override
	public void addEvent(final GameEvent event)
	{
		BotID affectedBot = event.getAffectedBot();
		List<GameEvent> listForBot = events.get(affectedBot);
		
		if (listForBot == null)
		{
			listForBot = new ArrayList<GameEvent>();
			events.put(affectedBot, listForBot);
		}
		
		listForBot.add(event);
		countEvents++;
	}
	
	
	@Override
	public int getGeneralEventCount()
	{
		return countEvents;
	}
	
	
	@Override
	public List<GameEvent> getEvents()
	{
		List<GameEvent> events = this.events.values().stream().flatMap(l -> l.stream()).collect(Collectors.toList());
		
		return events;
	}
	
	
	@Override
	public List<GameEvent> getActiveEventsForFrame(final long frameID)
	{
		List<GameEvent> activeEvents = new ArrayList<GameEvent>();
		
		for (GameEvent gameEvent : getEvents())
		{
			if ((gameEvent.getStartFrame() <= frameID) && (gameEvent.getEndFrame() >= frameID))
			{
				activeEvents.add(gameEvent);
			}
		}
		
		return activeEvents;
	}
}
