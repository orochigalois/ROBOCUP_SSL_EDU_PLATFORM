/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.others;

import org.apache.log4j.Logger;

import com.github.g3force.instanceables.InstanceableClass.NotCreateableException;

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ai.pandora.roles.RoleFactory;



public class GuiTestPlay extends APlay
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger	log					= Logger.getLogger(GuiTestPlay.class.getName());
	
	private ARole						roleToBeRemoved	= null;
	private ARole						roleToBeAdded		= null;
	
	private ERole						lastAddedRoleType	= ERole.MOVE;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public GuiTestPlay()
	{
		super(EPlay.GUI_TEST);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	protected ARole onRemoveRole(final MetisAiFrame frame)
	{
		if (roleToBeRemoved != null)
		{
			ARole role = roleToBeRemoved;
			roleToBeRemoved = null;
			return role;
		}
		return getLastRole();
	}
	
	
	@Override
	protected ARole onAddRole(final MetisAiFrame frame)
	{
		if ((roleToBeAdded != null))
		{
			ARole role = roleToBeAdded;
			lastAddedRoleType = role.getType();
			return role;
		}
		log.warn("Could not add requested role. Creating new default instance. Your custom parameters will not be used! You may need to set a fixed botID.");
		try
		{
			return RoleFactory.createDefaultRole(lastAddedRoleType);
		} catch (NotCreateableException err)
		{
			log.error("Could not create role " + lastAddedRoleType, err);
		}
		return null;
	}
	
	
	@Override
	protected void onGameStateChanged(final EGameStateTeam gameState)
	{
	}
	
	
	
	public void setRoleToBeRemoved(final ARole role)
	{
		roleToBeRemoved = role;
	}
	
	
	
	public void setRoleToBeAdded(final ARole role)
	{
		roleToBeAdded = role;
	}
	
	
	@Override
	public boolean overrideRoleAssignment()
	{
		return true;
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
