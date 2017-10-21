/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.github.g3force.configurable.Configurable;

import edu.tigers.autoreferee.AutoRefUtil.ColorFilter;
import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.events.BotNumberViolation;
import edu.tigers.autoreferee.engine.events.GameEvent;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.referee.TeamInfo;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class BotNumberDetector extends AGameEventDetector
{
	private static final int	priority				= 1;
	
	@Configurable(comment = "Number of bots allowed on the field")
	private static int			maxTeamBotCount	= 6;
	
	private int						blueLastDiff		= 0;
	private int						yellowLastDiff		= 0;
	
	
	
	public BotNumberDetector()
	{
		super(EGameStateNeutral.RUNNING);
	}
	
	
	@Override
	public int getPriority()
	{
		return priority;
	}
	
	
	@Override
	public Optional<IGameEvent> update(final IAutoRefFrame frame, final List<IGameEvent> violations)
	{
		Collection<ITrackedBot> bots = frame.getWorldFrame().getBots().values();
		RefereeMsg refMsg = frame.getRefereeMsg();
		long ts = frame.getTimestamp();
		
		int blueAllowedCount = getAllowedTeamBotCount(refMsg, ETeamColor.BLUE, ts);
		int blueActualCount = getTeamOnFieldBotCount(bots, ETeamColor.BLUE);
		
		int yellowAllowedCount = getAllowedTeamBotCount(refMsg, ETeamColor.YELLOW, ts);
		int yellowActualCount = getTeamOnFieldBotCount(bots, ETeamColor.YELLOW);
		
		int blueDiff = blueActualCount - blueAllowedCount;
		int yellowDiff = yellowActualCount - yellowAllowedCount;
		
		GameEvent violation = null;
		if ((blueDiff > blueLastDiff) && (blueDiff > 0))
		{
			blueLastDiff = blueDiff;
			violation = new BotNumberViolation(frame.getTimestamp(), ETeamColor.BLUE, null, blueAllowedCount,
					blueActualCount);
		} else if ((yellowDiff > yellowLastDiff) && (yellowDiff > 0))
		{
			yellowLastDiff = yellowDiff;
			violation = new BotNumberViolation(frame.getTimestamp(), ETeamColor.YELLOW, null, yellowAllowedCount,
					yellowActualCount);
		}
		return violation != null ? Optional.of(violation) : Optional.empty();
	}
	
	
	private int getAllowedTeamBotCount(final RefereeMsg msg, final ETeamColor color, final long curTime_ns)
	{
		TeamInfo teamInfo = color == ETeamColor.BLUE ? msg.getTeamInfoBlue() : msg.getTeamInfoYellow();
		long msgTime_ns = msg.getFrameTimestamp();
		long passedTime_us = TimeUnit.NANOSECONDS.toMicros(curTime_ns - msgTime_ns);
		
		int yellowCards = (int) teamInfo.getYellowCardsTimes().stream()
				.map(cardTime_us -> cardTime_us - passedTime_us)
				.filter(cardTime_us -> cardTime_us > 0)
				.count();
		
		return maxTeamBotCount - yellowCards;
	}
	
	
	private int getTeamOnFieldBotCount(final Collection<ITrackedBot> bots, final ETeamColor color)
	{
		
		return (int) bots.stream()
				.filter(ColorFilter.get(color))
				.filter(bot -> Geometry.getFieldWBorders().isPointInShape(bot.getPos()))
				.count();
	}
	
	
	@Override
	public void reset()
	{
		blueLastDiff = 0;
		yellowLastDiff = 0;
	}
	
}
