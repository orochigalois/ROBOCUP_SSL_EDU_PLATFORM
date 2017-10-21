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

import org.apache.commons.lang.NotImplementedException;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.IVectorN;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.math.VectorN;



public class RandomMotorModel extends AMotorModel
{
	@Override
	protected VectorN getWheelSpeedInternal(final IVector3 targetVel)
	{
		return new VectorN(4);
	}
	
	
	@Override
	protected Vector3 getXywSpeedInternal(final IVectorN wheelSpeed)
	{
		throw new NotImplementedException();
	}
	
	
	@Override
	public EMotorModel getType()
	{
		return EMotorModel.RANDOM;
	}
}
