/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee;

import java.util.ArrayList;
import java.util.List;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ids.ETeamColor;



public class AutoRefConfig
{
	@Configurable(comment = "Enable ball placement calls for the blue teams")
	private static boolean		ballPlacementBlueEnabled		= false;
	
	@Configurable(comment = "Enable ball placement calls for the blue teams")
	private static boolean		ballPlacementYellowEnabled		= false;
	
	@Configurable(comment = "Enable ball placement calls for the blue teams")
	private static ETeamColor	ballPlacementPreference			= ETeamColor.NEUTRAL;
	
	@Configurable(comment = "[mm] The accuracy with which the ball needs to be placed")
	private static double		ballPlacementAccuracy			= 200;
	
	@Configurable(comment = "[mm] The accuracy with which the ball needs to be placed by the robots")
	private static double		robotBallPlacementAccuracy		= 100;
	
	@Configurable(comment = "[m/s] The maximum allowed ball velocity ingame")
	private static double		maxBallVelocity					= 8.5d;
	
	@Configurable(comment = "[m/s] The maximum bot velocity during game stoppage")
	private static double		maxBotStopSpeed					= 1.7d;						// in m/s
																												
	@Configurable(comment = "[m/s] The velocity below which a bot is considered to be stationary")
	private static double		botStationarySpeedThreshold	= 0.3;
	
	@Configurable(comment = "[m/s] The velocity below which the ball is considered to be stationary")
	private static double		ballStationarySpeedThreshold	= 0.08;
	
	@Configurable(comment = "[ms] The time each team has to place the ball")
	private static int			ballPlacementWindow				= 15_000;
	
	@Configurable(comment = "The hostname/ip address of the refbox")
	private static String		refboxHostname						= "localhost";
	
	@Configurable(comment = "The port which will be used to connect to the refbox")
	private static int			refboxPort							= 10007;
	
	static
	{
		ConfigRegistration.registerClass("autoreferee", AutoRefConfig.class);
	}
	
	
	
	public static List<ETeamColor> getBallPlacementTeams()
	{
		List<ETeamColor> teams = new ArrayList<>();
		if (ballPlacementYellowEnabled)
		{
			teams.add(ETeamColor.YELLOW);
		}
		if (ballPlacementBlueEnabled)
		{
			teams.add(ETeamColor.BLUE);
		}
		
		return teams;
	}
	
	
	
	public static ETeamColor getBallPlacementPreference()
	{
		return ballPlacementPreference;
	}
	
	
	
	public static double getBallPlacementAccuracy()
	{
		return ballPlacementAccuracy;
	}
	
	
	
	public static double getRobotBallPlacementAccuracy()
	{
		return robotBallPlacementAccuracy;
	}
	
	
	
	public static double getMaxBallVelocity()
	{
		return maxBallVelocity;
	}
	
	
	
	public static double getMaxBotStopSpeed()
	{
		return maxBotStopSpeed;
	}
	
	
	
	public static double getBotStationarySpeedThreshold()
	{
		return botStationarySpeedThreshold;
	}
	
	
	
	public static double getBallStationarySpeedThreshold()
	{
		return ballStationarySpeedThreshold;
	}
	
	
	
	public static long getBallPlacementWindow()
	{
		return ballPlacementWindow;
	}
	
	
	
	public static String getRefboxHostname()
	{
		return refboxHostname;
	}
	
	
	
	public static int getRefboxPort()
	{
		return refboxPort;
	}
}
