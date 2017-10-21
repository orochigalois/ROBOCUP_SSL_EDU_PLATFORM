/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class GameLog implements IGameLog
{
	private static final Logger		log					= Logger.getLogger(GameLog.class);
	
	private long							startRefTimestamp	= 0;
	private long							currentTimestamp	= 0;
	private GameTime						currentGameTime	= GameTime.empty();
	private List<GameLogEntry>			entries				= new ArrayList<>();
	private List<IGameLogObserver>	observer				= new CopyOnWriteArrayList<>();
	
	
	
	public void initialize(final long timestamp)
	{
		log.debug("Initialized game log with timestamp: " + timestamp);
		startRefTimestamp = timestamp;
		currentTimestamp = timestamp;
	}
	
	
	private long getTimeSinceStart()
	{
		return currentTimestamp - startRefTimestamp;
	}
	
	
	private Instant getCurrentInstant()
	{
		return Instant.now();
	}
	
	
	
	@Override
	public List<GameLogEntry> getEntries()
	{
		return Collections.unmodifiableList(entries);
	}
	
	
	
	public void setCurrentTimestamp(final long timestamp)
	{
		currentTimestamp = timestamp;
	}
	
	
	
	public void setCurrentGameTime(final GameTime time)
	{
		currentGameTime = time;
	}
	
	
	private void addEntryToLog(final GameLogEntry entry)
	{
		int id;
		synchronized (entries)
		{
			entries.add(entry);
			id = entries.size() - 1;
		}
		log.debug("Added new entry with id " + id + "and type " + entry.getType());
		observer.forEach(obs -> obs.onNewEntry(id, entry));
	}
	
	
	private GameLogEntry buildEntry(final Consumer<? super GameLogEntryBuilder> consumer)
	{
		GameLogEntryBuilder builder = new GameLogEntryBuilder();
		builder.setTimestamp(currentTimestamp);
		builder.setGameTime(currentGameTime);
		builder.setTimeSinceStart(getTimeSinceStart());
		builder.setInstant(getCurrentInstant());
		
		consumer.accept(builder);
		return builder.toEntry();
	}
	
	
	private GameLogEntry buildAndAddEntry(final Consumer<? super GameLogEntryBuilder> consumer)
	{
		GameLogEntry entry = buildEntry(consumer);
		addEntryToLog(entry);
		return entry;
	}
	
	
	
	public GameLogEntry addEntry(final EGameStateNeutral gamestate)
	{
		return buildAndAddEntry(builder -> builder.setGamestate(gamestate));
	}
	
	
	
	public GameLogEntry addEntry(final IGameEvent event)
	{
		return addEntry(event, false);
	}
	
	
	
	public GameLogEntry addEntry(final IGameEvent event, final boolean acceptedByEngine)
	{
		return buildAndAddEntry(builder -> builder.setGameEvent(event, acceptedByEngine));
	}
	
	
	
	public GameLogEntry addEntry(final RefereeMsg refereeMsg)
	{
		return buildAndAddEntry(builder -> builder.setRefereeMsg(refereeMsg));
	}
	
	
	
	public GameLogEntry addEntry(final FollowUpAction action)
	{
		return buildAndAddEntry(builder -> builder.setFollowUpAction(action));
	}
	
	
	
	public GameLogEntry addEntry(final RefCommand command)
	{
		return buildAndAddEntry(builder -> builder.setCommand(command));
	}
	
	
	
	@Override
	public void addObserver(final IGameLogObserver observer)
	{
		this.observer.add(observer);
	}
	
	
	
	@Override
	public void removeObserver(final IGameLogObserver observer)
	{
		this.observer.remove(observer);
	}
	
	
	public interface IGameLogObserver
	{
		
		public void onNewEntry(int id, GameLogEntry entry);
	}
}
