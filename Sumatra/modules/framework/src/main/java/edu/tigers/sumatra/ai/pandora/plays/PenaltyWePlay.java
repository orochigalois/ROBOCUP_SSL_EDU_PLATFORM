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

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ai.pandora.roles.move.MoveRole;
import edu.tigers.sumatra.ai.pandora.roles.move.MoveRole.EMoveBehavior;
import edu.tigers.sumatra.ai.pandora.roles.offense.EpicPenaltyShooterRole;



public class PenaltyWePlay extends APenaltyPlay
{
	
	public PenaltyWePlay()
	{
		super(EPlay.PENALTY_WE);
	}
	
	
	@Override
	protected ARole onRemoveRole(final MetisAiFrame frame)
	{
		for (ARole role : getRoles())
		{
			if (role.getType() != ERole.EPIC_PENALTY_SHOOTER)
			{
				return role;
			}
		}
		return getLastRole();
	}
	
	
	@Override
	protected ARole onAddRole(final MetisAiFrame frame)
	{
		for (ARole role : getRoles())
		{
			if (role.getType() == ERole.EPIC_PENALTY_SHOOTER)
			{
				return new MoveRole(EMoveBehavior.LOOK_AT_BALL);
			}
		}
		return new EpicPenaltyShooterRole();
	}
	
	
	@Override
	protected void doUpdate(final AthenaAiFrame frame)
	{
		if (!getRoles().isEmpty())
		{
			List<ARole> moveRoles = new ArrayList<ARole>(getRoles().size());
			for (ARole role : getRoles())
			{
				if (role.getType() != ERole.EPIC_PENALTY_SHOOTER)
				{
					moveRoles.add(role);
				}
			}
			updateMoveRoles(frame, moveRoles, 1, 0);
		}
	}
}
