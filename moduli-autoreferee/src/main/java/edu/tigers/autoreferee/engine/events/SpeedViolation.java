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



public class SpeedViolation extends GameEvent
{
	
	private final double	speed;
	
	
	
	public SpeedViolation(final EGameEvent eventType, final long timestamp, final BotID botAtFault,
			final FollowUpAction followUp, final double speed)
	{
		super(eventType, timestamp, botAtFault, followUp);
		this.speed = speed;
	}
	
	
	
	public double getSpeed()
	{
		return speed;
	}
	
	
	@Override
	public String buildLogString()
	{
		return super.buildLogString();
	}
	
	
	@Override
	protected String generateLogString()
	{
		DecimalFormat format = new DecimalFormat("#.000");
		
		String superResult = super.generateLogString();
		StringBuilder builder = new StringBuilder(superResult);
		
		builder.append(" | Speed: ");
		builder.append(format.format(speed));
		builder.append("m/s");
		
		return builder.toString();
	}
}
