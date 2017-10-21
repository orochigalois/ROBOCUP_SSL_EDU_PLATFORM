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

import java.text.DecimalFormat;

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.sumatra.ids.BotID;



public class DistanceViolation extends GameEvent
{
	
	private final double	distance;
	
	
	
	public DistanceViolation(final EGameEvent eventType, final long timestamp, final BotID botAtFault,
			final FollowUpAction followUp, final double distance)
	{
		this(eventType, timestamp, botAtFault, followUp, null, distance);
	}
	
	
	
	public DistanceViolation(final EGameEvent eventType, final long timestamp, final BotID botAtFault,
			final FollowUpAction followUp, final CardPenalty cardPenalty, final double distance)
	{
		super(eventType, timestamp, botAtFault, followUp, cardPenalty);
		this.distance = distance;
	}
	
	
	
	public double getDistance()
	{
		return distance;
	}
	
	
	@Override
	public String buildLogString()
	{
		return super.buildLogString();
	}
	
	
	@Override
	protected String generateLogString()
	{
		DecimalFormat format = new DecimalFormat("####.0");
		
		String superResult = super.generateLogString();
		StringBuilder builder = new StringBuilder(superResult);
		
		builder.append(" | Distance: ");
		builder.append(format.format(distance));
		builder.append("mm");
		
		return builder.toString();
	}
}
