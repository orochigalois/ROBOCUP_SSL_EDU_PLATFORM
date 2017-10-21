/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.autoreferee.cases;

import java.util.List;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg.EMsgType;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.Geometry;



public class OutOfBoundsCase extends ARefereeCase
{
	
	public OutOfBoundsCase()
	{
		
	}
	
	
	@Override
	protected void checkCase(final MetisAiFrame frame, final List<RefereeCaseMsg> caseMsgs)
	{
		IVector2 ballPos = frame.getWorldFrame().getBall().getPos();
		if (!Geometry.getField().isPointInShape(ballPos))
		{
			// Is the ball outside the goal
			if (Math.abs(ballPos.y()) >= (Geometry.getGoalSize() / 2.0))
			{
				caseMsgs.add(new RefereeCaseMsg(frame.getTacticalField().getBotLastTouchedBall().getTeamColor(),
						EMsgType.OUT_OF_BOUNDS));
			}
		}
	}
}
