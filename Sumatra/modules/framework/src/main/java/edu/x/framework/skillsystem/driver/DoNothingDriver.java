/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class DoNothingDriver extends ABaseDriver implements IKickPathDriver
{
	
	
	public DoNothingDriver()
	{
		addSupportedCommand(EBotSkill.LOCAL_VELOCITY);
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.DO_NOTHING;
	}
	
	
	@Override
	public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
	{
		return AVector3.ZERO_VECTOR;
	}
	
	
	@Override
	public boolean isEnableDribbler()
	{
		return false;
	}
	
	
	@Override
	public boolean armKicker()
	{
		return false;
	}
}
