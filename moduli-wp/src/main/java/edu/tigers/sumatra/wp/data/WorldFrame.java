/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.util.Map;

import edu.tigers.sumatra.bot.DummyBot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.BotIDMapConst;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.ids.IBotIDMap;



public class WorldFrame extends SimpleWorldFrame
{
	
	public final BotIDMapConst<ITrackedBot>	foeBots;
	
	
	public final BotIDMapConst<ITrackedBot>	tigerBotsVisible;
	
	public final IBotIDMap<ITrackedBot>			tigerBotsAvailable;
	
	
	private final ETeamColor						teamColor;
	
	private final boolean							inverted;
	
	
	
	public WorldFrame(final SimpleWorldFrame simpleWorldFrame, final ETeamColor teamColor, final boolean invert)
	{
		super(simpleWorldFrame);
		this.teamColor = teamColor;
		inverted = invert;
		
		BotIDMap<ITrackedBot> foes = new BotIDMap<>();
		BotIDMap<ITrackedBot> tigersVisible = new BotIDMap<>();
		BotIDMap<ITrackedBot> tigersAvailable = new BotIDMap<>();
		for (Map.Entry<BotID, ITrackedBot> entry : simpleWorldFrame.getBots().entrySet())
		{
			final BotID botID = entry.getKey();
			ITrackedBot bot = entry.getValue();
			
			if (bot.getBotId().getTeamColor() == getTeamColor())
			{
				tigersVisible.put(botID, bot);
				if (bot.isAvailableToAi()
						&& Geometry.getFieldWReferee()
								.isPointInShape(bot.getPos(), -Geometry.getBotRadius()))
				{
					tigersAvailable.put(botID, bot);
				}
			} else
			{
				TrackedBot nBot = new TrackedBot(bot);
				nBot.setBot(new DummyBot(botID));
				foes.put(botID, nBot);
			}
		}
		foeBots = BotIDMapConst.unmodifiableBotIDMap(foes);
		tigerBotsAvailable = BotIDMapConst.unmodifiableBotIDMap(tigersAvailable);
		tigerBotsVisible = BotIDMapConst.unmodifiableBotIDMap(tigersVisible);
	}
	
	
	
	public WorldFrame(final WorldFrame original)
	{
		super(original);
		teamColor = original.getTeamColor();
		inverted = original.isInverted();
		foeBots = BotIDMapConst.unmodifiableBotIDMap(original.getFoeBots());
		tigerBotsAvailable = original.getTigerBotsAvailable();
		tigerBotsVisible = BotIDMapConst.unmodifiableBotIDMap(original.getTigerBotsVisible());
	}
	
	
	@Override
	public String toString()
	{
		final StringBuilder b = new StringBuilder();
		b.append("[WorldFrame, id = ").append(getId()).append("|\n");
		b.append("Ball: ").append(getBall().getPos()).append("|\n");
		b.append("Tigers: ");
		for (final ITrackedBot tiger : tigerBotsVisible.values())
		{
			b.append(tiger.getPos()).append(",");
		}
		b.append("|\n");
		b.append("Enemies: ");
		for (final ITrackedBot bot : foeBots.values())
		{
			b.append(bot.getPos()).append(",");
		}
		b.append("]\n");
		return b.toString();
	}
	
	
	
	public ITrackedBot getTiger(final BotID botId)
	{
		return getBot(botId);
	}
	
	
	
	public ITrackedBot getFoeBot(final BotID botId)
	{
		return getBot(botId);
	}
	
	
	
	public BotIDMapConst<ITrackedBot> getFoeBots()
	{
		return foeBots;
	}
	
	
	
	public BotIDMapConst<ITrackedBot> getTigerBotsVisible()
	{
		return tigerBotsVisible;
	}
	
	
	
	public IBotIDMap<ITrackedBot> getTigerBotsAvailable()
	{
		return tigerBotsAvailable;
	}
	
	
	
	public final ETeamColor getTeamColor()
	{
		return teamColor;
	}
	
	
	
	public final boolean isInverted()
	{
		return inverted;
	}
}
