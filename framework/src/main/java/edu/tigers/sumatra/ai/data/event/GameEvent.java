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
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



@Persistent
public class GameEvent
{
	private long			startFrame;
	private long			endFrame;
	private BotID			affectedBot;
	private List<BotID>	additionalBots;
	
	
	
	public GameEvent(final long startFrame)
	{
		this(startFrame, startFrame, null);
	}
	
	
	
	public GameEvent()
	{
		this(0);
	}
	
	
	
	public GameEvent(final long frameID, final BotID responsibleBot)
	{
		this(frameID, frameID, responsibleBot);
	}
	
	
	
	public GameEvent(final long frameID, final BotID affectedBot, final List<BotID> additionalBots)
	{
		this(frameID, affectedBot);
		this.additionalBots = additionalBots;
	}
	
	
	
	public GameEvent(final long startFrame, final ETeamColor affectedTeam)
	{
		this(startFrame, startFrame, null);
	}
	
	
	
	public GameEvent(final long startFrame, final long endFrame, final BotID responsibleBot)
	{
		this.startFrame = startFrame;
		this.endFrame = endFrame;
		affectedBot = responsibleBot;
	}
	
	
	
	public void signalEventActiveAtFrame(final long frameID)
	{
		if (frameID > endFrame)
		{
			endFrame = frameID;
		}
	}
	
	
	
	public long getStartFrame()
	{
		return startFrame;
	}
	
	
	
	public long getEndFrame()
	{
		return endFrame;
	}
	
	
	
	public ETeamColor getAffectedTeam()
	{
		return affectedBot.getTeamColor();
	}
	
	
	
	public BotID getAffectedBot()
	{
		return affectedBot;
	}
	
	
	
	public List<BotID> getAdditionalBots()
	{
		return additionalBots;
	}
	
	
	
	public void addInvolvedBot(final BotID bot)
	{
		if (additionalBots == null)
		{
			additionalBots = new ArrayList<BotID>();
		}
		additionalBots.add(bot);
	}
	
	
	
	public long getDuration()
	{
		return (endFrame - startFrame) + 1;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((additionalBots == null) ? 0 : additionalBots.hashCode());
		result = (prime * result) + ((affectedBot == null) ? 0 : affectedBot.hashCode());
		result = (prime * result) + (int) (endFrame ^ (endFrame >>> 32));
		result = (prime * result) + (int) (startFrame ^ (startFrame >>> 32));
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
		GameEvent other = (GameEvent) obj;
		if (additionalBots == null)
		{
			if (other.additionalBots != null)
			{
				return false;
			}
		} else if (!additionalBots.equals(other.additionalBots))
		{
			return false;
		}
		if (affectedBot == null)
		{
			if (other.affectedBot != null)
			{
				return false;
			}
		} else if (!affectedBot.equals(other.affectedBot))
		{
			return false;
		}
		if (endFrame != other.endFrame)
		{
			return false;
		}
		if (startFrame != other.startFrame)
		{
			return false;
		}
		return true;
	}
	
	
}
