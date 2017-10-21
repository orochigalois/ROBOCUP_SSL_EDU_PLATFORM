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

import edu.tigers.sumatra.ai.sisyphus.TrajectoryGenerator;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.statemachine.IState;
import edu.tigers.sumatra.trajectory.ITrajectory;
import edu.tigers.sumatra.trajectory.TrajectoryXyw;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.SplinePathDriver;



public class MoveBangBangSkill extends AMoveSkill
{
	private IVector2	dest;
	private double		orient;
	private EBotSkill	botSkill;
	
	private double		maxAcc	= 30;
	private double		maxVel	= 5;
	
	
	
	public MoveBangBangSkill(final IVector2 dest, final double orient)
	{
		this(dest, orient, EBotSkill.LOCAL_VELOCITY);
	}
	
	
	
	public MoveBangBangSkill(final IVector2 dest, final double orient, final EBotSkill botSkill)
	{
		super(ESkill.MOVE_BANG_BANG);
		this.dest = dest;
		this.orient = orient;
		this.botSkill = botSkill;
		setInitialState(new DefState());
	}
	
	
	private enum EStateId
	{
		DEFAULT
	}
	
	
	private class DefState implements IState
	{
		ITrajectory<IVector2>	trajXY;
		ITrajectory<Double>		trajW;
		
		
		@Override
		public void doEntryActions()
		{
			trajXY = new TrajectoryGenerator().generatePositionTrajectory(getTBot(), dest);
			trajW = new TrajectoryGenerator().generateRotationTrajectory(getTBot(), orient, maxAcc, maxVel);
			ITrajectory<IVector3> traj = new TrajectoryXyw(trajXY, trajW);
			SplinePathDriver driver = new SplinePathDriver(traj);
			driver.clearSupportedCommands();
			driver.addSupportedCommand(botSkill);
			setPathDriver(driver);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			
		}
		
		
		@Override
		public Enum<?> getIdentifier()
		{
			return EStateId.DEFAULT;
		}
		
	}
	
	
	
	public double getMaxAcc()
	{
		return maxAcc;
	}
	
	
	
	public void setMaxAcc(final double maxAcc)
	{
		this.maxAcc = maxAcc;
	}
	
	
	
	public double getMaxVel()
	{
		return maxVel;
	}
	
	
	
	public void setMaxVel(final double maxVel)
	{
		this.maxVel = maxVel;
	}
	
	
}
