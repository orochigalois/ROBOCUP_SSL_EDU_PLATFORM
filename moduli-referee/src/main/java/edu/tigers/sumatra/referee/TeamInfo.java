/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.referee;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.Referee.SSL_Referee;



@Persistent
public class TeamInfo
{
	private final String				name;
	private final int					score;
	private final int					redCards;
	private final int					yellowCards;
	
	private final List<Integer>	yellowCardsTimes;
	private final int					timeouts;
	
	private final int					timeoutTime;
	private final int					goalie;
	
	
	@SuppressWarnings("unused")
	TeamInfo()
	{
		name = "";
		score = 0;
		redCards = 0;
		yellowCards = 0;
		yellowCardsTimes = new ArrayList<Integer>(0);
		timeouts = 0;
		timeoutTime = 0;
		goalie = 0;
	}
	
	
	
	public TeamInfo(final SSL_Referee.TeamInfo teamInfo)
	{
		name = teamInfo.getName();
		score = teamInfo.getScore();
		redCards = teamInfo.getRedCards();
		yellowCards = teamInfo.getYellowCards();
		yellowCardsTimes = new ArrayList<Integer>(teamInfo.getYellowCardTimesList());
		timeouts = teamInfo.getTimeouts();
		timeoutTime = teamInfo.getTimeoutTime();
		goalie = teamInfo.getGoalie();
	}
	
	
	
	public final String getName()
	{
		return name;
	}
	
	
	
	public final int getScore()
	{
		return score;
	}
	
	
	
	public final int getRedCards()
	{
		return redCards;
	}
	
	
	
	public final int getYellowCards()
	{
		return yellowCards;
	}
	
	
	
	public final List<Integer> getYellowCardsTimes()
	{
		return yellowCardsTimes;
	}
	
	
	
	public final int getTimeouts()
	{
		return timeouts;
	}
	
	
	
	public final int getTimeoutTime()
	{
		return timeoutTime;
	}
	
	
	
	public final int getGoalie()
	{
		return goalie;
	}
}
