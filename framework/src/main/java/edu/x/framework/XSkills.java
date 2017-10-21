/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework;

import edu.tigers.sumatra.math.Vector2f;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.CatchSkill;
import edu.x.framework.skillsystem.skills.test.PositionSkill;



public class XSkills
{
	
	
	public XSkills()
	{
		
	}
	
	
	public CatchSkill catchSkill()
	{
		return new CatchSkill(ESkill.CATCH);
	}
	
	
	
	public PositionSkill gotoSkill(final double x, final double y, final double orient)
	{
		Vector2f v2f = new Vector2f(x, y);
		return new PositionSkill(v2f, orient);
	}
}
