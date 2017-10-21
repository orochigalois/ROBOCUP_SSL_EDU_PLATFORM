/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman;

import Jama.Matrix;
import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;
import edu.tigers.sumatra.wp.kalman.data.PredictionContext;
import edu.tigers.sumatra.wp.kalman.data.WPCamBall;



public class BallProcessor
{
	private final PredictionContext context;
	
	
	
	public BallProcessor(final PredictionContext context)
	{
		this.context = context;
	}
	
	
	
	public void process(final ExtendedCamDetectionFrame frame)
	{
		CamBall ball = frame.getBall();
		double age = (frame.gettCapture() - ball.getTimestamp()) / 1e9;
		if (age > 0.1)
		{
			context.getBall().observation(frame.gettCapture(), null);
		} else
		{
			final WPCamBall visionBall = new WPCamBall(ball);
			context.getBall().observation(ball.getTimestamp(), visionBall);
		}
	}
	
	
	
	public void reset(final long timestamp, final IVector2 pos)
	{
		Matrix m = new Matrix(3, 1);
		m.set(0, 0, pos.x());
		m.set(1, 0, pos.y());
		context.getBall().reset(timestamp, m);
	}
}
