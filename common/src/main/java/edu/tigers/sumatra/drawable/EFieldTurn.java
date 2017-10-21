/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import edu.tigers.sumatra.math.AngleMath;



public enum EFieldTurn
{
	
	NORMAL(0),
	
	T90(AngleMath.PI_HALF),
	
	T180(AngleMath.PI),
	
	T270(AngleMath.PI + AngleMath.PI_HALF);
	
	private final double angle;
	
	
	private EFieldTurn(final double angle)
	{
		this.angle = angle;
	}
	
	
	
	public final double getAngle()
	{
		return angle;
	}
}
