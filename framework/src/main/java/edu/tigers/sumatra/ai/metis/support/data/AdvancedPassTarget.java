/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.support.data;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.ValuePoint;



@Persistent
public class AdvancedPassTarget extends ValuePoint
{
	private final boolean	chipKick;
	private final BotID		botId;
	
	
	@SuppressWarnings("unused")
	private AdvancedPassTarget()
	{
		super();
		chipKick = false;
		botId = BotID.get();
	}
	
	
	
	public AdvancedPassTarget(final double x, final double y, final double value, final boolean chipKick,
			final BotID botId)
	{
		super(x, y, value);
		this.chipKick = chipKick;
		this.botId = botId;
	}
	
	
	
	public AdvancedPassTarget(final IVector2 vector, final double value, final boolean chipKick, final BotID botId)
	{
		super(vector, value);
		this.chipKick = chipKick;
		this.botId = botId;
	}
	
	
	
	public boolean isChipKick()
	{
		return chipKick;
	}
	
	
	
	public BotID getBotId()
	{
		return botId;
	}
	
	
	@Override
	public synchronized int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((botId == null) ? 0 : botId.hashCode());
		result = (prime * result) + (chipKick ? 1231 : 1237);
		return result;
	}
	
	
	@Override
	public synchronized boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		AdvancedPassTarget other = (AdvancedPassTarget) obj;
		if (botId == null)
		{
			if (other.botId != null)
			{
				return false;
			}
		} else if (!botId.equals(other.botId))
		{
			return false;
		}
		if (chipKick != other.chipKick)
		{
			return false;
		}
		return true;
	}
	
	
}
