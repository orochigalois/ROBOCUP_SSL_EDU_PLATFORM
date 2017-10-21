/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj;

import java.util.Optional;

import edu.tigers.sumatra.ai.sisyphus.TrajectoryGenerator;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.trajectory.ITrajectory;
import edu.tigers.sumatra.trajectory.TrajectoryWithTime;



public class TrajPathFinderNoObs implements ITrajPathFinder
{
	private final TrajectoryGenerator gen = new TrajectoryGenerator();
	
	
	@Override
	public Optional<TrajectoryWithTime<IVector2>> calcPath(final TrajPathFinderInput input)
	{
		ITrajectory<IVector2> traj = gen.generatePositionTrajectory(input.getMoveCon().getMoveConstraints(),
				input.getPos(), input.getVel(), input.getDest());
		TrajectoryWithTime<IVector2> path = new TrajectoryWithTime<>(traj, input.getTimestamp());
		return Optional.of(path);
	}
	
}
