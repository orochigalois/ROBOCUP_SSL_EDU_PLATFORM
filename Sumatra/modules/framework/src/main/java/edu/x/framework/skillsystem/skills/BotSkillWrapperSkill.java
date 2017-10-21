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

import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillMotorsOff;
import edu.x.framework.skillsystem.ESkill;



public class BotSkillWrapperSkill extends ASkill
{
	private ABotSkill skill;
	
	
	
	public BotSkillWrapperSkill(final ABotSkill botSkill)
	{
		super(ESkill.BOT_SKILL_WRAPPER);
		skill = botSkill;
	}
	
	
	
	public BotSkillWrapperSkill()
	{
		super(ESkill.BOT_SKILL_WRAPPER);
		skill = new BotSkillMotorsOff();
	}
	
	
	@Override
	protected void doCalcActionsBeforeStateUpdate()
	{
		getMatchCtrl().setSkill(skill);
	}
	
	
	
	public final ABotSkill getSkill()
	{
		return skill;
	}
	
	
	
	public final void setSkill(final ABotSkill skill)
	{
		this.skill = skill;
	}
}
