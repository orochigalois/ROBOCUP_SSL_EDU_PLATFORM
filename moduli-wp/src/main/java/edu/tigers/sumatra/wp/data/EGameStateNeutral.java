/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import edu.tigers.sumatra.ids.ETeamColor;



public enum EGameStateNeutral
{
	
	UNKNOWN,
	
	
	HALTED,
	
	STOPPED,
	
	RUNNING,
	
	
	TIMEOUT_YELLOW,
	
	TIMEOUT_BLUE,
	
	
	PREPARE_KICKOFF_YELLOW,
	
	PREPARE_KICKOFF_BLUE,
	
	
	KICKOFF_YELLOW,
	
	KICKOFF_BLUE,
	
	
	PREPARE_PENALTY_YELLOW,
	
	PREPARE_PENALTY_BLUE,
	
	
	PENALTY_YELLOW,
	
	PENALTY_BLUE,
	
	// 
	// THROW_IN_YELLOW,
	// 
	// THROW_IN_BLUE,
	//
	// 
	// CORNER_KICK_YELLOW,
	// 
	// CORNER_KICK_BLUE,
	//
	// 
	// GOAL_KICK_YELLOW,
	// 
	// GOAL_KICK_BLUE,
	
	
	INDIRECT_KICK_YELLOW,
	
	INDIRECT_KICK_BLUE,
	
	
	DIRECT_KICK_YELLOW,
	
	DIRECT_KICK_BLUE,
	
	
	BALL_PLACEMENT_YELLOW,
	
	
	BALL_PLACEMENT_BLUE,
	
	
	BREAK,
	
	POST_GAME, ;
	
	
	public static EGameStateNeutral getKickoff(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return PREPARE_KICKOFF_BLUE;
			case YELLOW:
				return PREPARE_KICKOFF_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
	
	
	
	public static EGameStateNeutral getPreparePenalty(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return PREPARE_PENALTY_BLUE;
			case YELLOW:
				return PREPARE_PENALTY_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
	
	
	
	public static EGameStateNeutral getPenalty(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return PENALTY_BLUE;
			case YELLOW:
				return PENALTY_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
	
	
	
	public static EGameStateNeutral getDirectKick(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return DIRECT_KICK_BLUE;
			case YELLOW:
				return DIRECT_KICK_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
	
	
	
	public static EGameStateNeutral getTimeout(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return TIMEOUT_BLUE;
			case YELLOW:
				return TIMEOUT_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid color: " + color);
		}
	}
	
	
	
	public static EGameStateNeutral getBallPlacement(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return BALL_PLACEMENT_BLUE;
			case YELLOW:
				return BALL_PLACEMENT_YELLOW;
			default:
				throw new IllegalArgumentException("Invalid team color for ball placement: " + color);
		}
	}
	
	
	
	public static boolean isBallPlacement(final EGameStateNeutral state)
	{
		return (state == BALL_PLACEMENT_BLUE) || (state == BALL_PLACEMENT_YELLOW);
	}
	
	
	
	public boolean isBallPlacement()
	{
		return isBallPlacement(this);
	}
	
	
	
	public static boolean isDirectKick(final EGameStateNeutral state)
	{
		return (state == DIRECT_KICK_BLUE) || (state == DIRECT_KICK_YELLOW);
	}
	
	
	
	public boolean isDirectKick()
	{
		return isDirectKick(this);
	}
	
	
	
	public static boolean isIndirectKick(final EGameStateNeutral state)
	{
		return (state == INDIRECT_KICK_BLUE) || (state == INDIRECT_KICK_YELLOW);
	}
	
	
	
	public boolean isIndirectKick()
	{
		return isIndirectKick(this);
	}
	
	
	
	public static boolean isKickOff(final EGameStateNeutral state)
	{
		return (state == KICKOFF_BLUE) || (state == KICKOFF_YELLOW);
	}
	
	
	
	public boolean isKickOff()
	{
		return isKickOff(this);
	}
	
	
	
	public static boolean isPenalty(final EGameStateNeutral state)
	{
		return (state == PENALTY_BLUE) || (state == PENALTY_YELLOW);
	}
	
	
	
	public boolean isPenalty()
	{
		return isPenalty(this);
	}
	
	
	
	public static boolean isTimeout(final EGameStateNeutral state)
	{
		return (state == TIMEOUT_BLUE) || (state == TIMEOUT_YELLOW);
	}
	
	
	
	public boolean isTimeout()
	{
		return isTimeout(this);
	}
	
	
	
	public static ETeamColor getTeamColorOfState(final EGameStateNeutral state)
	{
		switch (state)
		{
			case BALL_PLACEMENT_BLUE:
			case DIRECT_KICK_BLUE:
			case INDIRECT_KICK_BLUE:
			case KICKOFF_BLUE:
			case PENALTY_BLUE:
			case PREPARE_KICKOFF_BLUE:
			case PREPARE_PENALTY_BLUE:
			case TIMEOUT_BLUE:
				return ETeamColor.BLUE;
			case BALL_PLACEMENT_YELLOW:
			case DIRECT_KICK_YELLOW:
			case INDIRECT_KICK_YELLOW:
			case KICKOFF_YELLOW:
			case PENALTY_YELLOW:
			case PREPARE_KICKOFF_YELLOW:
			case PREPARE_PENALTY_YELLOW:
			case TIMEOUT_YELLOW:
				return ETeamColor.YELLOW;
			case BREAK:
			case HALTED:
			case POST_GAME:
			case RUNNING:
			case STOPPED:
			case UNKNOWN:
				return ETeamColor.NEUTRAL;
			default:
				throw new IllegalArgumentException("Please add gamestate \"" + state + "\" to this switch case");
		}
	}
	
	
	
	public ETeamColor getTeamColor()
	{
		return getTeamColorOfState(this);
	}
}
