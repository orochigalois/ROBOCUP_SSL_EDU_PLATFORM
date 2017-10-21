/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills.test;

import edu.tigers.sumatra.math.IVector2;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.PositionDriver;
import edu.x.framework.skillsystem.skills.AMoveSkill;



public class PositionSkill extends AMoveSkill
{
	private final PositionDriver	positionDriver	= new PositionDriver();
	
	
	
	public PositionSkill(final IVector2 dest, final double orient)
	{
		this(ESkill.POSITION, dest, orient);
	}
	
	
	
	protected PositionSkill(final ESkill skillName)
	{
		this(skillName, null, 0);
	}
	
	
	
	protected PositionSkill(final ESkill skillName, final IVector2 dest, final double orient)
	{
		super(skillName);
		setPathDriver(positionDriver);
		positionDriver.setDestination(dest);
		positionDriver.setOrientation(orient);
	}
	
	
	
	public final void setDestination(final IVector2 destination)
	{
		positionDriver.setDestination(destination);
	}
	
	
	
	public final void setOrientation(final double orientation)
	{
		positionDriver.setOrientation(orientation);
	}
}
