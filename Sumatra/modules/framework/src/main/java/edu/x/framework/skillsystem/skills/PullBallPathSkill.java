/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;

import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.botmanager.commands.other.EKickerMode;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.statemachine.IState;
import edu.tigers.sumatra.trajectory.DribblePath;
import edu.tigers.sumatra.trajectory.HermiteSplinePart2D;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.KickBallTrajDriver;
import edu.x.framework.skillsystem.driver.PullBallPathDriver;



public class PullBallPathSkill extends AMoveSkill
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private DribblePath path = null;
	
	private enum ESate
	{
		GET,
		PULL;
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public PullBallPathSkill()
	{
		super(ESkill.PULL_BALL_PATH);
		setInitialState(new GetBallControlState());
		addTransition(ESate.PULL, new PullState());
	}
	
	
	
	public DribblePath getPath()
	{
		return path;
	}
	
	
	
	public void setPath(final DribblePath path)
	{
		this.path = path;
	}
	
	private class GetBallControlState implements IState
	{
		
		private KickBallTrajDriver driver = null;
		
		
		@Override
		public void doEntryActions()
		{
			if (path == null)
			{
				IVector2 ballPos = getWorldFrame().getBall().getPos();
				IVector2 target = null;
				
				target = Geometry.getGoalTheir().getGoalCenter();
				
				IVector2 ballToTarget = target.subtractNew(ballPos);
				IVector2 normal = ballToTarget.getNormalVector().normalizeNew();
				
				IVector2 endPos = ballPos.addNew(ballToTarget.scaleToNew(500)).add(normal.scaleToNew(-300));
				// IVector2 botBallNormal = ball2Pos.subtractNew(botPos).normalizeNew();
				
				IVector2 initVel = normal.multiplyNew(-800).addNew(ballToTarget.normalizeNew().multiply(300));
				IVector2 endVel = target.subtractNew(endPos).scaleTo(500);
				path = new DribblePath(new HermiteSplinePart2D(getWorldFrame().getBall().getPos(), endPos,
						initVel, endVel, 1.0));
				path.setTarget(Geometry.getGoalTheir().getGoalCenter());
			}
			IVector2 initVel = path.getPosition(0.1).subtractNew(path.getPosition(0)).scaleToNew(500);
			IVector2 target = getWorldFrame().getBall().getPos().addNew(initVel);
			driver = new KickBallTrajDriver(new DynamicPosition(target));
			driver.setDistBehindBallHitTarget(0);
			setPathDriver(driver);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			if (getTBot().hasBallContact())
			{
				triggerEvent(ESate.PULL);
			}
		}
		
		
		@Override
		public Enum<?> getIdentifier()
		{
			return ESate.GET;
		}
	}
	
	private class PullState implements IState
	{
		
		private PullBallPathDriver driver = null;
		
		
		@Override
		public void doEntryActions()
		{
			driver = new PullBallPathDriver(path);
			setPathDriver(driver);
		}
		
		
		@Override
		public void doExitActions()
		{
			getBot().getMatchCtrl().setKick(0.0, EKickerDevice.STRAIGHT, EKickerMode.ARM);
		}
		
		
		@Override
		public void doUpdate()
		{
			getBot().getMatchCtrl().setDribblerSpeed(5000 + (15000 * (1 - Math.min(1, driver.getProgress()))));
			if (driver.isFinished())
			{
				getBot().getMatchCtrl().setKick(8.0, EKickerDevice.STRAIGHT, EKickerMode.ARM);
			}
		}
		
		
		@Override
		public Enum<?> getIdentifier()
		{
			return ESate.PULL;
		}
	}
}
