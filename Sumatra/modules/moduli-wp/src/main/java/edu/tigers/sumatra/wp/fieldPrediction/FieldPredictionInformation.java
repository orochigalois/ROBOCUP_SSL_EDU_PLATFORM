/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.fieldPrediction;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent(version = 3)
public class FieldPredictionInformation
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private final IVector2	pos;
	private final IVector2	vel;
	
	
	private final double		firstCrashAfterTime;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	private FieldPredictionInformation()
	{
		pos = new Vector2(AVector2.ZERO_VECTOR);
		vel = new Vector2(AVector2.ZERO_VECTOR);
		firstCrashAfterTime = 0;
	}
	
	
	
	public FieldPredictionInformation(final IVector2 pos, final IVector2 vel, final double firstCrashAfterTime)
	{
		super();
		this.pos = new Vector2(pos);
		this.vel = new Vector2(vel);
		this.firstCrashAfterTime = firstCrashAfterTime;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public IVector2 getPosAt(double time)
	{
		if (time > firstCrashAfterTime)
		{
			time = firstCrashAfterTime;
		}
		IVector2 velocityLength = vel.multiplyNew(time);
		// if (time > 1)
		// {
		// velocityLength.scaleToNew(1.0 / time);
		// }
		return pos.addNew(velocityLength);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public double getFirstCrashAfterTime()
	{
		return firstCrashAfterTime;
	}
	
	
	
	public FieldPredictionInformation mirror()
	{
		FieldPredictionInformation fpi = new FieldPredictionInformation(pos.multiplyNew(-1), vel.multiplyNew(-1),
				firstCrashAfterTime);
		return fpi;
	}
	
	
	
	public IVector2 getVel()
	{
		return vel;
	}
}
