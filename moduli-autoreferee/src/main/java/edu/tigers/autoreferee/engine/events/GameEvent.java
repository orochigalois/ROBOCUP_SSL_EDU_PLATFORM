/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events;

import java.util.Optional;

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class GameEvent implements IGameEvent
{
	private final EGameEvent		eventType;
	private final long				timestamp;						// ns
	private final ETeamColor		responsibleTeam;
	private final BotID				responsibleBot;
	
	private final FollowUpAction	followUpAction;
	private final CardPenalty		cardPenalty;
	
	private String						cachedLogString	= null;
	
	
	
	public GameEvent(final EGameEvent eventType, final long timestamp,
			final BotID responsibleBot, final FollowUpAction followUp)
	{
		this(eventType, timestamp, responsibleBot, followUp, null);
	}
	
	
	
	public GameEvent(final EGameEvent eventType, final long timestamp,
			final BotID responsibleBot, final FollowUpAction followUp, final CardPenalty cardPenalty)
	{
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.responsibleBot = responsibleBot;
		
		followUpAction = followUp;
		this.cardPenalty = cardPenalty;
		
		responsibleTeam = responsibleBot.getTeamColor();
	}
	
	
	
	public GameEvent(final EGameEvent eventType, final long timestamp, final ETeamColor responsibleTeam,
			final FollowUpAction followUp)
	{
		this(eventType, timestamp, responsibleTeam, followUp, null);
	}
	
	
	
	public GameEvent(final EGameEvent eventType, final long timestamp, final ETeamColor responsibleTeam,
			final FollowUpAction followUp, final CardPenalty cardPenalty)
	{
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.responsibleTeam = responsibleTeam;
		responsibleBot = null;
		
		followUpAction = followUp;
		this.cardPenalty = cardPenalty;
	}
	
	
	
	@Override
	public EGameEvent getType()
	{
		return eventType;
	}
	
	
	@Override
	public EEventCategory getCategory()
	{
		return eventType.getCategory();
	}
	
	
	
	@Override
	public long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	@Override
	public ETeamColor getResponsibleTeam()
	{
		return responsibleTeam;
	}
	
	
	@Override
	public Optional<BotID> getResponsibleBot()
	{
		return Optional.ofNullable(responsibleBot);
	}
	
	
	@Override
	public String buildLogString()
	{
		if (cachedLogString == null)
		{
			synchronized (this)
			{
				if (cachedLogString == null)
				{
					cachedLogString = generateLogString();
				}
			}
		}
		return cachedLogString;
	}
	
	
	protected String generateLogString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getType());
		if (responsibleBot != null)
		{
			builder.append(" | Bot: ");
			builder.append(responsibleBot.getNumber());
			builder.append(" ");
			builder.append(responsibleBot.getTeamColor());
		} else
		{
			builder.append(" | Team: ");
			builder.append(responsibleTeam);
		}
		return builder.toString();
	}
	
	
	@Override
	public String toString()
	{
		return buildLogString();
	}
	
	
	
	@Override
	public FollowUpAction getFollowUpAction()
	{
		return followUpAction;
	}
	
	
	@Override
	public Optional<CardPenalty> getCardPenalty()
	{
		return Optional.ofNullable(cardPenalty);
	}
}
