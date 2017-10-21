/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.tigerv2;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.BotSkillFactory;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.commands.IMatchCommand;
import edu.tigers.sumatra.botmanager.commands.MatchCommand;
import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillMotorsOff;
import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.botmanager.commands.other.EKickerMode;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerSystemMatchCtrl extends ACommand implements IMatchCommand
{
	
	public static final int		MAX_SKILL_DATA_SIZE	= 12;
	
	
	@SerialData(type = ESerialDataType.INT16)
	private final int				curPosition[]			= new int[3];
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						posDelay;
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						camId;
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						kickSpeed;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						kickFlags;
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						dribblerSpeed;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						skillId;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						flags;
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int						feedbackFreq;
	
	
	@SerialData(type = ESerialDataType.TAIL)
	private byte					skillData[];
	
	private ABotSkill				skill;
	
	private static final int	UNUSED_FIELD			= 0x7FFF;
	
	
	
	public TigerSystemMatchCtrl()
	{
		super(ECommand.CMD_SYSTEM_MATCH_CTRL);
		
		curPosition[0] = UNUSED_FIELD;
		curPosition[1] = UNUSED_FIELD;
		curPosition[2] = UNUSED_FIELD;
		
		setSkill(new BotSkillMotorsOff());
	}
	
	
	
	public TigerSystemMatchCtrl(final MatchCommand matchCtrl)
	{
		this();
		setSkill(matchCtrl.getSkill());
		setDribblerSpeed(matchCtrl.getDribbleSpeed());
		setFeedbackFreq(matchCtrl.getFeedbackFreq());
		setKickerAutocharge(matchCtrl.isAutoCharge());
		setKick(matchCtrl.getKickSpeed(), matchCtrl.getDevice(), matchCtrl.getMode());
		setCheering(matchCtrl.isCheer());
		setLEDs(matchCtrl.isLeftRed(), matchCtrl.isLeftGreen(), matchCtrl.isRightRed(), matchCtrl.isRightGreen());
		setSongFinalCountdown(matchCtrl.isSetSongFinalCountdown());
	}
	
	
	
	@Override
	public void setSkill(final ABotSkill skill)
	{
		this.skill = skill;
		skillData = BotSkillFactory.getInstance().encode(skill);
		skillId = skill.getType().getId();
	}
	
	
	
	@Override
	public final ABotSkill getSkill()
	{
		return skill;
	}
	
	
	
	@Override
	public void setDribblerSpeed(final double speed)
	{
		if (speed < 0)
		{
			dribblerSpeed = 0;
		} else
		{
			dribblerSpeed = ((int) (speed + 50.0)) / 100;
		}
	}
	
	
	
	@Override
	public void setFeedbackFreq(final int freq)
	{
		feedbackFreq = freq;
	}
	
	
	
	@Override
	public void setKickerAutocharge(final boolean enable)
	{
		kickFlags &= ~(0x80);
		
		if (enable)
		{
			kickFlags |= 0x80;
		}
	}
	
	
	
	@Override
	public void setKick(final double kickSpeed, final EKickerDevice device, final EKickerMode mode)
	{
		setKick(kickSpeed, device.getValue(), mode.getId());
	}
	
	
	
	public void setKick(final double kickSpeed, final int device, final int mode)
	{
		int kickSpeedA = (int) (kickSpeed);
		int kickSpeedB = ((((int) (kickSpeed * 1000)) % 1000) + 15) / 32;
		
		if (kickSpeed > 7.96875)
		{
			kickSpeedA = 7;
			kickSpeedB = 31;
		}
		
		this.kickSpeed = (kickSpeedA << 5) | kickSpeedB;
		kickFlags &= ~(0x7F); // setAutocharge also modifies a bit in this field
		kickFlags |= device | (mode << 1);
	}
	
	
	
	@Override
	public void setCheering(final boolean enable)
	{
		if (enable)
		{
			flags |= 0x02;
		} else
		{
			flags &= ~0x02;
		}
	}
	
	
	
	@Override
	public void setSongFinalCountdown(final boolean enable)
	{
		if (enable)
		{
			flags |= 0x04;
		} else
		{
			flags &= ~0x04;
		}
	}
	
	
	
	@Override
	public void setLEDs(final boolean leftRed, final boolean leftGreen, final boolean rightRed, final boolean rightGreen)
	{
		flags &= ~0xF0;
		
		if (leftRed)
		{
			flags |= 0x10;
		}
		
		if (leftGreen)
		{
			flags |= 0x20;
		}
		
		if (rightRed)
		{
			flags |= 0x40;
		}
		
		if (rightGreen)
		{
			flags |= 0x80;
		}
	}
}
