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

import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.ball.BallAction;
import edu.tigers.sumatra.wp.ball.BallCollisionModel;
import edu.tigers.sumatra.wp.ball.BallDynamicsModelSimple;
import edu.tigers.sumatra.wp.ball.BallState;
import edu.tigers.sumatra.wp.ball.IAction;
import edu.tigers.sumatra.wp.ball.IState;
import edu.tigers.sumatra.wp.ball.collision.ICollisionState;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.MotionContext;



public class SimulatedBall implements ISimulatedBall
{
	private final BallDynamicsModelSimple	dynamicsModel;
	private final BallCollisionModel			collisionModel;
	
	private BallState								state						= new BallState();
	
	private final Object							sync						= new Object();
	
	private double									timeSinceLastImpulse	= 0;
	
	
	
	public SimulatedBall()
	{
		dynamicsModel = new BallDynamicsModelSimple(Geometry.getBallModel().getAcc());
		collisionModel = new BallCollisionSimModel();
	}
	
	
	@Override
	public void step(final double dt, final MotionContext context)
	{
		synchronized (sync)
		{
			IVector3 torqueAccTarget = collisionModel.getTorqueAcc(state, context);
			IAction action = new BallAction(torqueAccTarget);
			IState postState = dynamicsModel.dynamics(state, action, dt, context);
			ICollisionState colState = collisionModel.processCollision(state, postState, dt, context);
			IVector3 impulse = collisionModel.getImpulse(colState, context);
			BallState newState = new BallState(colState);
			if (timeSinceLastImpulse > 0.2)
			{
				newState.setVel(newState.getVel().addNew(impulse));
			}
			if (!impulse.isZeroVector())
			{
				timeSinceLastImpulse = 0;
			}
			timeSinceLastImpulse += dt;
			
			if (!Geometry.getFieldWBorders().isPointInShape(newState.getPos().getXYVector()))
			{
				newState.setVel(AVector3.ZERO_VECTOR);
				newState.setPos(
						new Vector3(Geometry.getField().nearestPointInside(newState.getPos().getXYVector(), -100), 0));
			}
			
			state = newState;
		}
	}
	
	
	
	@Override
	public IVector3 getPos()
	{
		return state.getPos();
	}
	
	
	
	@Override
	public IVector3 getVel()
	{
		return state.getVel();
	}
	
	
	
	@Override
	public void setPos(final IVector3 pos)
	{
		synchronized (sync)
		{
			state.setPos(pos);
		}
	}
	
	
	
	@Override
	public void addVel(final IVector3 vector3)
	{
		synchronized (sync)
		{
			state.setVel(state.getVel().addNew(vector3));
		}
	}
	
	
	
	@Override
	public final void setVel(final IVector3 vel)
	{
		synchronized (sync)
		{
			state.setVel(vel);
		}
	}
}
