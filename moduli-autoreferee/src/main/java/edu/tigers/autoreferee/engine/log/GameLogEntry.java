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

import org.apache.commons.lang.NotImplementedException;

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class GameLogEntry
{
	
	public enum ELogEntryType
	{
		
		GAME_STATE,
		
		GAME_EVENT,
		
		REFEREE_MSG,
		
		FOLLOW_UP,
		
		COMMAND
	}
	
	private final ELogEntryType		type;
	
	private final long					timestamp;
	
	private final GameTime				gameTime;
	
	private final Instant				instant;
	
	private final long					timeSinceStart;
	
	private final EGameStateNeutral	gamestate;
	private final RefereeMsg			refereeMsg;
	private final FollowUpAction		followUpAction;
	private final RefCommand			command;
	
	private final IGameEvent			gameEvent;
	private final boolean				acceptedByEngine;
	
	
	
	protected GameLogEntry(final long timestamp, final GameTime gameTime, final long timeSinceStart,
			final Instant instant, final ELogEntryType type, final EGameStateNeutral gamestate,
			final IGameEvent gameEvent, final boolean acceptedByEngine, final RefereeMsg refereeMsg,
			final FollowUpAction followUpAction, final RefCommand command)
	{
		this.type = type;
		this.timeSinceStart = timeSinceStart;
		this.gameTime = gameTime;
		this.timestamp = timestamp;
		this.instant = instant;
		
		this.gameEvent = gameEvent;
		this.acceptedByEngine = acceptedByEngine;
		
		this.gamestate = gamestate;
		this.refereeMsg = refereeMsg;
		this.followUpAction = followUpAction;
		this.command = command;
	}
	
	
	
	public ELogEntryType getType()
	{
		return type;
	}
	
	
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public GameTime getGameTime()
	{
		return gameTime;
	}
	
	
	
	public long getTimeSinceStart()
	{
		return timeSinceStart;
	}
	
	
	
	public EGameStateNeutral getGamestate()
	{
		return gamestate;
	}
	
	
	
	public IGameEvent getGameEvent()
	{
		return gameEvent;
	}
	
	
	
	public boolean isAcceptedByEngine()
	{
		return acceptedByEngine;
	}
	
	
	
	public RefereeMsg getRefereeMsg()
	{
		return refereeMsg;
	}
	
	
	
	public FollowUpAction getFollowUpAction()
	{
		return followUpAction;
	}
	
	
	
	public RefCommand getCommand()
	{
		return command;
	}
	
	
	
	public Instant getInstant()
	{
		return instant;
	}
	
	
	
	public Object getObject()
	{
		switch (type)
		{
			case COMMAND:
				return command;
			case FOLLOW_UP:
				return followUpAction;
			case GAME_STATE:
				return gamestate;
			case REFEREE_MSG:
				return refereeMsg;
			case GAME_EVENT:
				return gameEvent;
			default:
				throw new NotImplementedException("Please add the following enum value to this switch case: " + type);
		}
	}
	
}
