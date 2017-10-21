/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;
import com.github.g3force.instanceables.InstanceableParameter;

import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillGlobalPosition;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillGlobalVelXyPosW;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillGlobalVelocity;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillLocalVelocity;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillMotorsOff;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillSine;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillWheelVelocity;
import edu.tigers.sumatra.math.IVector2;



public enum EBotSkill implements IInstanceableEnum
{
	
	MOTORS_OFF(0, new InstanceableClass(BotSkillMotorsOff.class)),
	
	
	LOCAL_VELOCITY(1, new InstanceableClass(BotSkillLocalVelocity.class,
			new InstanceableParameter(IVector2.class, "xy", "0,0"),
			new InstanceableParameter(Double.TYPE, "w", "0"),
			new InstanceableParameter(Double.TYPE, "accMax", "3"),
			new InstanceableParameter(Double.TYPE, "accMaxW", "50"),
			new InstanceableParameter(Double.TYPE, "jerkMax", "30"),
			new InstanceableParameter(Double.TYPE, "jerkMaxW", "500"))),
	
	WHEEL_VELOCITY(11, new InstanceableClass(BotSkillWheelVelocity.class,
			new InstanceableParameter(Double.TYPE, "FR", "0"),
			new InstanceableParameter(Double.TYPE, "FL", "0"),
			new InstanceableParameter(Double.TYPE, "RL", "0"),
			new InstanceableParameter(Double.TYPE, "RR", "0"))),
	
	
	GLOBAL_POSITION(3, new InstanceableClass(BotSkillGlobalPosition.class,
			new InstanceableParameter(IVector2.class, "dest", "0,0"),
			new InstanceableParameter(Double.TYPE, "orient", "0"),
			new InstanceableParameter(Double.TYPE, "velMax", "3"),
			new InstanceableParameter(Double.TYPE, "velMaxW", "10"),
			new InstanceableParameter(Double.TYPE, "accMax", "3"),
			new InstanceableParameter(Double.TYPE, "accMaxW", "50"))),
	
	
	BOT_SKILL_SINE(4, new InstanceableClass(BotSkillSine.class)),
	
	
	GLOBAL_VELOCITY(2, new InstanceableClass(BotSkillGlobalVelocity.class,
			new InstanceableParameter(IVector2.class, "xy", "0,0"),
			new InstanceableParameter(Double.TYPE, "w", "0"))),
	
	
	GLOBAL_VEL_XY_POS_W(12, new InstanceableClass(BotSkillGlobalVelXyPosW.class,
			new InstanceableParameter(IVector2.class, "xyVel", "0,0"),
			new InstanceableParameter(Double.TYPE, "targetAngle", "0")));
	
	private final InstanceableClass	clazz;
	private final int						id;
	
	
	
	private EBotSkill(final int id, final InstanceableClass clazz)
	{
		this.clazz = clazz;
		this.id = id;
	}
	
	
	
	@Override
	public final InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
	
	
	
	public Class<?> getClazz()
	{
		return clazz.getImpl();
	}
	
	
	
	public int getId()
	{
		return id;
	}
}
