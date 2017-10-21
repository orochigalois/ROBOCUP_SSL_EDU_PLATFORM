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

import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.statemachine.IState;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.MoveToTrajSkill;



public class PosTrajTestSkill extends MoveToTrajSkill
{
	private final IVector3 p1, p2;
	
	
	
	public PosTrajTestSkill(final IVector3 p1, final IVector3 p2)
	{
		super(ESkill.POS_TRAJ_TEST);
		this.p1 = p1;
		this.p2 = p2;
		IState one = new OneState();
		IState two = new TwoState();
		setInitialState(one);
		addTransition(one, EEvent.NEXT, two);
		addTransition(two, EEvent.NEXT, one);
	}
	
	
	private enum EStateId
	{
		ONE,
		TWO
	}
	
	private enum EEvent
	{
		NEXT
	}
	
	private abstract class BaseState implements IState
	{
		long tWaitStart = 0;
		
		
		@Override
		public void doEntryActions()
		{
			tWaitStart = 0;
		}
		
		
		@Override
		public void doUpdate()
		{
			if ((tWaitStart == 0) && (GeoMath.distancePP(getPos(), getMoveCon().getDestination()) < 10)
					&& (Math.abs(AngleMath.difference(getAngle(), getMoveCon().getTargetAngle())) < 0.01))
			{
				tWaitStart = getWorldFrame().getTimestamp();
			}
			if ((tWaitStart != 0) && (GeoMath.distancePP(getPos(), getMoveCon().getDestination()) > 50)
					&& (Math.abs(AngleMath.difference(getAngle(), getMoveCon().getTargetAngle())) > 0.1))
			{
				tWaitStart = 0;
			}
			
			if ((tWaitStart != 0) && (((getWorldFrame().getTimestamp() - tWaitStart) / 1e9) > 0.5))
			{
				triggerEvent(EEvent.NEXT);
			}
		}
		
		
		@Override
		public void doExitActions()
		{
		}
		
	}
	
	
	private class OneState extends BaseState
	{
		@Override
		public void doEntryActions()
		{
			super.doEntryActions();
			getMoveCon().updateDestination(p1.getXYVector());
			getMoveCon().updateTargetAngle(p1.z());
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.ONE;
		}
	}
	
	private class TwoState extends BaseState
	{
		@Override
		public void doEntryActions()
		{
			super.doEntryActions();
			getMoveCon().updateDestination(p2.getXYVector());
			getMoveCon().updateTargetAngle(p2.z());
		}
		
		
		@Override
		public Enum<? extends Enum<?>> getIdentifier()
		{
			return EStateId.TWO;
		}
	}
}
