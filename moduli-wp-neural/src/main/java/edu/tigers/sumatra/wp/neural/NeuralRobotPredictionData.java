/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.neural;

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;



public class NeuralRobotPredictionData implements INeuralPredicitonData
{
	
	private long		lastUpdate	= 0;
	private CamRobot	lastData;
	private IVector2	pos			= AVector2.ZERO_VECTOR;
	private IVector2	vel			= AVector2.ZERO_VECTOR;
	private IVector2	acc			= AVector2.ZERO_VECTOR;
	private double		orient		= 0;
	private double		orientVel	= 0;
	private double		orientAcc	= 0;
	private BotID		id;
	
	
	
	public void update(final BotID id, final CamRobot cr, final long time,
			final IVector2 pos, final IVector2 vel, final IVector2 acc, final double orient,
			final double orientVel, final double orientAcc)
	{
		lastUpdate = time;
		lastData = cr;
		this.id = id;
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
		this.orient = orient;
		this.orientVel = orientVel;
		this.orientAcc = orientAcc;
	}
	
	
	
	public long getUpdateTimestamp()
	{
		return lastUpdate;
	}
	
	
	
	public CamRobot getLastData()
	{
		return lastData;
	}
	
	
	
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public IVector2 getVel()
	{
		return vel;
	}
	
	
	
	public IVector2 getAcc()
	{
		return acc;
	}
	
	
	
	public double getOrient()
	{
		return orient;
	}
	
	
	
	public double getOrientVel()
	{
		return orientVel;
	}
	
	
	
	public double getOrientAcc()
	{
		return orientAcc;
	}
	
	
	
	public BotID getId()
	{
		return id;
	}
}
