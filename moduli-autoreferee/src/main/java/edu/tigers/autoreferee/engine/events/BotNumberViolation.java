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

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.sumatra.ids.ETeamColor;



public class BotNumberViolation extends GameEvent
{
	
	private final int	allowedNumber;
	private final int	actualNumber;
	
	
	
	public BotNumberViolation(final long timestamp, final ETeamColor responsibleTeam, final FollowUpAction followUp,
			final int allowedNumber, final int actualNumber)
	{
		super(EGameEvent.BOT_COUNT, timestamp, responsibleTeam, followUp);
		this.allowedNumber = allowedNumber;
		this.actualNumber = actualNumber;
	}
	
	
	
	public int getAllowedNumber()
	{
		return allowedNumber;
	}
	
	
	
	public int getActualNumber()
	{
		return actualNumber;
	}
	
	
	@Override
	public String buildLogString()
	{
		return super.buildLogString();
	}
	
	
	@Override
	protected String generateLogString()
	{
		String superResult = super.generateLogString();
		StringBuilder builder = new StringBuilder(superResult);
		
		builder.append(" | Allowed/Actual: ");
		builder.append(getAllowedNumber());
		builder.append("/");
		builder.append(getActualNumber());
		
		return builder.toString();
	}
	
}
