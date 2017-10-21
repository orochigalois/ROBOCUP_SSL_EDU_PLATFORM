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

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;



public interface ICollisionObject
{
	
	IVector2 getVel();
	
	
	
	Optional<ICollision> getCollision(IVector3 prePos, IVector3 postPos);
}
