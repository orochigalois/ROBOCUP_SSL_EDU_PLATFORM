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

import edu.tigers.sumatra.botmanager.commands.botskills.BotSkillLocalVelocity;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.statemachine.IState;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.ASkill;



public class SinWSkill extends ASkill
{
	
	
	
	public SinWSkill()
	{
		super(ESkill.SIN_W);
		setInitialState(new DoState());
	}
	
	private enum EStateId
	{
		INIT
	}
	
	
	private class DoState implements IState
	{
		long tStart;
		
		
		@Override
		public void doEntryActions()
		{
			tStart = getWorldFrame().getTimestamp();
		}
		
		
		@Override
		public void doUpdate()
		{
			double t = (getWorldFrame().getTimestamp() - tStart) / 1e9;
			double velw = 3 * Math.sin(t);
			BotSkillLocalVelocity skill = new BotSkillLocalVelocity(AVector2.ZERO_VECTOR, velw,
					getBot().getMoveConstraints());
			getMatchCtrl().setSkill(skill);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.INIT;
		}
	}
}
