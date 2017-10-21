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

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.spline.EDecision;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.ITrajectory;



public interface IUpdateSplineDecisionMaker
{
	
	EDecision check(PathFinderInput localPathFinderInput, ITrajectory<IVector3> oldSpline,
			ITrajectory<IVector3> newSpline,
			List<IDrawableShape> shapes, double curTime);
}
