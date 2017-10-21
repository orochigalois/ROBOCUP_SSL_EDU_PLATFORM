/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.AroundBallDriver;



public class DelayedKickSkill extends AMoveSkill
{
	private IVector2						target;
	
	private final AroundBallDriver	driver;
	
	
	
	public DelayedKickSkill(final IVector2 target)
	{
		super(ESkill.DELAYED_KICK);
		this.target = target;
		driver = new AroundBallDriver(new DynamicPosition(target));
		setPathDriver(driver);
	}
	
	
	@Override
	protected void beforeStateUpdate()
	{
	}
	
	
	
	public final IVector2 getTarget()
	{
		return target;
	}
	
	
	
	public final void setTarget(final IVector2 target)
	{
		this.target = target;
	}
	
	
}
