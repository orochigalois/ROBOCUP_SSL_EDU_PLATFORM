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

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.trajectory.TrajectoryWithTime;



public interface ITrajPathFinder
{
	
	Optional<TrajectoryWithTime<IVector2>> calcPath(final TrajPathFinderInput input);
}
