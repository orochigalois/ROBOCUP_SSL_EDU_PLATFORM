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
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class BotSkillWheelVelocity extends ABotSkill
{
	@SerialData(type = ESerialDataType.INT16)
	private final int	vel[]	= new int[4];
	
	
	
	public BotSkillWheelVelocity()
	{
		super(EBotSkill.WHEEL_VELOCITY);
	}
	
	
	
	public BotSkillWheelVelocity(final double fr, final double fl, final double rl, final double rr)
	{
		super(EBotSkill.WHEEL_VELOCITY);
		
		vel[0] = (int) (fr * 200.0);
		vel[1] = (int) (fl * 200.0);
		vel[2] = (int) (rl * 200.0);
		vel[3] = (int) (rr * 200.0);
	}
	
	
	
	public BotSkillWheelVelocity(final double[] in)
	{
		super(EBotSkill.WHEEL_VELOCITY);
		
		if (in.length < 4)
		{
			return;
		}
		
		for (int i = 0; i < 4; i++)
		{
			vel[i] = (int) (in[i] * 200.0);
		}
	}
	
	
	
	public double getWheelVelocity(final int i)
	{
		if ((i > 3) || (i < 0))
		{
			return 0;
		}
		
		return vel[i] * 0.005;
	}
	
	
	
	public double[] getVelocities()
	{
		double[] result = new double[4];
		
		for (int i = 0; i < 4; i++)
		{
			result[i] = vel[i] * 0.005;
		}
		
		return result;
	}
}
