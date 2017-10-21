/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman.data;

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.wp.kalman.WPConfig;



public class WPCamBot extends AWPCamObject
{
	
	public double	orientation;
	
	
	
	public WPCamBot(final CamRobot robot)
	{
		x = robot.getPos().x() * WPConfig.FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
		y = robot.getPos().y() * WPConfig.FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
		orientation = robot.getOrientation();
		id = robot.getRobotID();
	}
}
