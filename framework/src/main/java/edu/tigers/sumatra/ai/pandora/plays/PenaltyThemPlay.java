/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.move.MoveRole;
import edu.tigers.sumatra.ai.pandora.roles.move.MoveRole.EMoveBehavior;
import edu.tigers.sumatra.wp.data.Geometry;



public class PenaltyThemPlay extends APenaltyPlay
{
	
	
	public PenaltyThemPlay()
	{
		super(EPlay.PENALTY_THEM);
	}
	
	
	@Override
	protected ARole onRemoveRole(final MetisAiFrame frame)
	{
		return getLastRole();
	}
	
	
	@Override
	protected ARole onAddRole(final MetisAiFrame frame)
	{
		return new MoveRole(EMoveBehavior.LOOK_AT_BALL);
	}
	
	
	@Override
	protected void doUpdate(final AthenaAiFrame frame)
	{
		updateMoveRoles(frame, getRoles(), -1, (int) Geometry.getBotRadius() * 2);
	}
}
