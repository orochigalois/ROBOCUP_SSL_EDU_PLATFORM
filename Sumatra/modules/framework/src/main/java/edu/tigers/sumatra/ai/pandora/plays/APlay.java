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
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;



public abstract class APlay
{
	private static final Logger	log	= Logger.getLogger(APlay.class.getName());
													
	private EPlayState				state;
	private final List<ARole>		roles;
	private final EPlay				type;
											
	private enum EPlayState
	{
		
		RUNNING,
		
		FINISHED;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getInstance/constructor(s) -------------------------------------------
	// --------------------------------------------------------------------------
	
	public APlay(final EPlay type)
	{
		roles = new ArrayList<ARole>();
		state = EPlayState.RUNNING;
		this.type = type;
	}
	
	
	// --------------------------------------------------------------
	// --- roles ----------------------------------------------------
	// --------------------------------------------------------------
	
	
	
	public final void switchRoles(final ARole oldRole, final ARole newRole, final BotID newBotId)
	{
		if (!newBotId.isBot())
		{
			log.error("Could not switch roles. New botId is not valid: " + newBotId);
			return;
		}
		
		if (newRole.isCompleted())
		{
			log.error("Role is already completed. Can not switch to new role: " + newRole.getType());
			return;
		}
		
		boolean removed = roles.remove(oldRole);
		if (!removed)
		{
			log.error("Could not switch roles. Role to switch is not in list. + " + oldRole);
			return;
		}
		roles.add(newRole);
		
		newRole.assignBotID(newBotId, oldRole.getAiFrame());
		newRole.update(oldRole.getAiFrame());
	}
	
	
	
	protected final void switchRoles(final ARole oldRole, final ARole newRole)
	{
		switchRoles(oldRole, newRole, oldRole.getBotID());
	}
	
	
	
	private void addRole(final ARole role)
	{
		roles.add(role);
	}
	
	
	
	public final void removeRole(final ARole role)
	{
		roles.remove(role);
		role.setCompleted();
		onRoleRemoved(role);
	}
	
	
	
	protected final ARole getLastRole()
	{
		ARole role = getRoles().get(getRoles().size() - 1);
		return role;
	}
	
	
	
	public final List<ARole> addRoles(final int count, final MetisAiFrame frame)
	{
		List<ARole> roles = new ArrayList<ARole>();
		for (int i = 0; i < count; i++)
		{
			ARole role = onAddRole(frame);
			roles.add(role);
			addRole(role);
		}
		return roles;
	}
	
	
	
	public final List<ARole> removeRoles(final int count, final MetisAiFrame frame)
	{
		List<ARole> roles = new ArrayList<ARole>();
		for (int i = 0; i < count; i++)
		{
			ARole role = onRemoveRole(frame);
			roles.add(role);
			removeRole(role);
		}
		return roles;
	}
	
	
	
	protected abstract ARole onRemoveRole(MetisAiFrame frame);
	
	
	
	protected abstract ARole onAddRole(MetisAiFrame frame);
	
	
	
	protected void onRoleRemoved(final ARole role)
	{
	}
	
	
	
	public final void changeToFinished()
	{
		for (ARole role : getRoles())
		{
			role.setCompleted();
		}
		state = EPlayState.FINISHED;
	}
	
	
	
	public final boolean isFinished()
	{
		return state == EPlayState.FINISHED;
	}
	
	
	
	public void updateBeforeRoles(final AthenaAiFrame frame)
	{
	}
	
	
	
	public final void update(final AthenaAiFrame frame)
	{
		if (frame.getPrevFrame().getTacticalField().getGameState() != frame.getTacticalField().getGameState())
		{
			onGameStateChanged(frame.getTacticalField().getGameState());
			for (ARole role : getRoles())
			{
				role.onGameStateChanged(frame.getTacticalField().getGameState());
			}
		}
		doUpdate(frame);
	}
	
	
	
	protected void doUpdate(final AthenaAiFrame frame)
	{
	}
	
	
	
	protected abstract void onGameStateChanged(EGameStateTeam gameState);
	
	
	
	public boolean overrideRoleAssignment()
	{
		return false;
	}
	
	
	// --------------------------------------------------------------
	// --- setter/getter --------------------------------------------
	// --------------------------------------------------------------
	
	
	@Override
	public String toString()
	{
		return type.toString();
	}
	
	
	
	public final List<ARole> getRoles()
	{
		return Collections.unmodifiableList(roles);
	}
	
	
	
	public final void setReorderedRoles(final List<ARole> orderedRoles)
	{
		if (roles.size() != orderedRoles.size())
		{
			throw new IllegalArgumentException("Provided orderedRoles list does not have correct size: "
					+ orderedRoles.size());
		}
		for (ARole role : orderedRoles)
		{
			if (!roles.contains(role))
			{
				throw new IllegalArgumentException(
						"Provided orderedRoles list contains an unknown role! " + role.getType());
			}
		}
		roles.clear();
		roles.addAll(orderedRoles);
	}
	
	
	
	public final EPlayState getPlayState()
	{
		return state;
	}
	
	
	
	public final EPlay getType()
	{
		return type;
	}
	
	
	
	protected void reorderRolesToDestinations(final List<IVector2> destinations)
	{
		List<ARole> roles = new ArrayList<ARole>(getRoles());
		List<ARole> rolesSorted = new ArrayList<ARole>(getRoles().size());
		for (IVector2 dest : destinations)
		{
			double minDist = Double.MAX_VALUE;
			ARole theRole = null;
			for (ARole role : roles)
			{
				double dist = GeoMath.distancePP(dest, role.getPos());
				if (dist < minDist)
				{
					minDist = dist;
					theRole = role;
				}
			}
			roles.remove(theRole);
			rolesSorted.add(theRole);
		}
		setReorderedRoles(rolesSorted);
	}
}
