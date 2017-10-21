/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman.data;



public class UnregisteredBot
{
	
	private final long	oldTimestamp;
	private long			newTimestamp;
	private WPCamBot		visionBot;
	private int				count;
								
								
	
	public UnregisteredBot(final long time, final WPCamBot visionbot)
	{
		oldTimestamp = time;
		setNewTimestamp(time);
		visionBot = visionbot;
		setCount(0);
	}
	
	
	
	public void addBot(final long time, final WPCamBot visionbot)
	{
		setNewTimestamp(time);
		visionBot = visionbot;
		setCount(getCount() + 1);
	}
	
	
	
	public long getOldTimestamp()
	{
		return oldTimestamp;
	}
	
	
	
	public long getNewTimestamp()
	{
		return newTimestamp;
	}
	
	
	
	public void setNewTimestamp(final long newTimestamp)
	{
		this.newTimestamp = newTimestamp;
	}
	
	
	
	public WPCamBot getVisionBot()
	{
		return visionBot;
	}
	
	
	
	public int getCount()
	{
		return count;
	}
	
	
	
	private void setCount(final int count)
	{
		this.count = count;
	}
}
