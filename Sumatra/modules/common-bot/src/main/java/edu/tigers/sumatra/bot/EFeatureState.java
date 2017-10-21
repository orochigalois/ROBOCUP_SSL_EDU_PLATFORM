/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.bot;



public enum EFeatureState
{
	
	WORKING(0),
	
	KAPUT(2),
	
	UNKNOWN(0xFF);
	
	private final int	id;
	
	
	private EFeatureState(final int id)
	{
		this.id = id;
	}
	
	
	
	public int getId()
	{
		return id;
	}
	
	
	
	public static EFeatureState getFeatureStateConstant(final int id)
	{
		for (EFeatureState s : values())
		{
			if (s.getId() == id)
			{
				return s;
			}
		}
		
		return UNKNOWN;
	}
}
