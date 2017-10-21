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

import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.cam.data.CamDetectionFrame;
import edu.tigers.sumatra.cam.data.CamRobot;



@Persistent
public class ExtendedCamDetectionFrame extends CamDetectionFrame
{
	private final CamBall ball;
	
	
	@SuppressWarnings("unused")
	private ExtendedCamDetectionFrame()
	{
		super();
		ball = null;
	}
	
	
	
	public ExtendedCamDetectionFrame(final CamDetectionFrame frame, final CamBall ball)
	{
		super(frame);
		this.ball = ball;
	}
	
	
	
	public ExtendedCamDetectionFrame(final long frameId, final CamDetectionFrame frame,
			final List<CamBall> balls, final List<CamRobot> yellowBots, final List<CamRobot> blueBots,
			final CamBall ball)
	{
		super(frame.gettCapture(), frame.gettSent(), frame.getCameraId(), frameId, balls, yellowBots,
				blueBots);
		this.ball = ball;
	}
	
	
	
	public final CamBall getBall()
	{
		return ball;
	}
}
