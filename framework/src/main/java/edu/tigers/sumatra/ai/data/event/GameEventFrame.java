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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.tigers.sumatra.ids.BotID;



public class GameEventFrame
{
	private Map<BotID, List<BotID>> involvedBots = new HashMap<BotID, List<BotID>>();
	
	
	
	public GameEventFrame()
	{
		
	}
	
	
	
	public GameEventFrame(final BotID singleBot)
	{
		putSingleBot(singleBot);
	}
	
	
	
	public GameEventFrame(final BotID mainBot, final BotID secondaryBot)
	{
		putBotPair(mainBot, secondaryBot);
	}
	
	
	
	public List<BotID> getinvolvedBotsForBot(final BotID bot)
	{
		return involvedBots.get(bot);
	}
	
	
	
	public Set<BotID> getMappedBots()
	{
		return involvedBots.keySet();
	}
	
	
	
	public void putInvolvedBots(final BotID mainBot, final List<BotID> additionalBots)
	{
		involvedBots.put(mainBot, additionalBots);
	}
	
	
	
	public void putSingleBot(final BotID bot)
	{
		involvedBots.put(bot, null);
	}
	
	
	
	public void putBotPair(final BotID mainBot, final BotID secondaryBot)
	{
		List<BotID> tempList = new ArrayList<BotID>();
		tempList.add(secondaryBot);
		
		putInvolvedBots(mainBot, tempList);
	}
	
	
	
	public Set<Entry<BotID, List<BotID>>> getEntrySet()
	{
		return involvedBots.entrySet();
	}
	
	
	
	public boolean hasHappened()
	{
		return !involvedBots.isEmpty();
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((involvedBots == null) ? 0 : involvedBots.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		GameEventFrame other = (GameEventFrame) obj;
		if (involvedBots == null)
		{
			if (other.involvedBots != null)
			{
				return false;
			}
		} else if (!involvedBots.equals(other.involvedBots))
		{
			return false;
		}
		return true;
	}
}
