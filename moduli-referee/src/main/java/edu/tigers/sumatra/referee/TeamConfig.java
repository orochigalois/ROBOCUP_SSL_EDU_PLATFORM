/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.referee;

import java.util.HashSet;
import java.util.Set;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public final class TeamConfig
{
	@Configurable
	private static int			keeperIdBlue	= 0;
	@Configurable
	private static int			keeperIdYellow	= 0;
	@Configurable
	private static ETeamColor	leftTeam			= ETeamColor.BLUE;
	
	
	static
	{
		ConfigRegistration.registerClass("team", TeamConfig.class);
	}
	
	
	private TeamConfig()
	{
	}
	
	
	
	public static final int getKeeperIdBlue()
	{
		return keeperIdBlue;
	}
	
	
	
	public static final int getKeeperIdYellow()
	{
		return keeperIdYellow;
	}
	
	
	
	public static final int getKeeperId(final ETeamColor tc)
	{
		if (tc == ETeamColor.BLUE)
		{
			return keeperIdBlue;
		} else if (tc == ETeamColor.YELLOW)
		{
			return keeperIdYellow;
		}
		throw new IllegalArgumentException();
	}
	
	
	
	public static final BotID getKeeperBotIDBlue()
	{
		return BotID.createBotId(keeperIdBlue, ETeamColor.BLUE);
	}
	
	
	
	public static final BotID getKeeperBotIDYellow()
	{
		return BotID.createBotId(keeperIdYellow, ETeamColor.YELLOW);
	}
	
	
	
	public static final BotID getKeeperBotID(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return getKeeperBotIDBlue();
			case YELLOW:
				return getKeeperBotIDYellow();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	
	
	public static Set<BotID> getKeeperIDs()
	{
		Set<BotID> keeperSet = new HashSet<>();
		keeperSet.add(getKeeperBotIDBlue());
		keeperSet.add(getKeeperBotIDYellow());
		return keeperSet;
	}
	
	
	
	public static final ETeamColor getLeftTeam()
	{
		return leftTeam;
	}
	
	
	
	public static final void setKeeperIdBlue(final int keeperIdBlue)
	{
		TeamConfig.keeperIdBlue = keeperIdBlue;
	}
	
	
	
	public static final void setKeeperIdYellow(final int keeperIdYellow)
	{
		TeamConfig.keeperIdYellow = keeperIdYellow;
	}
}
