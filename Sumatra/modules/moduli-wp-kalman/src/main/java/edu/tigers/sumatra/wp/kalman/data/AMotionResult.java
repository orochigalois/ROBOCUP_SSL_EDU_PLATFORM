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


public abstract class AMotionResult
{
	
	public final double		x;
	
	public final double		y;
									
	private final double		confidence;
	private final boolean	onCam;
									
									
	AMotionResult(final double x, final double y, final double confidence, final boolean onCam)
	{
		this.x = x;
		this.y = y;
		this.confidence = confidence;
		this.onCam = onCam;
	}
	
	
	
	public double getX()
	{
		return x;
	}
	
	
	
	public double getY()
	{
		return y;
	}
	
	
	
	public double getConfidence()
	{
		return confidence;
	}
	
	
	
	public boolean isOnCam()
	{
		return onCam;
	}
	
	
}
