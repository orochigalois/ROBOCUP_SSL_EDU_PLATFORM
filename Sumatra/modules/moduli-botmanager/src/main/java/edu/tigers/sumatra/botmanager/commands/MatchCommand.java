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

import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;
import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillMotorsOff;
import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.botmanager.commands.other.EKickerMode;



public class MatchCommand implements IMatchCommand
{
	private ABotSkill			skill							= new BotSkillMotorsOff();
	private double				dribbleSpeed				= 0;
	private double				kickSpeed					= 0;
	private EKickerDevice	device						= EKickerDevice.STRAIGHT;
	private EKickerMode		mode							= EKickerMode.DISARM;
	private boolean			cheer							= false;
	private int					feedbackFreq				= 20;
	private boolean			autoCharge					= false;
	
	private boolean			leftRed, leftGreen, rightRed, rightGreen;
	private boolean			setSongFinalCountdown	= false;
	
	
	@Override
	public void setSkill(final ABotSkill skill)
	{
		this.skill = skill;
		setLEDs(true, false, true, false);
	}
	
	
	@Override
	public void setDribblerSpeed(final double speed)
	{
		dribbleSpeed = speed;
	}
	
	
	@Override
	public void setKick(final double kickSpeed, final EKickerDevice device, final EKickerMode mode)
	{
		this.kickSpeed = kickSpeed;
		this.device = device;
		this.mode = mode;
	}
	
	
	@Override
	public void setCheering(final boolean enable)
	{
		cheer = enable;
	}
	
	
	@Override
	public void setFeedbackFreq(final int freq)
	{
		feedbackFreq = freq;
	}
	
	
	@Override
	public void setKickerAutocharge(final boolean enable)
	{
		autoCharge = enable;
	}
	
	
	
	@Override
	public final ABotSkill getSkill()
	{
		return skill;
	}
	
	
	
	public final double getDribbleSpeed()
	{
		return dribbleSpeed;
	}
	
	
	
	public final double getKickSpeed()
	{
		return kickSpeed;
	}
	
	
	
	public final EKickerDevice getDevice()
	{
		return device;
	}
	
	
	
	public final EKickerMode getMode()
	{
		return mode;
	}
	
	
	
	public final boolean isCheer()
	{
		return cheer;
	}
	
	
	
	public final int getFeedbackFreq()
	{
		return feedbackFreq;
	}
	
	
	
	public final boolean isAutoCharge()
	{
		return autoCharge;
	}
	
	
	@Override
	public void setLEDs(final boolean leftRed, final boolean leftGreen, final boolean rightRed, final boolean rightGreen)
	{
		this.leftGreen = leftGreen;
		this.rightGreen = rightGreen;
		this.leftRed = leftRed;
		this.rightRed = rightRed;
	}
	
	
	
	public boolean isLeftRed()
	{
		return leftRed;
	}
	
	
	
	public void setLeftRed(final boolean leftRed)
	{
		this.leftRed = leftRed;
	}
	
	
	
	public boolean isLeftGreen()
	{
		return leftGreen;
	}
	
	
	
	public void setLeftGreen(final boolean leftGreen)
	{
		this.leftGreen = leftGreen;
	}
	
	
	
	public boolean isRightRed()
	{
		return rightRed;
	}
	
	
	
	public void setRightRed(final boolean rightRed)
	{
		this.rightRed = rightRed;
	}
	
	
	
	public boolean isRightGreen()
	{
		return rightGreen;
	}
	
	
	
	public void setRightGreen(final boolean rightGreen)
	{
		this.rightGreen = rightGreen;
	}
	
	
	@Override
	public void setSongFinalCountdown(final boolean enable)
	{
		setSongFinalCountdown = enable;
	}
	
	
	
	public boolean isSetSongFinalCountdown()
	{
		return setSongFinalCountdown;
	}
}
