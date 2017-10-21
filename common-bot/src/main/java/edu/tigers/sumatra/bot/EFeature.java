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

import java.util.LinkedHashMap;
import java.util.Map;



public enum EFeature
{
	
	MOVE("Move", 0x0001),
	
	DRIBBLER("Dribbler", 0x0002),
	
	STRAIGHT_KICKER("Straight Kicker", 0x0004),
	
	CHIP_KICKER("Chip Kicker", 0x0008),
	
	BARRIER("Barrier", 0x0010),
	
	V2016("v2016", 0x0020),
	
	EXT_BOARD("Ext. Board", 0x0040);
	
	private final String	name;
	private final int		id;
	
	
	private EFeature(final String name, final int id)
	{
		this.name = name;
		this.id = id;
	}
	
	
	private EFeature(final String name, final int id, final String desc)
	{
		this.name = name;
		this.id = id;
	}
	
	
	
	public static Map<EFeature, EFeatureState> createFeatureList()
	{
		Map<EFeature, EFeatureState> map = new LinkedHashMap<EFeature, EFeatureState>();
		for (EFeature f : EFeature.values())
		{
			map.put(f, EFeatureState.UNKNOWN);
		}
		return map;
	}
	
	
	
	public final String getName()
	{
		return name;
	}
	
	
	
	public int getId()
	{
		return id;
	}
	
	
	
	public static EFeature getFeatureConstant(final int id)
	{
		for (EFeature s : values())
		{
			if (s.getId() == id)
			{
				return s;
			}
		}
		
		return null;
	}
	
}
