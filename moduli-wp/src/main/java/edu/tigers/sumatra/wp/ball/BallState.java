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
import edu.tigers.sumatra.math.Vector3;



public class BallState implements IState
{
	private IVector3	pos			= Vector3.ZERO_VECTOR, vel = Vector3.ZERO_VECTOR, acc = Vector3.ZERO_VECTOR;
	private IVector3	accTorque	= Vector3.ZERO_VECTOR;
	
	
	
	public BallState(final IVector3 pos, final IVector3 vel, final IVector3 acc, final IVector3 accTorque)
	{
		super();
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
		this.accTorque = accTorque;
	}
	
	
	
	public BallState(final IState state)
	{
		this(state.getPos(), state.getVel(), state.getAcc(), state.getAccFromTorque());
	}
	
	
	
	public BallState()
	{
	}
	
	
	
	@Override
	public IVector3 getPos()
	{
		return pos;
	}
	
	
	
	@Override
	public IVector3 getVel()
	{
		return vel;
	}
	
	
	
	@Override
	public IVector3 getAcc()
	{
		return acc;
	}
	
	
	
	public void setPos(final IVector3 pos)
	{
		this.pos = pos;
	}
	
	
	
	public void setVel(final IVector3 vel)
	{
		this.vel = vel;
	}
	
	
	
	public void setAcc(final IVector3 acc)
	{
		this.acc = acc;
	}
	
	
	
	public void setAccTorque(final IVector3 accTorque)
	{
		this.accTorque = accTorque;
	}
	
	
	@Override
	public IVector3 getAccFromTorque()
	{
		return accTorque;
	}
	
	
}
