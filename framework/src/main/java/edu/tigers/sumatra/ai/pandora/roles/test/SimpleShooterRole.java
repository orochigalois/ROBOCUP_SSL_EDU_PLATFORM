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

import edu.tigers.sumatra.ai.data.math.OffensiveMath;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.skills.KickSkill;
import edu.x.framework.skillsystem.skills.KickSkill.EKickMode;
import edu.x.framework.skillsystem.skills.KickSkill.EMoveMode;



public class SimpleShooterRole extends ARole
{
	private DynamicPosition passTarget;
	
	
	
	public SimpleShooterRole(final DynamicPosition passTarget)
	{
		super(ERole.SIMPLE_SHOOTER);
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
			skill.setMoveMode(EMoveMode.CHILL);
			skill.setKickMode(EKickMode.FIXED_SPEED);
			setNewSkill(skill);
		}
		
		
		@Override
		public void doUpdate()
		{
			skill.setReceiver(passTarget);
			getAiFrame().getAICom().setOffensiveRolePassTarget(passTarget);
			getAiFrame().getAICom().setOffensiveRolePassTargetID((BotID) passTarget.getTrackedId());
			
			double passVel = OffensiveMath.calcPassSpeedForReceivers(getPos(), passTarget,
					getAiFrame().getTacticalField().getBestDirectShotTargetsForTigerBots().get(passTarget.getTrackedId()));
			skill.setKickSpeed(passVel);
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
	
	
	
	public void setPassTarget(final DynamicPosition passTarget)
	{
		this.passTarget = passTarget;
	}
}
