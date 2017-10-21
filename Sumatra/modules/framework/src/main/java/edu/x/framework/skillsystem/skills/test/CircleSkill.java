/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills.test;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.ABaseDriver;
import edu.x.framework.skillsystem.driver.EPathDriver;
import edu.x.framework.skillsystem.skills.AMoveSkill;



public class CircleSkill extends AMoveSkill
{
	
	public CircleSkill(final double duration, final double speed)
	{
		super(ESkill.CIRCLE);
		setPathDriver(new CircleDriver(duration, speed));
	}
	
	
	private static class CircleDriver extends ABaseDriver
	{
		private long			tStart	= 0;
		private final double	duration;
		private final double	speed;
									
									
		
		public CircleDriver(final double duration, final double speed)
		{
			this.duration = duration;
			this.speed = speed;
			addSupportedCommand(EBotSkill.LOCAL_VELOCITY);
		}
		
		
		@Override
		public IVector3 getNextDestination(final ITrackedBot bot, final WorldFrame wFrame)
		{
			throw new IllegalStateException();
		}
		
		
		@Override
		public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
		{
			if (tStart == 0)
			{
				tStart = wFrame.getTimestamp();
			}
			double relTime = ((wFrame.getTimestamp() - tStart)) / 1e9 / duration;
			if (relTime > 1)
			{
				setDone(true);
				return AVector3.ZERO_VECTOR;
			}
			IVector2 vel = new Vector2(relTime * AngleMath.PI_TWO).scaleTo(speed);
			return new Vector3(vel, 0);
		}
		
		
		@Override
		public EPathDriver getType()
		{
			return EPathDriver.CIRCLE;
		}
		
	}
}
