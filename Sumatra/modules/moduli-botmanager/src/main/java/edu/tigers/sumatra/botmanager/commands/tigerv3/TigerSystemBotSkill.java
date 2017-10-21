/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.tigerv3;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;



public class TigerSystemBotSkill extends ACommand
{
	private final ABotSkill	skill;
	
	
	
	public TigerSystemBotSkill()
	{
		this(null);
	}
	
	
	
	public TigerSystemBotSkill(final ABotSkill skill)
	{
		super(ECommand.CMD_SYSTEM_BOT_SKILL);
		this.skill = skill;
	}
	
	
	
	public final ABotSkill getSkill()
	{
		return skill;
	}
}
