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

import edu.tigers.sumatra.math.Vector2;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.test.PositionSkill;



public class StraightMoveSkill extends PositionSkill
{
	
	private final int		distance;
	
	private final double	angle;
								
								
	
	public StraightMoveSkill(final int distance, final double angle)
	{
		super(ESkill.STRAIGHT_MOVE);
		
		this.distance = distance;
		this.angle = angle;
	}
	
	
	@Override
	protected void onSkillStarted()
	{
		setDestination(getPos().addNew(new Vector2(getAngle() + angle).multiply(distance)));
	}
	
	
}
