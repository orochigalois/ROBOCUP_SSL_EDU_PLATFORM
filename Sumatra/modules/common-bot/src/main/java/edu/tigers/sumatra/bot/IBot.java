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

import java.util.Map;
import java.util.Optional;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.TrajectoryWithTime;



public interface IBot
{
	
	
	double getBatteryRelative();
	
	
	
	double getKickerLevel();
	
	
	
	double getKickerLevelMax();
	
	
	
	double getDribblerSpeed();
	
	
	
	int getHardwareId();
	
	
	
	boolean isAvailableToAi();
	
	
	
	long getLastKickTime();
	
	
	
	EBotType getType();
	
	
	
	Map<EFeature, EFeatureState> getBotFeatures();
	
	
	
	String getControlledBy();
	
	
	
	ETeamColor getColor();
	
	
	
	boolean isBlocked();
	
	
	
	boolean isHideFromAi();
	
	
	
	boolean isHideFromRcm();
	
	
	
	BotID getBotId();
	
	
	
	double getCenter2DribblerDist();
	
	
	
	String getName();
	
	
	
	Optional<IVector3> getSensoryPos();
	
	
	
	Optional<IVector3> getSensoryVel();
	
	
	
	default Optional<TrajectoryWithTime<IVector3>> getCurrentTrajectory()
	{
		return Optional.empty();
	}
	
	
	
	double getKickSpeed();
	
	
	
	String getDevice();
	
	
	
	double getDefaultVelocity();
	
	
	
	double getDefaultAcceleration();
	
	
	
	MoveConstraints getMoveConstraints();
}