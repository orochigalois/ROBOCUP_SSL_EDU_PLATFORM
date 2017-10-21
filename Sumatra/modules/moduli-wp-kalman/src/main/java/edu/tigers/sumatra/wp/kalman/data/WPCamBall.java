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

import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.wp.kalman.WPConfig;



public class WPCamBall extends AWPCamObject
{
	
	public double	z;
	
	
	
	public WPCamBall(final CamBall ball)
	{
		id = 0;
		x = ball.getPos().x() * WPConfig.FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
		y = ball.getPos().y() * WPConfig.FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
		z = ball.getPos().z() * WPConfig.FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
	}
}
