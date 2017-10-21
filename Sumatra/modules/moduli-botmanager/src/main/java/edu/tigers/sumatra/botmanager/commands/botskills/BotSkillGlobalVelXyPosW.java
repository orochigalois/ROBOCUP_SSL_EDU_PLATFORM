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
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



public class BotSkillGlobalVelXyPosW extends ABotSkill
{
	@SerialData(type = ESerialDataType.INT16)
	private final int				vel[]			= new int[2];
	
	@SerialData(type = ESerialDataType.INT16)
	private final int				targetAngle;
	
	private static final int	MAX_VEL_W	= 30;
	private static final int	MAX_ACC_W	= 100;
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						velMaxW		= 0;
	@SerialData(type = ESerialDataType.UINT8)
	private int						accMaxW		= 0;
	
	
	
	public BotSkillGlobalVelXyPosW()
	{
		this(AVector2.ZERO_VECTOR, 0);
	}
	
	
	
	public BotSkillGlobalVelXyPosW(final IVector2 xyVel, final double targetAngle)
	{
		super(EBotSkill.GLOBAL_VEL_XY_POS_W);
		vel[0] = (int) (xyVel.x() * 1000.0);
		vel[1] = (int) (xyVel.y() * 1000.0);
		this.targetAngle = (int) (targetAngle * 1000);
	}
	
	
	
	public IVector2 getVel()
	{
		return new Vector2(vel[0], vel[1]).multiply(1e-3);
	}
	
	
	
	public double getTargetAngle()
	{
		return targetAngle / 1000.0;
	}
	
	
	
	public final void setVelMaxW(final double val)
	{
		velMaxW = (int) ((val / MAX_VEL_W) * 255);
	}
	
	
	
	public final void setAccMaxW(final double val)
	{
		accMaxW = (int) ((val / MAX_ACC_W) * 255);
	}
	
	
	
	public double getVelMaxW()
	{
		return (velMaxW / 255) * MAX_VEL_W;
	}
	
	
	
	public double getAccMaxW()
	{
		return (accMaxW / 255) * MAX_ACC_W;
	}
}
