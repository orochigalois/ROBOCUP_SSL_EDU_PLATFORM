/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.lachesis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;



@Persistent
public class RoleFinderInfo
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger	log						= Logger.getLogger(RoleFinderInfo.class.getName());
	private final int					minRoles;
	private final int					maxRoles;
	private final int					desiredRoles;
	private final List<BotID>		desiredBots				= new ArrayList<BotID>(3);
	private int							forceNumDesiredBots	= 0;
	
	
	@SuppressWarnings("unused")
	private RoleFinderInfo()
	{
		minRoles = 0;
		maxRoles = 0;
		desiredRoles = 0;
	}
	
	
	
	public RoleFinderInfo(final int minRoles, final int maxRoles, final int desiredRoles)
	{
		super();
		this.minRoles = minRoles;
		this.maxRoles = maxRoles;
		this.desiredRoles = desiredRoles;
	}
	
	
	
	public RoleFinderInfo(final RoleFinderInfo value)
	{
		this(value.minRoles, value.maxRoles, value.desiredRoles);
		desiredBots.addAll(value.desiredBots);
	}
	
	
	
	public int getMinRoles()
	{
		return minRoles;
	}
	
	
	
	public int getMaxRoles()
	{
		return maxRoles;
	}
	
	
	
	public int getDesiredRoles()
	{
		return desiredRoles;
	}
	
	
	
	public List<BotID> getDesiredBots()
	{
		return desiredBots;
	}
	
	
	
	public int getForceNumDesiredBots()
	{
		return forceNumDesiredBots;
	}
	
	
	
	public void setForceNumDesiredBots(final int forceNumDesiredBots)
	{
		if (forceNumDesiredBots > desiredBots.size())
		{
			log.warn("Tried to set forceNumDesiredBots=" + forceNumDesiredBots + ", but only " + desiredBots.size()
					+ " desired bots available!");
			this.forceNumDesiredBots = desiredBots.size();
		} else
		{
			this.forceNumDesiredBots = forceNumDesiredBots;
		}
	}
	
	
	@Override
	public String toString()
	{
		return "RoleFinderInfo [minRoles=" + minRoles + ", maxRoles=" + maxRoles + ", desiredRoles=" + desiredRoles
				+ ", desiredBots=" + desiredBots + ", forceNumDesiredBots=" + forceNumDesiredBots + "]";
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((desiredBots == null) ? 0 : desiredBots.hashCode());
		result = (prime * result) + desiredRoles;
		result = (prime * result) + forceNumDesiredBots;
		result = (prime * result) + maxRoles;
		result = (prime * result) + minRoles;
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		RoleFinderInfo other = (RoleFinderInfo) obj;
		if (desiredBots == null)
		{
			if (other.desiredBots != null)
			{
				return false;
			}
		} else if (!desiredBots.equals(other.desiredBots))
		{
			return false;
		}
		if (desiredRoles != other.desiredRoles)
		{
			return false;
		}
		if (forceNumDesiredBots != other.forceNumDesiredBots)
		{
			return false;
		}
		if (maxRoles != other.maxRoles)
		{
			return false;
		}
		if (minRoles != other.minRoles)
		{
			return false;
		}
		return true;
	}
}
