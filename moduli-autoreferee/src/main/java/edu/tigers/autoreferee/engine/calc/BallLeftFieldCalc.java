/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.calc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.tigers.autoreferee.AutoRefFrame;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.wp.data.Geometry;



public class BallLeftFieldCalc implements IRefereeCalc
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger	log				= Logger.getLogger(BallLeftFieldCalc.class.getName());
	private static final int		BUFFER_SIZE		= 5;
	private final List<IVector2>	lastBallPoss	= new ArrayList<IVector2>(BUFFER_SIZE);
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void process(final AutoRefFrame frame)
	{
		frame.setBallLeftFieldPos(frame.getPreviousFrame().getBallLeftFieldPos());
		
		IVector2 ballPos = frame.getWorldFrame().getBall().getPos();
		if (Geometry.getField().isPointInShape(ballPos))
		{
			if (lastBallPoss.size() == BUFFER_SIZE)
			{
				lastBallPoss.remove(BUFFER_SIZE - 1);
			}
			lastBallPoss.add(0, ballPos);
			frame.setBallLeftFieldPos(null);
		} else if ((frame.getPreviousFrame().getBallLeftFieldPos() == null)
				&& !lastBallPoss.isEmpty())
		{
			ILine line = Line.newLine(lastBallPoss.get(0), ballPos);
			List<IVector2> ballIntersectionPoints = Geometry.getField().lineIntersections(line);
			if (ballIntersectionPoints.isEmpty())
			{
				log.warn("Ball left field, but there was no intersection with field borders?!");
			} else
			{
				frame.setBallLeftFieldPos(ballIntersectionPoints.get(0));
			}
		}
	}
}