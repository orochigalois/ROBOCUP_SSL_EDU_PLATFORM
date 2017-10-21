/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import edu.tigers.sumatra.ai.sisyphus.spline.SplineGenerator;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.shapes.path.IPath;
import edu.tigers.sumatra.trajectory.ITrajectory;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class HermiteSplinePathDriver extends SplinePathDriver
{
	
	public HermiteSplinePathDriver(final ITrackedBot bot, final IPath path)
	{
		super(createSpline(bot, path));
	}
	
	
	private static ITrajectory<IVector3> createSpline(final ITrackedBot bot, final IPath path)
	{
		SplineGenerator splineGenerator = new SplineGenerator(bot.getBot().getType());
		ITrajectory<IVector3> spline = splineGenerator.createSpline(bot, path.getPathPoints(),
				path.getTargetOrientation(), 0);
		return spline;
	}
}
