/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.spline.decisionmaker;

import java.util.List;

import org.apache.log4j.Logger;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.spline.EDecision;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.ITrajectory;



public class NewPathShorterDecisionMaker implements IUpdateSplineDecisionMaker
{
	
	
	private static final Logger	log							= Logger
																					.getLogger(NewPathShorterDecisionMaker.class.getName());
																					
																					
	@Configurable(comment = "[s] if the new calculated path is faster by this amount of time it will replace the old path [s]")
	private static double			useShorterPathIfFaster	= 1.5;
																			
																			
	static
	{
		ConfigRegistration.registerClass("sisyphus", NewPathShorterDecisionMaker.class);
	}
	
	
	@Override
	public EDecision check(final PathFinderInput localPathFinderInput, final ITrajectory<IVector3> oldSpline,
			final ITrajectory<IVector3> newSpline,
			final List<IDrawableShape> shapes, final double curTime)
	{
		double newPathTotalTime = newSpline.getTotalTime();
		double curTimeOnSpline = curTime;
		double oldPathRemainingTime = oldSpline.getTotalTime() - curTimeOnSpline;
		double threshold = useShorterPathIfFaster;
		if ((newPathTotalTime < (oldPathRemainingTime - threshold)))
		{
			log.trace("New Spline - Shorter Path found: " + newPathTotalTime + " < (" + oldPathRemainingTime + " - "
					+ useShorterPathIfFaster + "s)");
			return EDecision.OPTIMIZATION_FOUND;
		}
		return EDecision.NO_VIOLATION;
	}
}
