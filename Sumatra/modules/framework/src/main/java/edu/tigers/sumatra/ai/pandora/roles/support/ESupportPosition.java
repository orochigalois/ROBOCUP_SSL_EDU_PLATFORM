/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.support;



public enum ESupportPosition
{
	
	RANDOM(new RandomSupportPosition()),
	
	GPUGrid(new GPUGridSupportPosition());
	
	private ASupportPosition supportPosition;
	
	
	private ESupportPosition(final ASupportPosition supportPosition)
	{
		this.supportPosition = supportPosition;
	}
	
	
	
	public ASupportPosition getSupportPosition()
	{
		return supportPosition;
	}
}
