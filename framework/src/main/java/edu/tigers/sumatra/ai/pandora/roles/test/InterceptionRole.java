/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.test;

import edu.tigers.sumatra.ai.data.BotDistance;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.x.framework.skillsystem.skills.InterceptionSkill;



public class InterceptionRole extends ARole
{
	
	
	protected enum EStateId
	{
		BLOCKING
	}
	
	protected enum EEvent
	{
		
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public InterceptionRole()
	{
		super(ERole.INTERCEPTION);
		
		IRoleState blocker = new BlockerState();
		setInitialState(blocker);
	}
	
	
	private class BlockerState implements IRoleState
	{
		InterceptionSkill	skill	= new InterceptionSkill();
		
		
		@Override
		public void doEntryActions()
		{
			setNewSkill(skill);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			BotDistance nearestEnemyBot = getAiFrame().getTacticalField().getEnemyClosestToBall();
			if (nearestEnemyBot != null)
			{
				if (nearestEnemyBot.getBot() != null)
				{
					skill.setNearestEnemyBotPos(nearestEnemyBot.getBot().getPos());
				} else
				{
					skill.setNearestEnemyBotPos(null);
				}
			}
			else
			{
				skill.setNearestEnemyBotPos(null);
			}
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.BLOCKING;
		}
	}
}
