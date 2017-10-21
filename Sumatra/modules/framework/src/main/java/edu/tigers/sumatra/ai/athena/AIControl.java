/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.athena;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.ai.lachesis.RoleFinderInfo;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ids.BotID;



public final class AIControl
{
	private final List<APlay>						addPlays					= new LinkedList<APlay>();
	private final List<APlay>						removePlays				= new LinkedList<APlay>();
	private final List<ARole>						addRoles					= new LinkedList<ARole>();
	private final List<ARole>						removeRoles				= new LinkedList<ARole>();
	private final Map<BotID, ARole>				assignRoles				= new HashMap<BotID, ARole>();
	
	private final Map<APlay, Integer>			addRoles2Play			= new HashMap<APlay, Integer>();
	private final Map<APlay, Integer>			removeRolesFromPlay	= new HashMap<APlay, Integer>();
	
	private boolean									changed					= false;
	
	private final Map<EPlay, RoleFinderInfo>	roleFinderInfos		= new HashMap<EPlay, RoleFinderInfo>();
	private final Map<EPlay, Boolean>			roleFinderOverrides	= new HashMap<EPlay, Boolean>();
	
	
	
	public AIControl()
	{
	}
	
	
	
	public void addPlay(final APlay play)
	{
		addPlays.add(play);
		changed = true;
	}
	
	
	
	public void removePlay(final APlay play)
	{
		removePlays.add(play);
		changed = true;
	}
	
	
	
	public void addRole(final ARole role, final BotID botId)
	{
		addRoles.add(role);
		if (botId.isBot())
		{
			assignRoles.put(botId, role);
		}
		changed = true;
	}
	
	
	
	public void removeRole(final ARole role)
	{
		removeRoles.add(role);
		changed = true;
	}
	
	
	
	public void addRoles2Play(final APlay play, final int numRoles)
	{
		addRoles2Play.put(play, numRoles);
		changed = true;
	}
	
	
	
	public void removeRolesFromPlay(final APlay play, final int numRoles)
	{
		removeRolesFromPlay.put(play, numRoles);
		changed = true;
	}
	
	
	
	public final List<APlay> getAddPlays()
	{
		return addPlays;
	}
	
	
	
	public void reset()
	{
		addPlays.clear();
		removePlays.clear();
		addRoles.clear();
		removeRoles.clear();
		assignRoles.clear();
		addRoles2Play.clear();
		removeRolesFromPlay.clear();
		changed = false;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final boolean hasChanged()
	{
		return changed;
	}
	
	
	
	public final List<APlay> getRemovePlays()
	{
		return removePlays;
	}
	
	
	
	public final List<ARole> getAddRoles()
	{
		return addRoles;
	}
	
	
	
	public final List<ARole> getRemoveRoles()
	{
		return removeRoles;
	}
	
	
	
	public final Map<APlay, Integer> getAddRoles2Play()
	{
		return addRoles2Play;
	}
	
	
	
	public final Map<APlay, Integer> getRemoveRolesFromPlay()
	{
		return removeRolesFromPlay;
	}
	
	
	
	public final Map<BotID, ARole> getAssignRoles()
	{
		return assignRoles;
	}
	
	
	
	public final Map<EPlay, RoleFinderInfo> getRoleFinderInfos()
	{
		return roleFinderInfos;
	}
	
	
	
	public final Map<EPlay, Boolean> getRoleFinderOverrides()
	{
		return roleFinderOverrides;
	}
}
