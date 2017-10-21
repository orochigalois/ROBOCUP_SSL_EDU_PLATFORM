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

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import edu.tigers.sumatra.ai.pandora.plays.EPlay;



public class PlayPrioComparatorInfo implements Comparator<Map.Entry<EPlay, RoleFinderInfo>>, Serializable
{
	
	private static final long	serialVersionUID	= -5929457403712458061L;
	
	
	
	public PlayPrioComparatorInfo()
	{
	}
	
	
	@Override
	public int compare(final Map.Entry<EPlay, RoleFinderInfo> a, final Map.Entry<EPlay, RoleFinderInfo> b)
	{
		return Integer.compare(a.getKey().getPrio(), b.getKey().getPrio());
	}
}
