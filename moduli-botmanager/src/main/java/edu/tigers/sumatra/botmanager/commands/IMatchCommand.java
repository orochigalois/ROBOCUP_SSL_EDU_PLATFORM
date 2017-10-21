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
import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.botmanager.commands.other.EKickerMode;



public interface IMatchCommand
{
	
	
	void setSkill(ABotSkill skill);
	
	
	
	void setDribblerSpeed(double speed);
	
	
	
	void setKick(double kickSpeed, EKickerDevice device, EKickerMode mode);
	
	
	
	void setCheering(boolean enable);
	
	
	
	void setFeedbackFreq(final int freq);
	
	
	
	void setKickerAutocharge(final boolean enable);
	
	
	
	ABotSkill getSkill();
	
	
	
	void setLEDs(final boolean leftRed, final boolean leftGreen, final boolean rightRed, final boolean rightGreen);
	
	
	
	void setSongFinalCountdown(final boolean enable);
}