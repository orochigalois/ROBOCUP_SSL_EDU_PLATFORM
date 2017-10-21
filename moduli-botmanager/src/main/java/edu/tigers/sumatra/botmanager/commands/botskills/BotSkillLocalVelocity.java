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



public class BotSkillLocalVelocity extends AMoveBotSkill
{
	private static final int	MAX_ACC		= 10;
	private static final int	MAX_ACC_W	= 100;
	private static final int	MAX_JERK		= 100;
	private static final int	MAX_JERK_W	= 1000;
	
	
	@SerialData(type = ESerialDataType.INT16)
	private final int				vel[]			= new int[3];
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						accMax		= 0;
	@SerialData(type = ESerialDataType.UINT8)
	private int						accMaxW		= 0;
	@SerialData(type = ESerialDataType.UINT8)
	private int						jerkMax		= 0;
	@SerialData(type = ESerialDataType.UINT8)
	private int						jerkMaxW		= 0;
	
	
	
	private BotSkillLocalVelocity()
	{
		super(EBotSkill.LOCAL_VELOCITY);
	}
	
	
	
	public BotSkillLocalVelocity(final MoveConstraints mc)
	{
		this();
		setAccMax(mc.getAccMax());
		setAccMaxW(mc.getAccMaxW());
		setJerkMaxW(mc.getJerkMaxW());
		setJerkMax(mc.getJerkMax());
	}
	
	
	
	public BotSkillLocalVelocity(final IVector2 xy, final double orientation, final MoveConstraints mc)
	{
		this(mc);
		
		vel[0] = (int) (xy.x() * 1000.0);
		vel[1] = (int) (xy.y() * 1000.0);
		vel[2] = (int) (orientation * 1000.0);
	}
	
	
	
	public BotSkillLocalVelocity(final IVector2 xy, final double orientation,
			final double accMax, final double accMaxW, final double jerkMax, final double jerkMaxW)
	{
		this();
		
		vel[0] = (int) (xy.x() * 1000.0);
		vel[1] = (int) (xy.y() * 1000.0);
		vel[2] = (int) (orientation * 1000.0);
		
		setAccMax(accMax);
		setAccMaxW(accMaxW);
		setJerkMaxW(jerkMaxW);
		setJerkMax(jerkMax);
	}
	
	
	
	public double getX()
	{
		return vel[0] / 1000.0;
	}
	
	
	
	public double getY()
	{
		return vel[1] / 1000.0;
	}
	
	
	
	public double getW()
	{
		return vel[2] / 1000.0;
	}
	
	
	
	public final void setAccMax(final double val)
	{
		accMax = (int) ((val / MAX_ACC) * 255);
	}
	
	
	
	public final void setAccMaxW(final double val)
	{
		accMaxW = (int) ((val / MAX_ACC_W) * 255);
	}
	
	
	
	public double getAccMax()
	{
		return (accMax / 255.0) * MAX_ACC;
	}
	
	
	
	public double getAccMaxW()
	{
		return (accMaxW / 255.0) * MAX_ACC_W;
	}
	
	
	
	public final void setJerkMax(final double val)
	{
		jerkMax = (int) ((val / MAX_JERK) * 255);
	}
	
	
	
	public final void setJerkMaxW(final double val)
	{
		jerkMaxW = (int) ((val / MAX_JERK_W) * 255);
	}
	
	
	
	public double getJerkMax()
	{
		return (jerkMax / 255.0) * MAX_JERK;
	}
	
	
	
	public double getJerkMaxW()
	{
		return (jerkMaxW / 255.0) * MAX_JERK_W;
	}
}
