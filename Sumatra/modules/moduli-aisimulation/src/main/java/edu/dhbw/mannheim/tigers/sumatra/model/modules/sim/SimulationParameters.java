/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class SimulationParameters
{
	private final Map<BotID, SimulationObject>	initBots		= new HashMap<>();
	private SimulationObject							initBall		= new SimulationObject();
	
	private double											speedFactor	= 1;
	
	
	
	public final SimulationObject getInitBall()
	{
		return initBall;
	}
	
	
	
	public final void setInitBall(final SimulationObject initBall)
	{
		this.initBall = initBall;
	}
	
	
	
	public final Map<BotID, SimulationObject> getInitBots()
	{
		return initBots;
	}
	
	
	public static class SimulationObject
	{
		private IVector3	pos	= new Vector3();
		private IVector3	vel	= new Vector3();
		
		
		
		public SimulationObject()
		{
		}
		
		
		
		public SimulationObject(final IVector3 pos, final IVector3 vel)
		{
			this.pos = pos;
			this.vel = vel;
		}
		
		
		
		public SimulationObject(final IVector3 pos)
		{
			this.pos = pos;
		}
		
		
		
		public final IVector3 getPos()
		{
			return pos;
		}
		
		
		
		public final void setPos(final IVector3 pos)
		{
			this.pos = pos;
		}
		
		
		
		public final IVector3 getVel()
		{
			return vel;
		}
		
		
		
		public final void setVel(final IVector3 vel)
		{
			this.vel = vel;
		}
	}
	
	
	
	public final double getSpeedFactor()
	{
		return speedFactor;
	}
	
	
	
	public final void setSpeedFactor(final double speedFactor)
	{
		this.speedFactor = speedFactor;
	}
}
