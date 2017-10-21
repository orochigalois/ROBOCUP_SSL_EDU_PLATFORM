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

import java.util.Random;

import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.x.framework.skillsystem.MovementCon;
import edu.x.framework.skillsystem.skills.AMoveToSkill;



public class MoveStressTestRole extends ARole
{
	
	
	public MoveStressTestRole()
	{
		super(ERole.MOVE_STRESS_TEST);
		setInitialState(new SmallDestChanges());
	}
	
	private enum EState
	{
		SMALL_DEST_CHANGES
	}
	
	
	private class SmallDestChanges implements IRoleState
	{
		private MovementCon	moveCon			= null;
		private IVector2		initBotPos		= null;
		private double			initBotAngle	= 0;
		private double			b					= 0;
		private final Random	rnd				= new Random();
		
		
		@Override
		public void doEntryActions()
		{
			AMoveToSkill skill = AMoveToSkill.createMoveToSkill();
			moveCon = skill.getMoveCon();
			moveCon.setPenaltyAreaAllowedOur(true);
			setNewSkill(skill);
			initBotPos = getPos();
			initBotAngle = getBot().getAngle();
		}
		
		
		@Override
		public void doUpdate()
		{
			IVector2 baseDest = initBotPos.addNew(new Vector2(1000, 0));
			
			b += (1.0 / 50.0) * AngleMath.PI_TWO;
			
			double a = Math.cos(b);
			
			IVector2 dest = baseDest.addNew(new Vector2((a * 10), (a * 10)));
			moveCon.updateDestination(dest);
			
			double baseAngle = AngleMath.normalizeAngle(initBotAngle + AngleMath.PI);
			double angle = baseAngle + (rnd.nextGaussian() * 0.15);
			moveCon.updateTargetAngle(angle);
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EState.SMALL_DEST_CHANGES;
		}
	}
}
