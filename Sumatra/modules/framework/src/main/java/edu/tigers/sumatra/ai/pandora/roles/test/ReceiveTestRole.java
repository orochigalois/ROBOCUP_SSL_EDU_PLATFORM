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

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.skills.AMoveToSkill;
import edu.x.framework.skillsystem.skills.KickSkill;
import edu.x.framework.skillsystem.skills.ReceiverSkill;
import edu.x.framework.skillsystem.skills.ReceiverSkill.EReceiverMode;



public class ReceiveTestRole extends ARole
{
	private final IVector2			dest;
	private final DynamicPosition	target;
	private final EReceiverMode	receiverMode;
	
	private enum EStateId
	{
		RECEIVE,
		KICK,
		INIT
	}
	
	private enum EEvent
	{
		RECEIVED,
		KICKED,
		INIT;
	}
	
	
	
	public ReceiveTestRole(final IVector2 dest, final DynamicPosition target, final EReceiverMode receiverMode)
	{
		super(ERole.RECEIVE_TEST);
		this.dest = dest;
		this.target = target;
		this.receiverMode = receiverMode;
		
		IRoleState receiveState = new ReceiveState();
		IRoleState kickState = new KickState();
		IRoleState initState = new InitState();
		setInitialState(initState);
		addTransition(EEvent.RECEIVED, kickState);
		addTransition(EEvent.KICKED, initState);
		addTransition(EEvent.INIT, receiveState);
	}
	
	
	private class InitState implements IRoleState
	{
		
		@Override
		public void doEntryActions()
		{
			AMoveToSkill move = AMoveToSkill.createMoveToSkill();
			move.getMoveCon().updateDestination(dest);
			setNewSkill(move);
		}
		
		
		@Override
		public void doUpdate()
		{
			if (GeoMath.distancePP(dest, getPos()) < 50)
			{
				triggerEvent(EEvent.INIT);
			}
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
	
	private class ReceiveState implements IRoleState
	{
		private ReceiverSkill skill;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new ReceiverSkill(receiverMode);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.RECEIVE;
		}
	}
	
	private class KickState implements IRoleState
	{
		
		@Override
		public void doEntryActions()
		{
			setNewSkill(new KickSkill(target));
		}
		
		
		@Override
		public void doUpdate()
		{
			if (GeoMath.distancePP(getPos(), getAiFrame().getWorldFrame().getBall().getPos()) > 200)
			{
				triggerEvent(EEvent.KICKED);
			}
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.KICK;
		}
	}
}
