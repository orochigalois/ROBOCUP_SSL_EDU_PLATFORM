/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.ball.collision;

import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.ball.IState;



public class BotKickImpuls implements IImpulseObject
{
	private final IVector3	kickVel;
	private final ILine		kickerLine;
	
	
	
	public BotKickImpuls(final IVector3 pos, final double center2DribblerDist, final IVector3 kickVel)
	{
		super();
		kickerLine = BotCollision.getKickerFrontLine(pos, center2DribblerDist);
		this.kickVel = kickVel;
	}
	
	
	@Override
	public IVector3 getImpulse(final IVector3 pos)
	{
		if ((pos.z() < 170) && kickerLine.isPointOnLine(pos.getXYVector(), 20))
		{
			return kickVel;
		}
		
		return AVector3.ZERO_VECTOR;
	}
	
	
	@Override
	public IVector3 getTorqueAcc(final IState state)
	{
		return AVector3.ZERO_VECTOR;
	}
	
}
