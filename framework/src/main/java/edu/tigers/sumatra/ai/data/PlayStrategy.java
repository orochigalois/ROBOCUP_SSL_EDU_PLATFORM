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
import java.util.Collections;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.BotIDMap;



@Persistent(version = 4)
public class PlayStrategy implements IPlayStrategy
{
	private transient List<APlay>	activePlays;
											
	
	private transient List<APlay>	finishedPlays;
											
	private EAIControlState			aiControlState;
											
											
	@SuppressWarnings("unused")
	private PlayStrategy()
	{
		activePlays = new ArrayList<>();
		finishedPlays = new ArrayList<>();
	}
	
	
	
	public PlayStrategy(final Builder builder)
	{
		activePlays = Collections.unmodifiableList(builder.activePlays);
		finishedPlays = Collections.unmodifiableList(builder.finishedPlays);
		aiControlState = builder.controlState;
	}
	
	
	
	@Override
	public int getNumRoles()
	{
		int sum = 0;
		for (APlay play : activePlays)
		{
			sum += play.getRoles().size();
		}
		return sum;
	}
	
	
	
	@Override
	public BotIDMap<ARole> getActiveRoles()
	{
		BotIDMap<ARole> roles = new BotIDMap<ARole>(6);
		for (APlay play : activePlays)
		{
			for (ARole role : play.getRoles())
			{
				roles.put(role.getBotID(), role);
			}
		}
		return roles;
	}
	
	
	
	@Override
	public List<ARole> getActiveRoles(final ERole roleType)
	{
		List<ARole> roles = new ArrayList<ARole>();
		for (APlay play : activePlays)
		{
			for (ARole role : play.getRoles())
			{
				if (role.getType() == roleType)
				{
					roles.add(role);
				}
			}
		}
		return roles;
	}
	
	
	
	@Override
	public List<ARole> getActiveRoles(final EPlay playType)
	{
		List<ARole> roles = new ArrayList<ARole>();
		for (APlay play : activePlays)
		{
			if (play.getType() != playType)
			{
				continue;
			}
			for (ARole role : play.getRoles())
			{
				roles.add(role);
			}
		}
		return roles;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public List<APlay> getActivePlays()
	{
		return activePlays;
	}
	
	
	
	@Override
	public List<APlay> getFinishedPlays()
	{
		return finishedPlays;
	}
	
	
	
	@Override
	public EAIControlState getAIControlState()
	{
		return aiControlState;
	}
	
	
	
	public static class Builder
	{
		private List<APlay>		activePlays;
		private List<APlay>		finishedPlays;
		private EAIControlState	controlState;
										
										
		
		public Builder()
		{
			activePlays = new ArrayList<APlay>();
			finishedPlays = new ArrayList<APlay>();
			controlState = EAIControlState.TEST_MODE;
		}
		
		
		
		public IPlayStrategy build()
		{
			return new PlayStrategy(this);
		}
		
		
		
		public final List<APlay> getActivePlays()
		{
			return activePlays;
		}
		
		
		
		public final void setActivePlays(final List<APlay> activePlays)
		{
			this.activePlays = activePlays;
		}
		
		
		
		public final List<APlay> getFinishedPlays()
		{
			return finishedPlays;
		}
		
		
		
		public final void setFinishedPlays(final List<APlay> finishedPlays)
		{
			this.finishedPlays = finishedPlays;
		}
		
		
		
		public final EAIControlState getAIControlState()
		{
			return controlState;
		}
		
		
		
		public final void setAIControlState(final EAIControlState controlState)
		{
			this.controlState = controlState;
		}
		
	}
}
