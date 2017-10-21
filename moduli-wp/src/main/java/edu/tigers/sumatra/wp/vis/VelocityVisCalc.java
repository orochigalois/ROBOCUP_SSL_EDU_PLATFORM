/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.vis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;

import edu.tigers.sumatra.drawable.DrawableLine;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class VelocityVisCalc implements IWpCalc
{
	
	
	@Override
	public void process(final WorldFrameWrapper wfw)
	{
		List<IDrawableShape> shapes = wfw.getShapeMap().get(EWpShapesLayer.VELOCITY);
		
		TrackedBall ball = wfw.getSimpleWorldFrame().getBall();
		if (ball.getVel().getLength() > 0.1)
		{
			ILine ballVelLine = new Line(ball.getPos(), ball.getVel().multiplyNew(1000));
			DrawableLine dBallVelLine = new DrawableLine(ballVelLine, Color.cyan);
			dBallVelLine.setStroke(new BasicStroke(3));
			shapes.add(dBallVelLine);
		}
		
		for (ITrackedBot bot : wfw.getSimpleWorldFrame().getBots().values())
		{
			if (bot.getVel().getLength2() > 0.1)
			{
				ILine velLine = new Line(bot.getPos(), bot.getVel().multiplyNew(1000));
				DrawableLine line = new DrawableLine(velLine, Color.cyan);
				line.setStroke(new BasicStroke(3));
				shapes.add(line);
			}
		}
	}
	
}
