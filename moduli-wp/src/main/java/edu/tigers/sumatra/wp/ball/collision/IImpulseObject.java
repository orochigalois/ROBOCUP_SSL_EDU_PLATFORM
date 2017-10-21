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

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.ball.IState;



public interface IImpulseObject
{
	
	IVector3 getImpulse(IVector3 pos);
	
	
	
	IVector3 getTorqueAcc(IState state);
}
