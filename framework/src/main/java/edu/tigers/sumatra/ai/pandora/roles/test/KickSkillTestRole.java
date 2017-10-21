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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.math.AiMath;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.drawable.DrawableCircle;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.skills.KickSkill;
import edu.x.framework.skillsystem.skills.KickSkill.EMoveMode;
import edu.x.framework.skillsystem.skills.test.PositionSkill;



public class KickSkillTestRole extends ARole
{
	private DynamicPosition passTarget;
	
	
	
	public KickSkillTestRole(final DynamicPosition passTarget)
	{
		super(ERole.KICK_SKILL_TEST);
		setPassTarget(passTarget);
		setInitialState(new ShootState());
	}
	
	
	private enum EStateId
	{
		PREPARE,
		SHOOT
	}
	
	
	private class ShootState implements IRoleState
	{
		private KickSkill skill;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new KickSkill(passTarget);
			skill.setMoveMode(EMoveMode.NORMAL);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			List<BotID> igBots = new ArrayList<>(1);
			igBots.add(getBotID());
			double visScore = AiMath.p2pVisibilityVoting(getWFrame(), getWFrame().getBall().getPos(), passTarget, 200,
					igBots);
			
			if (visScore < 0.5)
			{
				skill.setRoleReady4Kick(true);
			} else
			{
				skill.setRoleReady4Kick(false);
			}
			skill.setRoleReady4Kick(false);
			
			IVector2 dest = getWFrame().getBall().getPos().addNew(new Vector2(1000, -1000));
			getAiFrame().getTacticalField().getDrawableShapes().get(EShapesLayer.KICK_SKILL)
					.add(new DrawableCircle(dest, 100, Color.red));
			// skill.setDestForAvoidingOpponent(dest);
			// skill.setProtectPos(getWFrame().getBot(BotID.createBotId(2,
			// getAiFrame().getTeamColor().opposite())).getPos());
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.SHOOT;
		}
	}
	
	@SuppressWarnings("unused")
	private class PrepareState implements IRoleState
	{
		private PositionSkill skill;
		
		
		@Override
		public void doEntryActions()
		{
			skill = new PositionSkill(AVector2.ZERO_VECTOR, 0);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			skill.getMoveCon().getMoveConstraints().setVelMax(0.5);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.PREPARE;
		}
	}
	
	
	
	public void setPassTarget(final DynamicPosition passTarget)
	{
		this.passTarget = passTarget;
	}
}
