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

import java.util.EnumMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.Referee.SSL_Referee;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.Referee.SSL_Referee.Stage;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public class RefereeMsg
{
	
	private final long			frameTimestamp;
	private final Command		command;
	
	private final long			cmdTimestamp;
	private final long			cmdCounter;
	
	private final long			packetTimestamp;
	private final Stage			stage;
	
	private final long			stageTimeLeft;
	private final TeamInfo		teamInfoYellow;
	private final TeamInfo		teamInfoBlue;
	
	private final ETeamColor	leftTeam;
	private final IVector2		ballPlacementPos;
	
	
	
	public RefereeMsg()
	{
		frameTimestamp = 0;
		command = Command.HALT;
		cmdTimestamp = 0;
		cmdCounter = -1;
		packetTimestamp = 0;
		stage = Stage.NORMAL_FIRST_HALF;
		stageTimeLeft = 0;
		teamInfoYellow = new TeamInfo();
		teamInfoBlue = new TeamInfo();
		leftTeam = TeamConfig.getLeftTeam();
		ballPlacementPos = new Vector2();
	}
	
	
	
	public RefereeMsg(final long frameTimestamp, final SSL_Referee sslRefereeMsg)
	{
		this.frameTimestamp = frameTimestamp;
		command = sslRefereeMsg.getCommand();
		cmdTimestamp = sslRefereeMsg.getCommandTimestamp();
		cmdCounter = sslRefereeMsg.getCommandCounter();
		packetTimestamp = sslRefereeMsg.getPacketTimestamp();
		stage = sslRefereeMsg.getStage();
		stageTimeLeft = sslRefereeMsg.getStageTimeLeft();
		
		teamInfoYellow = new TeamInfo(sslRefereeMsg.getYellow());
		teamInfoBlue = new TeamInfo(sslRefereeMsg.getBlue());
		
		if (sslRefereeMsg.hasDesignatedPosition())
		{
			SSL_Referee.Point msgBallPos = sslRefereeMsg.getDesignatedPosition();
			ballPlacementPos = new Vector2(msgBallPos.getX(), msgBallPos.getY());
		} else
		{
			ballPlacementPos = new Vector2();
		}
		
		leftTeam = TeamConfig.getLeftTeam();
	}
	
	
	
	public RefereeMsg(final RefereeMsg refereeMsg)
	{
		frameTimestamp = refereeMsg.getFrameTimestamp();
		command = refereeMsg.command;
		cmdTimestamp = refereeMsg.cmdTimestamp;
		cmdCounter = refereeMsg.cmdCounter;
		packetTimestamp = refereeMsg.packetTimestamp;
		stage = refereeMsg.stage;
		stageTimeLeft = refereeMsg.stageTimeLeft;
		teamInfoYellow = refereeMsg.teamInfoYellow;
		teamInfoBlue = refereeMsg.teamInfoBlue;
		leftTeam = refereeMsg.leftTeam;
		ballPlacementPos = refereeMsg.getBallPlacementPos();
	}
	
	
	
	public final BotID getKeeperBotID(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return BotID.createBotId(teamInfoBlue.getGoalie(), color);
			case YELLOW:
				return BotID.createBotId(teamInfoYellow.getGoalie(), color);
			default:
				throw new IllegalArgumentException();
		}
	}
	
	
	
	public final Command getCommand()
	{
		return command;
	}
	
	
	
	public final long getCommandTimestamp()
	{
		return cmdTimestamp;
	}
	
	
	
	public final long getCommandCounter()
	{
		return cmdCounter;
	}
	
	
	
	public final long getPacketTimestamp()
	{
		return packetTimestamp;
	}
	
	
	
	public final Stage getStage()
	{
		return stage;
	}
	
	
	
	public final long getStageTimeLeft()
	{
		return stageTimeLeft;
	}
	
	
	
	public final TeamInfo getTeamInfoBlue()
	{
		return teamInfoBlue;
	}
	
	
	
	public final TeamInfo getTeamInfoYellow()
	{
		return teamInfoYellow;
	}
	
	
	
	public final TeamInfo getTeamInfo(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return teamInfoBlue;
			case YELLOW:
				return teamInfoYellow;
			default:
				throw new IllegalArgumentException("Please specify a valid team color. The following value is invalid: "
						+ color);
		}
		
	}
	
	
	
	public final ETeamColor getLeftTeam()
	{
		return leftTeam;
	}
	
	
	
	public IVector2 getBallPlacementPos()
	{
		return ballPlacementPos;
	}
	
	
	
	public final long getFrameTimestamp()
	{
		return frameTimestamp;
	}
	
	
	
	public Map<ETeamColor, Integer> getGoals()
	{
		int goalsYellow = teamInfoYellow.getScore();
		int goalsBlue = teamInfoBlue.getScore();
		
		return buildMap(goalsBlue, goalsYellow);
	}
	
	
	
	public Map<ETeamColor, String> getTeamNames()
	{
		String yellowName = teamInfoYellow.getName();
		String blueName = teamInfoBlue.getName();
		return buildMap(blueName, yellowName);
	}
	
	
	private <T> Map<ETeamColor, T> buildMap(final T blue, final T yellow)
	{
		Map<ETeamColor, T> map = new EnumMap<>(ETeamColor.class);
		map.put(ETeamColor.YELLOW, yellow);
		map.put(ETeamColor.BLUE, blue);
		return map;
	}
}
