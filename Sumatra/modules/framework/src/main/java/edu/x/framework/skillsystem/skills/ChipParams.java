/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;


public class ChipParams
{
	private final double	kickSpeed;
	private final int		dribbleSpeed;
	
	
	
	public ChipParams(final double kickSpeed, final int dribbleSpeed)
	{
		this.kickSpeed = kickSpeed;
		this.dribbleSpeed = dribbleSpeed;
	}
	
	
	
	public final double getKickSpeed()
	{
		return kickSpeed;
	}
	
	
	
	public final int getDribbleSpeed()
	{
		return dribbleSpeed;
	}
}
