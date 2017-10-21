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

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.MotionContext;



public interface ISimulatedObject
{
	
	default void update(double dt)
	{
	}
	
	
	
	void step(double dt, MotionContext context);
	
	
	
	void setVel(final IVector3 vel);
	
	
	
	void addVel(final IVector3 vector3);
	
	
	
	void setPos(final IVector3 pos);
	
	
	
	IVector3 getVel();
	
	
	
	IVector3 getPos();
}
