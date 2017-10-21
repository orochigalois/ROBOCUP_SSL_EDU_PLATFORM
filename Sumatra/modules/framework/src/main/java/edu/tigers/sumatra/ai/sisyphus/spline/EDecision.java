/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.spline;


public enum EDecision
{
	
	NO_VIOLATION(1),
	
	OPTIMIZATION_FOUND(2),
	
	VIOLATION(3),
	
	COLLISION_AHEAD(4),
	
	ENFORCE(5);
	
	private final int	severity;
	
	
	private EDecision(final int severity)
	{
		this.severity = severity;
	}
	
	
	
	public EDecision max(final EDecision otherDecision)
	{
		if (otherDecision.getSeverity() > severity)
		{
			return otherDecision;
		}
		return this;
	}
	
	
	
	public int getSeverity()
	{
		return severity;
	}
	
	
}
