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
import java.util.List;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.ITrajectory;
import edu.tigers.sumatra.trajectory.SplineTrajectoryGenerator;
import edu.tigers.sumatra.units.DistanceUnit;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public final class SplineGenerator
{
	
	@Configurable(comment = "Vel [m/s] - Max linear velocity to use for spline generation")
	private static double	maxLinearVelocity			= 2.5;
	@Configurable(comment = "Acc [m/s^2] - Max linear acceleration to use for spline generation")
	private static double	maxLinearAcceleration	= 2.5;
																	
	@Configurable(comment = "Vel [rad/s] - Max rotation velocity to use for spline generation")
	private static double	maxRotateVelocity			= 10;
	@Configurable(comment = "Vel [rad/s^2] - Max rotation acceleration to use for spline generation")
	private static double	maxRotateAcceleration	= 15;
																	
																	
	@Configurable(comment = "Points on a path with a combined angle*distance score below this value will be removed")
	private static double	pathReductionScore		= 0.0;
																	
																	
	static
	{
		ConfigRegistration.registerClass("sisyphus", SplineGenerator.class);
	}
	
	
	
	public SplineGenerator()
	{
	}
	
	
	
	public SplineGenerator(final EBotType botType)
	{
		// parameter not needed atm, but constructor kept for compatibility
	}
	
	
	
	public SplineTrajectoryGenerator createDefaultGenerator()
	{
		SplineTrajectoryGenerator gen = new SplineTrajectoryGenerator();
		gen.setPositionTrajParams(maxLinearVelocity, maxLinearAcceleration);
		gen.setReducePathScore(pathReductionScore);
		gen.setRotationTrajParams(maxRotateVelocity, maxRotateAcceleration);
		return gen;
	}
	
	
	
	public ITrajectory<IVector3> createSpline(final ITrackedBot bot, final List<IVector2> nodes,
			final double finalOrientation, final double speedLimit)
	{
		SplineTrajectoryGenerator gen = createDefaultGenerator();
		if (speedLimit > 0)
		{
			gen.setMaxVelocity(speedLimit);
		}
		return createSpline(bot, nodes, finalOrientation, gen);
	}
	
	
	
	public ITrajectory<IVector3> createSpline(final ITrackedBot bot, final List<IVector2> nodes,
			final double finalOrientation,
			final SplineTrajectoryGenerator gen)
	{
		List<IVector2> nodesMeters = new ArrayList<IVector2>(nodes.size() + 1);
		nodesMeters.add(convertAIVector2SplineNode(bot.getPos()));
		
		for (IVector2 vec : nodes)
		{
			nodesMeters.add(convertAIVector2SplineNode(vec));
		}
		
		return gen.create(nodesMeters, bot.getVel(), AVector2.ZERO_VECTOR,
				convertAIAngle2SplineOrientation(bot.getAngle()),
				convertAIAngle2SplineOrientation(finalOrientation), bot.getaVel(), 0);
	}
	
	
	private IVector2 convertAIVector2SplineNode(final IVector2 vec)
	{
		IVector2 mVec = DistanceUnit.MILLIMETERS.toMeters(vec);
		return mVec;
	}
	
	
	private double convertAIAngle2SplineOrientation(final double angle)
	{
		return angle;
	}
	
	
	
	public static double getMaxLinearVelocity()
	{
		return maxLinearVelocity;
	}
	
	
	
	public static double getMaxLinearAcceleration()
	{
		return maxLinearAcceleration;
	}
	
	
	
	public static double getMaxRotateVelocity()
	{
		return maxRotateVelocity;
	}
	
	
	
	public static double getMaxRotateAcceleration()
	{
		return maxRotateAcceleration;
	}
}
