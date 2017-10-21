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

import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class PullBallDriver extends ABaseDriver
{
	
	// private double driveSpeed = 0;
	private boolean	finished		= false;
	
	private IVector2	direction	= null;
	private double		speed			= 1;
	
	
	
	public PullBallDriver()
	{
		clearSupportedCommands();
		addSupportedCommand(EBotSkill.LOCAL_VELOCITY);
	}
	
	
	@Override
	public boolean isDone()
	{
		return finished;
	}
	
	
	@Override
	public void update(final ITrackedBot bot, final ABot aBot, final WorldFrame wFrame)
	{
	}
	
	
	@Override
	public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
	{
		direction = direction.scaleToNew(speed);
		return new Vector3(direction.x(), direction.y(), 0);
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.PULL_BALL;
	}
	
	
	
	public void setDirection(final IVector2 dir)
	{
		direction = dir.normalizeNew();
	}
	
	
	
	public void setSpeed(final double speed)
	{
		this.speed = speed;
	}
	
}
