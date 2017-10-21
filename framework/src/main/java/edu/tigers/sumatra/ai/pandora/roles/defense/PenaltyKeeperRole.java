/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.defense;

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.x.framework.skillsystem.skills.AMoveToSkill;
import edu.x.framework.skillsystem.skills.PenaltyKeeperSkill;



public class PenaltyKeeperRole extends ARole
{
	private enum EStateId
	{
		MOVE_TO_GOAL_CENTER,
		BLOCK_SHOOTING_LINE
	}
	
	private enum EEvent
	{
		KEEPER_ON_GOAL_CENTER,
	}
	
	
	
	public PenaltyKeeperRole()
	{
		super(ERole.PENALTY_KEEPER);
		setInitialState(new MoveToGoalCenter());
		addTransition(EStateId.MOVE_TO_GOAL_CENTER, EEvent.KEEPER_ON_GOAL_CENTER, new BlockShootingLine());
	}
	
	
	
	private class MoveToGoalCenter implements IRoleState
	{
		private AMoveToSkill	skill;
		
		
		@Override
		public void doEntryActions()
		{
			skill = AMoveToSkill.createMoveToSkill();
			skill.getMoveCon().setPenaltyAreaAllowedOur(true);
			skill.getMoveCon().updateDestination(Geometry.getGoalOur().getGoalCenter());
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			if (skill.isDestinationReached())
			{
				triggerEvent(EEvent.KEEPER_ON_GOAL_CENTER);
			}
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.MOVE_TO_GOAL_CENTER;
		}
	}
	
	
	
	private class BlockShootingLine implements IRoleState
	{
		private PenaltyKeeperSkill	skill;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new PenaltyKeeperSkill(new DynamicPosition(getTrackedBot().getPos()));
			skill.getMoveCon().setPenaltyAreaAllowedOur(true);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			skill.setShooterPos(new DynamicPosition(getTrackedBot().getPos()));
		}
		
		
		private ITrackedBot getTrackedBot()
		{
			return getAiFrame().getTacticalField().getEnemyClosestToBall().getBot();
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.BLOCK_SHOOTING_LINE;
		}
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
