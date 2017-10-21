/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event;

import java.util.List;

import edu.tigers.sumatra.ai.data.statistics.calculators.StatisticData;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public interface IGameEventStorage
{
	
	int getGeneralEventCount();
	
	
	
	List<GameEvent> getEvents();
	
	
	
	void addEvent(final GameEvent event);
	
	
	
	List<GameEvent> getEventsForSingleBot(BotID bot);
	
	
	
	List<GameEvent> getEventsForTeamColor(ETeamColor teamColor);
	
	
	
	StatisticData toStatisticData();
	
	
	
	List<GameEvent> getActiveEventsForFrame(long frameID);
}
