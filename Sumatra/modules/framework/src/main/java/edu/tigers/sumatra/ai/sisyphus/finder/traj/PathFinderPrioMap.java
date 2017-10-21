/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class PathFinderPrioMap
{
	private final Map<BotID, Integer> map = new HashMap<>();
	
	
	
	private PathFinderPrioMap()
	{
	}
	
	
	
	public static PathFinderPrioMap byBotId(final ETeamColor myTeam)
	{
		PathFinderPrioMap map = new PathFinderPrioMap();
		for (BotID botId : BotID.getAll(myTeam))
		{
			map.map.put(botId, botId.getNumber());
		}
		// for (BotID botId : BotID.getAll(myTeam.opposite()))
		// {
		// map.map.put(botId, botId.getNumber() + 100);
		// }
		return map;
	}
	
	
	
	public static PathFinderPrioMap onlyMe(final BotID botId)
	{
		PathFinderPrioMap map = new PathFinderPrioMap();
		map.map.put(botId, 1);
		return map;
	}
	
	
	
	public static PathFinderPrioMap empty()
	{
		return new PathFinderPrioMap();
	}
	
	
	
	public void setPriority(final BotID botId, final int prio)
	{
		map.put(botId, prio);
	}
	
	
	
	public boolean isPreferred(final BotID botId, final BotID otherBotId)
	{
		Integer prio = map.get(botId);
		if (prio == null)
		{
			return false;
		}
		Integer otherPrio = map.get(otherBotId);
		if (otherPrio == null)
		{
			return true;
		}
		return prio > otherPrio;
	}
	
	
	
	public boolean isEqual(final BotID botId, final BotID otherBotId)
	{
		Integer prio = map.get(botId);
		if (prio == null)
		{
			return false;
		}
		Integer otherPrio = map.get(otherBotId);
		if (otherPrio == null)
		{
			return false;
		}
		return prio == otherPrio;
	}
}
