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

import edu.tigers.sumatra.math.IVector2;



public class Collision implements ICollision
{
	private final IVector2	pos;
	private final IVector2	normal;
	private final IVector2	objectVel;
	
	
	
	public Collision(final IVector2 pos, final IVector2 normal, final IVector2 objectVel)
	{
		super();
		this.pos = pos;
		this.normal = normal;
		this.objectVel = objectVel;
	}
	
	
	
	@Override
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	
	@Override
	public IVector2 getNormal()
	{
		return normal;
	}
	
	
	@Override
	public IVector2 getObjectVel()
	{
		return objectVel;
	}
}
