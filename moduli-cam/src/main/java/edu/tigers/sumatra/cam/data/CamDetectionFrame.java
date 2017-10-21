/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam.data;

import java.util.Collections;
import java.util.List;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class CamDetectionFrame
{
	
	private final long				tCapture;
	
	
	private final long				tSent;
	
	
	private final int					cameraId;
	
	
	private final long				frameNumber;
	private final List<CamBall>	balls;
	private final List<CamRobot>	robotsYellow;
	private final List<CamRobot>	robotsBlue;
	
	
	
	protected CamDetectionFrame()
	{
		tCapture = 0;
		tSent = 0;
		cameraId = 0;
		frameNumber = 0;
		balls = null;
		robotsYellow = null;
		robotsBlue = null;
	}
	
	
	
	public CamDetectionFrame(final long tCapture, final long tSent, final int cameraId,
			final long frameNumber,
			final List<CamBall> balls, final List<CamRobot> yellowBots, final List<CamRobot> blueBots)
	{
		// Fields
		this.tCapture = tCapture;
		this.tSent = tSent;
		this.cameraId = cameraId;
		this.frameNumber = frameNumber;
		
		// Collections
		this.balls = balls;
		robotsYellow = yellowBots;
		robotsBlue = blueBots;
	}
	
	
	
	public CamDetectionFrame(final CamDetectionFrame f)
	{
		tCapture = f.tCapture;
		tSent = f.tSent;
		cameraId = f.cameraId;
		frameNumber = f.frameNumber;
		balls = f.balls;
		robotsBlue = f.robotsBlue;
		robotsYellow = f.robotsYellow;
	}
	
	
	@Override
	public String toString()
	{
		return "tCapture:" + gettCapture() + "/tSend:" + gettSent() + "/cameraId:"
				+ getCameraId()
				+ "/frameNumber:" + getFrameNumber() + "/balls:" + getBalls() + "/rY:"
				+ getRobotsYellow()
				+ "/rB:" + getRobotsBlue();
	}
	
	
	
	public long gettCapture()
	{
		return tCapture;
	}
	
	
	
	public long gettSent()
	{
		return tSent;
	}
	
	
	
	public int getCameraId()
	{
		return cameraId;
	}
	
	
	
	public long getFrameNumber()
	{
		return frameNumber;
	}
	
	
	
	public List<CamBall> getBalls()
	{
		return Collections.unmodifiableList(balls);
	}
	
	
	
	public List<CamRobot> getRobotsYellow()
	{
		return Collections.unmodifiableList(robotsYellow);
	}
	
	
	
	public List<CamRobot> getRobotsBlue()
	{
		return Collections.unmodifiableList(robotsBlue);
	}
}
