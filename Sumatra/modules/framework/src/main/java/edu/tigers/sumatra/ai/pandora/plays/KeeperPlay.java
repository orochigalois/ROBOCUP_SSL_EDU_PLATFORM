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

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.defense.KeeperRole;
import edu.tigers.sumatra.ai.pandora.roles.defense.PenaltyKeeperRole;



public class KeeperPlay extends APlay
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private ARole	keeper	= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public KeeperPlay()
	{
		super(EPlay.KEEPER);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	protected ARole onRemoveRole(final MetisAiFrame frame)
	{
		ARole role = keeper;
		keeper = null;
		return role;
	}
	
	
	@Override
	protected void onRoleRemoved(final ARole role)
	{
		keeper = null;
	}
	
	
	@Override
	protected ARole onAddRole(final MetisAiFrame frame)
	{
		if (!getRoles().isEmpty())
		{
			throw new IllegalStateException("Keeper Play can not handle more than 1 role!");
		}
		if (frame.getTacticalField().getGameState() == EGameStateTeam.PREPARE_KICKOFF_THEY)
		{
			keeper = new PenaltyKeeperRole();
		} else
		{
			keeper = new KeeperRole();
		}
		return (keeper);
	}
	
	
	@Override
	protected void onGameStateChanged(final EGameStateTeam gameState)
	{
		ARole oldKeeper = keeper;
		switch (gameState)
		{
			case PREPARE_PENALTY_THEY:
				if (oldKeeper != null)
				{
					keeper = new PenaltyKeeperRole();
					switchRoles(oldKeeper, keeper);
				}
				break;
			default:
				if (oldKeeper != null)
				{
					keeper = new KeeperRole();
					switchRoles(oldKeeper, keeper);
				}
				break;
		}
	}
	
	
	@Override
	protected void doUpdate(final AthenaAiFrame frame)
	{
		if ((keeper != null) && keeper.getClass().equals(KeeperRole.class))
		{
			if (frame.getTacticalField().getGameState() == EGameStateTeam.STOPPED)
			{
				((KeeperRole) keeper).setAllowChipkick(false);
			} else
			{
				((KeeperRole) keeper).setAllowChipkick(true);
			}
		}
	}
}
