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

import java.util.Random;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.IVectorN;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.math.VectorN;



public abstract class AMotorModel implements IMotorModel
{
	@SuppressWarnings("unused")
	private static final Logger	log			= Logger.getLogger(AMotorModel.class.getName());
	
	private Random						rnd			= new Random();
	private IVectorN					motorNoise	= new VectorN(4);
	private IVector3					xywNoise		= Vector3.ZERO_VECTOR;
	
	
	protected abstract VectorN getWheelSpeedInternal(IVector3 targetVel);
	
	
	protected abstract Vector3 getXywSpeedInternal(IVectorN wheelSpeed);
	
	
	@Override
	public final IVectorN getWheelSpeed(final IVector3 targetVel)
	{
		IVector3 xywVel = targetVel;
		if (!xywNoise.isZeroVector())
		{
			xywVel = targetVel.addNew(xywNoise.applyNew(v -> v * rnd.nextGaussian()));
		}
		VectorN ws = getWheelSpeedInternal(xywVel);
		if (!motorNoise.isZeroVector())
		{
			ws.add(motorNoise.applyNew(f -> f * (rnd.nextGaussian())));
		}
		return ws;
	}
	
	
	@Override
	public final IVector3 getXywSpeed(final IVectorN wheelSpeed)
	{
		IVectorN ws = wheelSpeed;
		if (!motorNoise.isZeroVector())
		{
			ws = wheelSpeed.addNew(motorNoise.applyNew(v -> v * rnd.nextGaussian()));
		}
		
		Vector3 xyw = getXywSpeedInternal(ws);
		if (!xywNoise.isZeroVector())
		{
			xyw.add(xywNoise.applyNew(f -> f + (rnd.nextGaussian())));
		}
		return xyw;
	}
	
	
	
	public final Random getRnd()
	{
		return rnd;
	}
	
	
	
	public final void setRnd(final Random rnd)
	{
		this.rnd = rnd;
	}
	
	
	
	@Override
	public final void setMotorNoise(final IVectorN motorNoise)
	{
		if (motorNoise.getNumDimensions() == 4)
		{
			this.motorNoise = motorNoise;
		} else if (motorNoise.getNumDimensions() == 1)
		{
			this.motorNoise = new VectorN(4).apply(v -> motorNoise.x());
		} else
		{
			log.error("Invalid motor noise size: " + motorNoise.getNumDimensions());
		}
	}
	
	
	
	@Override
	public final void setXywNoise(final IVector3 xywNoise)
	{
		if (xywNoise.getNumDimensions() == 3)
		{
			this.xywNoise = xywNoise;
		} else if (xywNoise.getNumDimensions() == 1)
		{
			this.xywNoise = new Vector3().apply(v -> xywNoise.x());
		} else
		{
			log.error("Invalid xyw noise size: " + xywNoise.getNumDimensions());
		}
	}
	
}
