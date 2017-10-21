/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.move;

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.x.framework.skillsystem.MovementCon;
import edu.x.framework.skillsystem.skills.AMoveToSkill;



public class MoveRole extends ARole
{
	
	public enum EMoveBehavior
	{
		
		NORMAL,
		
		LOOK_AT_BALL,
		
		DO_COMPLETE;
	}
	
	private enum EStateId
	{
		MOVING;
	}
	
	private enum EEvent
	{
		DONE,
		DEST_UPDATE
	}
	
	private final EMoveBehavior	behavior;
	private final AMoveToSkill		skill;
											
											
	
	public MoveRole(final EMoveBehavior behavior)
	{
		super(ERole.MOVE);
		IRoleState state = new MovingState();
		setInitialState(state);
		addEndTransition(EStateId.MOVING, EEvent.DONE);
		this.behavior = behavior;
		skill = AMoveToSkill.createMoveToSkill();
	}
	
	
	
	public MoveRole(final IVector2 dest, final double orientation)
	{
		this(EMoveBehavior.DO_COMPLETE);
		skill.getMoveCon().updateDestination(dest);
		skill.getMoveCon().updateTargetAngle(orientation);
	}
	
	
	
	private class MovingState implements IRoleState
	{
		
		
		@Override
		public void doEntryActions()
		{
			
			setNewSkill(skill);
			switch (behavior)
			{
				case LOOK_AT_BALL:
					skill.getMoveCon().updateLookAtTarget(getAiFrame().getWorldFrame().getBall());
					break;
				default:
					// nothing to do
			}
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			if (skill.isDestinationReached() && (behavior == EMoveBehavior.DO_COMPLETE))
			{
				triggerEvent(EEvent.DONE);
			}
		}
		
		
		@Override
		public EStateId getIdentifier()
		{
			return EStateId.MOVING;
		}
		
	}
	
	
	
	public final MovementCon getMoveCon()
	{
		return skill.getMoveCon();
	}
	
	
	
	
	public final boolean isDestinationReached()
	{
		return skill.isDestinationReached();
	}
}
