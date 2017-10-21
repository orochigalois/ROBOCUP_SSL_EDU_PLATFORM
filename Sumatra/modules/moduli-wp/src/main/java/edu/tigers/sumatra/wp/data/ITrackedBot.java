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

import edu.tigers.sumatra.bot.IBot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;



public interface ITrackedBot extends ITrackedObject
{
	
	
	ITrackedBot mirrorNew();
	
	
	
	IVector2 getPosByTime(double t);
	
	
	
	IVector2 getVelByTime(double t);
	
	
	
	double getAngleByTime(double t);
	
	
	
	boolean hasBallContact();
	
	
	
	boolean isVisible();
	
	
	
	double getCenter2DribblerDist();
	
	
	
	IVector2 getBotKickerPos();
	
	
	
	@Override
	BotID getBotId();
	
	
	
	ETeamColor getTeamColor();
	
	
	
	double getAngle();
	
	
	
	double getaVel();
	
	
	
	double getaAcc();
	
	
	
	boolean isAvailableToAi();
	
	
	
	IBot getBot();
	
	
	
	IVector2 getBotKickerPosByTime(double t);
	
}