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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;



@Persistent
public class RefereeMsgTeamSpec extends RefereeMsg
{
	private final ETeamColor		color;
	private final ETeamSpecRefCmd	teamSpecRefCmd;
	
	
	@SuppressWarnings("unused")
	private RefereeMsgTeamSpec()
	{
		super();
		color = ETeamColor.UNINITIALIZED;
		teamSpecRefCmd = ETeamSpecRefCmd.NoCommand;
	}
	
	
	
	public RefereeMsgTeamSpec(final RefereeMsg refereeMsg, final ETeamColor color)
	{
		super(refereeMsg);
		teamSpecRefCmd = createTeamSpecRefCmd(refereeMsg, color);
		this.color = color;
	}
	
	
	private ETeamSpecRefCmd createTeamSpecRefCmd(final RefereeMsg sslRefereeMsg, final ETeamColor color)
	{
		switch (sslRefereeMsg.getCommand())
		{
			case DIRECT_FREE_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.DirectFreeKickEnemies
						: ETeamSpecRefCmd.DirectFreeKickTigers;
			case DIRECT_FREE_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.DirectFreeKickTigers
						: ETeamSpecRefCmd.DirectFreeKickEnemies;
			case INDIRECT_FREE_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.IndirectFreeKickEnemies
						: ETeamSpecRefCmd.IndirectFreeKickTigers;
			case INDIRECT_FREE_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.IndirectFreeKickTigers
						: ETeamSpecRefCmd.IndirectFreeKickEnemies;
			case PREPARE_KICKOFF_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.KickOffEnemies : ETeamSpecRefCmd.KickOffTigers;
			case PREPARE_KICKOFF_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.KickOffTigers : ETeamSpecRefCmd.KickOffEnemies;
			case PREPARE_PENALTY_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.PenaltyEnemies : ETeamSpecRefCmd.PenaltyTigers;
			case PREPARE_PENALTY_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.PenaltyTigers : ETeamSpecRefCmd.PenaltyEnemies;
			case TIMEOUT_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.TimeoutEnemies : ETeamSpecRefCmd.TimeoutTigers;
			case TIMEOUT_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.TimeoutTigers : ETeamSpecRefCmd.TimeoutEnemies;
			case GOAL_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.GoalEnemies : ETeamSpecRefCmd.GoalTigers;
			case GOAL_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.GoalTigers : ETeamSpecRefCmd.GoalEnemies;
			case BALL_PLACEMENT_BLUE:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.BallPlacementEnemies
						: ETeamSpecRefCmd.BallPlacementTigers;
			case BALL_PLACEMENT_YELLOW:
				return color == ETeamColor.YELLOW ? ETeamSpecRefCmd.BallPlacementTigers
						: ETeamSpecRefCmd.BallPlacementEnemies;
			case FORCE_START:
				return ETeamSpecRefCmd.ForceStart;
			case HALT:
				return ETeamSpecRefCmd.Halt;
			case NORMAL_START:
				return ETeamSpecRefCmd.NormalStart;
			case STOP:
				return ETeamSpecRefCmd.Stop;
		}
		return ETeamSpecRefCmd.NoCommand;
	}
	
	
	
	public final TeamInfo getTeamInfoTigers()
	{
		if (color == ETeamColor.YELLOW)
		{
			return getTeamInfoYellow();
		}
		return getTeamInfoBlue();
	}
	
	
	
	public final TeamInfo getTeamInfoThem()
	{
		if (color == ETeamColor.YELLOW)
		{
			return getTeamInfoBlue();
		}
		return getTeamInfoYellow();
	}
	
	
	
	public final ETeamSpecRefCmd getTeamSpecRefCmd()
	{
		return teamSpecRefCmd;
	}
	
	
	
	public final ETeamColor getColor()
	{
		return color;
	}
	
	
	@Override
	public IVector2 getBallPlacementPos()
	{
		IVector2 camFramePos = super.getBallPlacementPos();
		if (color != getLeftTeam())
		{
			return camFramePos.multiplyNew(-1.0d);
		}
		return camFramePos;
	}
}
