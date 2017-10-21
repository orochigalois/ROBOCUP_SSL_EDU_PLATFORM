/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots;


public class PingStats
{
	
	public PingStats()
	{
		avgDelay = 0;
		minDelay = Double.MAX_VALUE;
		maxDelay = 0;
		lostPings = 0;
	}
	
	
	public double	avgDelay;
	
	public double	minDelay;
	
	public double	maxDelay;
	
	public int		lostPings;
}
