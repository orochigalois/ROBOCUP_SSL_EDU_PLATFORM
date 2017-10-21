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

import edu.tigers.sumatra.bot.MoveConstraints;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;
import edu.tigers.sumatra.math.IVector2;



public class BotSkillGlobalVelocity extends AMoveBotSkill
{
	@SerialData(type = ESerialDataType.INT16)
	private final int vel[] = new int[3];
	
	
	private BotSkillGlobalVelocity()
	{
		super(EBotSkill.GLOBAL_VELOCITY);
	}
	
	
	
	public BotSkillGlobalVelocity(final MoveConstraints mc)
	{
		super(EBotSkill.GLOBAL_VELOCITY);
	}
	
	
	
	public BotSkillGlobalVelocity(final IVector2 xy, final double orientation, final MoveConstraints mc)
	{
		this(mc);
		
		vel[0] = (int) (xy.x() * 1000.0);
		vel[1] = (int) (xy.y() * 1000.0);
		vel[2] = (int) (orientation * 1000.0);
	}
}
