/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer;


public class BotStatus
{
	private boolean	visible		= false;
	private boolean	connected	= false;
	private double		batRel		= 0;
	private double		kickerRel	= 0;
											
	private boolean	hideRcm		= false;
	private boolean	hideAi		= false;
											
											
	
	public final boolean isVisible()
	{
		return visible;
	}
	
	
	
	public final void setVisible(final boolean visible)
	{
		this.visible = visible;
	}
	
	
	
	public final boolean isConnected()
	{
		return connected;
	}
	
	
	
	public final void setConnected(final boolean connected)
	{
		this.connected = connected;
	}
	
	
	
	public final double getBatRel()
	{
		return batRel;
	}
	
	
	
	public final void setBatRel(final double batRel)
	{
		this.batRel = batRel;
	}
	
	
	
	public final double getKickerRel()
	{
		return kickerRel;
	}
	
	
	
	public final void setKickerRel(final double kickerRel)
	{
		this.kickerRel = kickerRel;
	}
	
	
	
	public final boolean isHideRcm()
	{
		return hideRcm;
	}
	
	
	
	public final void setHideRcm(final boolean hideRcm)
	{
		this.hideRcm = hideRcm;
	}
	
	
	
	public final boolean isHideAi()
	{
		return hideAi;
	}
	
	
	
	public final void setHideAi(final boolean hideAi)
	{
		this.hideAi = hideAi;
	}
	
}
