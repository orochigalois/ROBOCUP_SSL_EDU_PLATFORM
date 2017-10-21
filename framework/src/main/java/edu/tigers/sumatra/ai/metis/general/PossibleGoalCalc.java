/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.general;

import edu.tigers.sumatra.ai.data.EPossibleGoal;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.Goal;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class PossibleGoalCalc extends ACalculator
{
	// --------------------------------------------------------------------------
	// --- variable(s) ----------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private final Goal	goalOur		= Geometry.getGoalOur();
	private final Goal	goalTheir	= Geometry.getGoalTheir();
	
	
	// --------------------------------------------------------------------------
	// --- constructor(s) -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- method(s) ------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		WorldFrame wFrame = baseAiFrame.getWorldFrame();
		EPossibleGoal isGoal = EPossibleGoal.NO_ONE;
		final IVector2 ball = wFrame.getBall().getPos();
		if (isBallPossiblyInGoal(ball, goalOur))
		{
			isGoal = EPossibleGoal.THEY;
		}
		if (isBallPossiblyInGoal(ball, goalTheir))
		{
			isGoal = EPossibleGoal.WE;
		}
		newTacticalField.setPossibleGoal(isGoal);
	}
	
	
	
	private boolean isBallPossiblyInGoal(final IVector2 ball, final Goal goal)
	{
		// if the y value of the ball is not between the both goal posts, there will not be a goal,
		// hopefully no one will place the goals on a different position
		if (!(((goal.getGoalPostLeft().y() < ball.y()) && (goal.getGoalPostRight().y() > ball.y())) || ((goal
				.getGoalPostLeft().y() > ball.y()) && (goal.getGoalPostRight().y() < ball.y()))))
		{
			return false;
		}
		// did the ball crossed the goal line?
		final double goalX = goal.getGoalCenter().x();
		if (((goalX < 0) && ((goalX + 25) > ball.x())) || ((goalX > 0) && ((goalX - 25) < ball.x())))
		{
			return true;
		}
		return false;
	}
	
	
	@Override
	public void fallbackCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		newTacticalField.setPossibleGoal(EPossibleGoal.NO_ONE);
	}
}
