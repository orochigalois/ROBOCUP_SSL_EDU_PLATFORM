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

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.autoreferee.engine.log.GameLogEntry.ELogEntryType;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class GameLogEntryBuilder
{
	private ELogEntryType		type;
	
	private Long					timestamp;
	
	private GameTime				gameTime;
	
	private Instant				instant;
	
	private Long					timeSinceStart;
	
	private IGameEvent			gameEvent;
	private boolean				acceptedByEngine;
	
	private EGameStateNeutral	gamestate;
	private RefereeMsg			refereeMsg;
	private FollowUpAction		followUpAction;
	private RefCommand			command;
	
	
	private void setType(final ELogEntryType type)
	{
		this.type = type;
	}
	
	
	
	public void setTimestamp(final long timestamp)
	{
		this.timestamp = timestamp;
	}
	
	
	
	public void setGameTime(final GameTime gameTime)
	{
		this.gameTime = gameTime;
	}
	
	
	
	public void setInstant(final Instant instant)
	{
		this.instant = instant;
	}
	
	
	
	public void setTimeSinceStart(final long timeSinceStart)
	{
		this.timeSinceStart = timeSinceStart;
	}
	
	
	
	public void setGamestate(final EGameStateNeutral gamestate)
	{
		this.gamestate = gamestate;
		setType(ELogEntryType.GAME_STATE);
	}
	
	
	
	public void setGameEvent(final IGameEvent gameEvent, final boolean acceptedByEngine)
	{
		this.gameEvent = gameEvent;
		this.acceptedByEngine = acceptedByEngine;
		setType(ELogEntryType.GAME_EVENT);
	}
	
	
	
	public void setRefereeMsg(final RefereeMsg refereeMsg)
	{
		this.refereeMsg = refereeMsg;
		setType(ELogEntryType.REFEREE_MSG);
	}
	
	
	
	public void setFollowUpAction(final FollowUpAction followUpAction)
	{
		this.followUpAction = followUpAction;
		setType(ELogEntryType.FOLLOW_UP);
	}
	
	
	
	public void setCommand(final RefCommand command)
	{
		this.command = command;
		setType(ELogEntryType.COMMAND);
	}
	
	
	
	public GameLogEntry toEntry()
	{
		if ((type == null) || (timestamp == null) || (gameTime == null) || (timeSinceStart == null) || (instant == null))
		{
			throw new NullPointerException("Not all required fields have been set");
		}
		
		return new GameLogEntry(timestamp, gameTime, timeSinceStart, instant, type, gamestate, gameEvent,
				acceptedByEngine, refereeMsg, followUpAction, command);
	}
}
