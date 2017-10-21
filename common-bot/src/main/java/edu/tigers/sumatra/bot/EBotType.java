/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.bot;

import java.util.Hashtable;
import java.util.Map;



public enum EBotType
{
	
	UNKNOWN(0, "Unknown", "Unknown Bot"),
	
	TIGER(2, "TigerBot", "Tiger Bot"),
	
	GRSIM(1, "grSim", "grSim Bot"),
	
	TIGER_V3(4, "TigerBotV3", "Tiger Bot v2015"),
	
	TIGER_2016(6, "TigerBot2016", "Tiger Bot v2016"),
	
	SUMATRA(5, "sumatra", "SumatraBot"),
	
	SHARED_RADIO(7, "sharedRadio", "SharedRadio Bot");
	
	private final String	cfgName;
	private final String	displayName;
	private final int		versionId;
								
								
	private EBotType(final int versionId, final String cfgName, final String displayName)
	{
		this.versionId = versionId;
		this.cfgName = cfgName;
		this.displayName = displayName;
	}
	
	
	
	public static Map<String, EBotType> getBotTypeMap()
	{
		final Map<String, EBotType> types = new Hashtable<String, EBotType>();
		
		for (EBotType t : EBotType.values())
		{
			types.put(t.displayName, t);
		}
		
		return types;
	}
	
	
	
	public static EBotType getTypeFromCfgName(final String cfgName)
	{
		for (EBotType t : EBotType.values())
		{
			if (t.cfgName.equals(cfgName))
			{
				return t;
			}
		}
		
		return UNKNOWN;
	}
	
	
	
	public String getCfgName()
	{
		return cfgName;
	}
	
	
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	
	
	public boolean atLeast(final EBotType type)
	{
		return type.versionId <= versionId;
	}
}
