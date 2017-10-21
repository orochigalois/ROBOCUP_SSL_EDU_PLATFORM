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

import java.util.Optional;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.ball.BallState;
import edu.tigers.sumatra.wp.ball.IState;



public class BallCollisionState extends BallState implements ICollisionState
{
	private Optional<ICollision> collision = Optional.empty();
	
	
	
	public BallCollisionState(final IVector3 pos, final IVector3 vel, final IVector3 acc, final IVector3 accTorque)
	{
		super(pos, vel, acc, accTorque);
	}
	
	
	
	public BallCollisionState(final ICollisionState state)
	{
		super(state);
	}
	
	
	
	public BallCollisionState(final IState state)
	{
		super(state);
	}
	
	
	
	@Override
	public Optional<ICollision> getCollision()
	{
		return collision;
	}
	
	
	
	public void setCollision(final Optional<ICollision> collision)
	{
		this.collision = collision;
	}
}
