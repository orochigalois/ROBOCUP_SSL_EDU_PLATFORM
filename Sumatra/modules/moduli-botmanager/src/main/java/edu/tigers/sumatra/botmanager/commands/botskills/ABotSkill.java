/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.botskills;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;



public abstract class ABotSkill
{
	private final EBotSkill	skill;
	
	
	protected ABotSkill(final EBotSkill skill)
	{
		this.skill = skill;
	}
	
	
	
	public EBotSkill getType()
	{
		return skill;
	}
}
