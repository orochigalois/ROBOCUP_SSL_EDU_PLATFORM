/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.ball;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.ball.collision.ICollisionState;
import edu.tigers.sumatra.wp.data.MotionContext;



public interface IBallCollisionModel
{
	
	ICollisionState processCollision(final IState state, final IState newState, final double dt,
			final MotionContext context);
	
	
	
	IVector3 getImpulse(ICollisionState state, final MotionContext context);
	
	
	
	IVector3 getTorqueAcc(IState state, MotionContext context);
}
