/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.calc;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class BotPosition
{
	private long				ts;
	private final BotID		id;
	private final IVector2	pos;
	
	
	
	public BotPosition()
	{
		ts = 0;
		id = BotID.get();
		pos = AVector2.ZERO_VECTOR;
	}
	
	
	
	public BotPosition(final long ts, final BotID id, final IVector2 pos)
	{
		setTs(ts);
		this.id = id;
		this.pos = pos;
	}
	
	
	
	public BotPosition(final long ts, final ITrackedBot bot)
	{
		setTs(ts);
		id = bot.getBotId();
		pos = bot.getPos();
	}
	
	
	
	public long getTs()
	{
		return ts;
	}
	
	
	
	public void setTs(final long ts)
	{
		this.ts = ts;
	}
	
	
	
	public BotID getId()
	{
		return id;
	}
	
	
	
	public IVector2 getPos()
	{
		return pos;
	}
}
