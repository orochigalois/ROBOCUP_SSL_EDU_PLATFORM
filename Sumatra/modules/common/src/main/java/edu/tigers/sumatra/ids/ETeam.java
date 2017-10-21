/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;


public enum ETeam
{
	
	TIGERS,
	
	OPPONENTS,
	
	UNKNOWN,
	
	EQUAL;
	
	
	
	public static void assertOneTeam(final ETeam team)
	{
		if ((team == TIGERS) || (team == OPPONENTS))
		{
			// okay!
			return;
		}
		final String teamStr = team == null ? "<null>" : team.name();
		throw new IllegalArgumentException(teamStr + " is not valid team identifier!!!");
	}
}
