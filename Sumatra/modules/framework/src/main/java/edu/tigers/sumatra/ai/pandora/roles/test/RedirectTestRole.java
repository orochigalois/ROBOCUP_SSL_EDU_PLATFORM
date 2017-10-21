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

import java.util.List;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.math.OffensiveMath;
import edu.tigers.sumatra.ai.data.math.RedirectMath;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.skills.ReceiverSkill;
import edu.x.framework.skillsystem.skills.RedirectSkill;
import edu.x.framework.skillsystem.skills.ReceiverSkill.EReceiverMode;



public class RedirectTestRole extends ARole
{
	
	private DynamicPosition target;
	
	
	
	public RedirectTestRole(final DynamicPosition target, final Boolean receive)
	{
		super(ERole.REDIRECT_TEST);
		this.target = target;
		
		IRoleState redirectState = new RedirectState();
		IRoleState receiveState = new ReceiveState();
		if (receive)
		{
			setInitialState(receiveState);
		} else
		{
			setInitialState(redirectState);
		}
	}
	
	private enum EStateId
	{
		REDIRECT,
		RECEIVE
	}
	
	private class RedirectState implements IRoleState
	{
		
		private RedirectSkill	skill;
		private IVector2			initialReceivePosition;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new RedirectSkill(target);
			setNewSkill(skill);
			initialReceivePosition = getPos();
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			if (OffensiveMath.getPotentialRedirectors(getWFrame(), getWFrame().getTigerBotsAvailable())
					.contains(getBotID()))
			{
				List<IDrawableShape> shapes = getAiFrame().getTacticalField().getDrawableShapes()
						.get(EShapesLayer.REDIRECT_SKILL);
				IVector2 betterPos = RedirectMath.calculateBetterPosition(getWFrame(), getBot(), initialReceivePosition,
						shapes);
				skill.setDesiredDestination(betterPos);
			} else
			{
				skill.setDesiredDestination(initialReceivePosition);
			}
		}
		
		
		@Override
		public Enum<?> getIdentifier()
		{
			return EStateId.REDIRECT;
		}
		
	}
	
	private class ReceiveState implements IRoleState
	{
		
		private ReceiverSkill	skill;
		private IVector2			initialReceivePosition;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new ReceiverSkill(EReceiverMode.STOP_DRIBBLER);
			setNewSkill(skill);
			initialReceivePosition = getPos();
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public void doUpdate()
		{
			if (OffensiveMath.getPotentialRedirectors(getWFrame(), getWFrame().getTigerBotsAvailable())
					.contains(getBotID()))
			{
				List<IDrawableShape> shapes = getAiFrame().getTacticalField().getDrawableShapes()
						.get(EShapesLayer.REDIRECT_SKILL);
				IVector2 betterPos = RedirectMath.calculateBetterPosition(getWFrame(), getBot(), initialReceivePosition,
						shapes);
				skill.setDesiredDestination(betterPos);
			} else
			{
				skill.setDesiredDestination(initialReceivePosition);
			}
		}
		
		
		@Override
		public Enum<?> getIdentifier()
		{
			return EStateId.RECEIVE;
		}
		
	}
	
}
