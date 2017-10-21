/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.sim;

import edu.tigers.sumatra.wp.ball.BallCollisionModel;
import edu.tigers.sumatra.wp.ball.collision.CollisionHandler;
import edu.tigers.sumatra.wp.data.MotionContext;



public class BallCollisionSimModel extends BallCollisionModel
{
	
	
	public BallCollisionSimModel()
	{
		super(true);
	}
	
	
	@Override
	protected void addCollisionObjects(final CollisionHandler ch, final MotionContext context)
	{
		super.addCollisionObjects(ch, context);
	}
	
	
	@Override
	protected void addImpulseObjects(final CollisionHandler ch, final MotionContext context)
	{
		super.addImpulseObjects(ch, context);
	}
	
}
