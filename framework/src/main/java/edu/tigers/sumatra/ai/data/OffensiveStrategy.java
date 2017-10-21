/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;



@Persistent(version = 2)
public class OffensiveStrategy
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public enum EOffensiveStrategy
	{
		
		GET,
		
		KICK,
		
		STOP,
		
		INTERCEPT,
		
		REDIRECT_CATCH_SPECIAL_MOVE,
		
		DELAY,
		
		SUPPORTIVE_ATTACKER,
		
	}
	
	private int										minNumberOfBots							= 0;
	private int										maxNumberOfBots							= 1;
	private List<BotID>							desiredBots									= new ArrayList<BotID>();
	private Map<BotID, EOffensiveStrategy>	currentOffensivePlayConfiguration	= new HashMap<BotID, EOffensiveStrategy>();
	private List<EOffensiveStrategy>			unassignedStrategies						= new ArrayList<EOffensiveStrategy>();
	private List<SpecialMoveCommand>			specialMoveCommands						= new ArrayList<SpecialMoveCommand>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public OffensiveStrategy()
	{
		
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public int getMinNumberOfBots()
	{
		return minNumberOfBots;
	}
	
	
	
	public void setMinNumberOfBots(final int minNumberOfBots)
	{
		this.minNumberOfBots = minNumberOfBots;
	}
	
	
	
	public int getMaxNumberOfBots()
	{
		return maxNumberOfBots;
	}
	
	
	
	public void setMaxNumberOfBots(final int maxNumberOfBots)
	{
		this.maxNumberOfBots = maxNumberOfBots;
	}
	
	
	
	public List<BotID> getDesiredBots()
	{
		return desiredBots;
	}
	
	
	
	public void setDesiredBots(final List<BotID> desiredBots)
	{
		this.desiredBots = desiredBots;
	}
	
	
	
	public Map<BotID, EOffensiveStrategy> getCurrentOffensivePlayConfiguration()
	{
		return currentOffensivePlayConfiguration;
	}
	
	
	
	public void setCurrentOffensivePlayConfiguration(
			final Map<BotID, EOffensiveStrategy> currentOffensivePlayConfiguration)
	{
		this.currentOffensivePlayConfiguration = currentOffensivePlayConfiguration;
	}
	
	
	
	public List<SpecialMoveCommand> getSpecialMoveCommands()
	{
		return specialMoveCommands;
	}
	
	
	
	public void setSpecialMoveCommands(final List<SpecialMoveCommand> specialMoveCommands)
	{
		this.specialMoveCommands = specialMoveCommands;
	}
	
	
	
	public List<EOffensiveStrategy> getUnassignedStrategies()
	{
		return unassignedStrategies;
	}
	
	
	
	public void setUnassignedStrategies(final List<EOffensiveStrategy> unassignedStrategies)
	{
		this.unassignedStrategies = unassignedStrategies;
	}
	
}