/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.spline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.spline.decisionmaker.CollisionDetectionDecisionMaker;
import edu.tigers.sumatra.ai.sisyphus.spline.decisionmaker.DestinationChangedDecisionMaker;
import edu.tigers.sumatra.ai.sisyphus.spline.decisionmaker.IUpdateSplineDecisionMaker;
import edu.tigers.sumatra.ai.sisyphus.spline.decisionmaker.NewPathShorterDecisionMaker;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.ITrajectory;



public class UpdateSplineDecisionMakerFactory
{
	private final List<IUpdateSplineDecisionMaker>	decisionMakers	= new ArrayList<IUpdateSplineDecisionMaker>();
																						
	private List<IDrawableShape>							shapes			= new LinkedList<IDrawableShape>();
																						
																						
	
	public UpdateSplineDecisionMakerFactory()
	{
		// decisionMakers.add(new BotNotOnSplineDecisionMaker());
		decisionMakers.add(new CollisionDetectionDecisionMaker());
		decisionMakers.add(new NewPathShorterDecisionMaker());
		// decisionMakers.add(new SplineEndGoalNotReachedDecisionMaker());
		decisionMakers.add(new DestinationChangedDecisionMaker());
	}
	
	
	
	public EDecision check(final PathFinderInput localPathFinderInput, final ITrajectory<IVector3> oldSpline,
			final ITrajectory<IVector3> newSpline, final double curTime)
	{
		List<IDrawableShape> newShapes = new LinkedList<IDrawableShape>();
		EDecision hardestDecision = EDecision.NO_VIOLATION;
		for (IUpdateSplineDecisionMaker decisionMaker : decisionMakers)
		{
			EDecision decision = decisionMaker.check(localPathFinderInput, oldSpline, newSpline, newShapes, curTime);
			hardestDecision = decision.max(hardestDecision);
		}
		shapes = newShapes;
		return hardestDecision;
	}
	
	
	
	public List<IDrawableShape> getShapes()
	{
		return shapes;
	}
}
