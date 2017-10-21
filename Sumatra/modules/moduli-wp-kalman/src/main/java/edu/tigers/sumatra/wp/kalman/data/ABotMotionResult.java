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

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public abstract class ABotMotionResult extends AMotionResult
{
	
	
	public final double orientation;
	
	
	
	public ABotMotionResult(final double x, final double y, final double orientation, final double confidence,
			final boolean onCam)
	{
		super(x, y, confidence, onCam);
		this.orientation = orientation;
	}
	
	
	
	public abstract ITrackedBot motionToTrackedBot(long timestamp, final BotID botId);
	
}
