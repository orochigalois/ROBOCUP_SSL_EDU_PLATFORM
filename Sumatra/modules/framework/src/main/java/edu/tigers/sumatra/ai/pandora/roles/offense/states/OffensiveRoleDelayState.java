/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.offense.states;

import java.awt.Color;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.OffensiveStrategy.EOffensiveStrategy;
import edu.tigers.sumatra.ai.data.math.OffensiveMath;
import edu.tigers.sumatra.ai.metis.offense.OffensiveConstants;
import edu.tigers.sumatra.ai.pandora.roles.offense.OffensiveRole;
import edu.tigers.sumatra.drawable.DrawableText;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.AMoveToSkill;
import edu.x.framework.skillsystem.skills.DelayedKickSkill;



public class OffensiveRoleDelayState extends AOffensiveRoleState implements IRoleState
{
	
	// -------------------------------------------------------------------------- //
	// --- variables and constants ---------------------------------------------- //
	// -------------------------------------------------------------------------- //
	private long initialTime = 0;
	// ----------------------------------------------------------------------- //
	// -------------------- functions ---------------------------------------- //
	// ----------------------------------------------------------------------- //
	
	
	
	public OffensiveRoleDelayState(final OffensiveRole role)
	{
		super(role);
	}
	
	
	private DelayedKickSkill	skill	= null;
	
	private AMoveToSkill			move	= null;
	
	
	@Override
	public void doExitActions()
	{
		
	}
	
	
	@Override
	public void doEntryActions()
	{
		skill = new DelayedKickSkill(new DynamicPosition(getAiFrame().getTacticalField().getBestDirectShootTarget()));
		move = AMoveToSkill.createMoveToSkill();
		setNewSkill(move);
		initialTime = getWFrame().getTimestamp();
	}
	
	
	@Override
	public void doUpdate()
	{
		IVector2 movePos = getWFrame().getBall().getPos().addNew(
				Geometry.getCenter().subtractNew(getWFrame().getBall().getPos()).scaleToNew(300));
		if ((GeoMath.distancePP(getPos(), movePos) < 50)
				&& (getCurrentSkill().toString() != ESkill.DELAYED_KICK.toString()))
		{
			skill = new DelayedKickSkill(new DynamicPosition(getAiFrame().getTacticalField().getBestDirectShootTarget()));
			setNewSkill(skill);
		} else
		{
			move.getMoveCon().updateLookAtTarget(getWFrame().getBall().getPos());
			move.getMoveCon().updateDestination(movePos);
		}
		double delayTime = OffensiveConstants.getDelayWaitTime();
		if (OffensiveMath.isKeeperInsane(getAiFrame(), getAiFrame().getTacticalField()))
		{
			delayTime *= 2.5;
		}
		if ((getWFrame().getTimestamp() - initialTime) > delayTime)
		{
			getAiFrame().getAICom().setResponded(true);
		}
		DrawableText dt = new DrawableText(getWFrame().getBall().getPos().addNew(new Vector2(-500, 0)),
				"delay: " + ((getWFrame().getTimestamp() - initialTime) / (1000 * 1000)), Color.orange);
		getAiFrame().getTacticalField().getDrawableShapes().get(EShapesLayer.OFFENSIVE_FINDER).add(dt);
	}
	
	
	@Override
	public Enum<? extends Enum<?>> getIdentifier()
	{
		return EOffensiveStrategy.DELAY;
	}
	
	
}
