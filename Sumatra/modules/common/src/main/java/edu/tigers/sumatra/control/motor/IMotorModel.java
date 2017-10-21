/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.control.motor;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.IVectorN;



public interface IMotorModel
{
	
	IVectorN getWheelSpeed(IVector3 targetVel);
	
	
	
	IVector3 getXywSpeed(IVectorN wheelSpeed);
	
	
	
	void setMotorNoise(final IVectorN motorNoise);
	
	
	
	void setXywNoise(final IVector3 xywNoise);
	
	
	
	EMotorModel getType();
}
