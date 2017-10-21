/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class VelocityDriver extends ABaseDriver
{
	private double		rotationalSpeed		= 0.0;
	private double		translationalSpeed	= 0.0;
	private IVector2	direction				= null;
														
														
	
	public enum EVelocityMode
	{
		
		WHEEL_VELOCITY,
		
		CARTESIAN_LOCAL_VELOCITY;
	}
	
	
	
	public VelocityDriver()
	{
		this(EVelocityMode.CARTESIAN_LOCAL_VELOCITY);
	}
	
	
	
	public VelocityDriver(final EVelocityMode mode)
	{
		clearSupportedCommands();
		switch (mode)
		{
			case CARTESIAN_LOCAL_VELOCITY:
				addSupportedCommand(EBotSkill.LOCAL_VELOCITY);
				break;
			case WHEEL_VELOCITY:
				addSupportedCommand(EBotSkill.WHEEL_VELOCITY);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.VELOCITY;
	}
	
	
	
	public void setRotationalSpeed(final double omega)
	{
		rotationalSpeed = omega;
	}
	
	
	
	public void setTranslationalSpeed(final double speed)
	{
		translationalSpeed = speed;
	}
	
	
	
	public void setSpeed(final double translSpeed, final double rotSpeed)
	{
		setTranslationalSpeed(translSpeed);
		setRotationalSpeed(rotSpeed);
	}
	
	
	
	public void setDirection(final IVector2 direction)
	{
		this.direction = direction;
	}
	
	
	
	public double getTranslationalSpeed()
	{
		return translationalSpeed;
	}
	
	
	
	public double getRotationalSpeed()
	{
		return rotationalSpeed;
	}
	
	
	@Override
	public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
	{
		IVector3 vel;
		if (direction == null)
		{
			vel = new Vector3(0.0, 0.0, rotationalSpeed);
		} else
		{
			vel = new Vector3(direction.scaleToNew(translationalSpeed), rotationalSpeed);
		}
		return vel;
	}
	
}
