/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj.path;

import edu.tigers.sumatra.ai.sisyphus.TrajectoryGenerator;
import edu.tigers.sumatra.bot.MoveConstraints;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.trajectory.ITrajectory;



public class TrajPathGen
{
	private TrajectoryGenerator gen = new TrajectoryGenerator();
	
	
	private ITrajectory<IVector2> generateTrajectory(
			final MoveConstraints moveConstraints,
			final IVector2 curPos,
			final IVector2 curVel,
			final IVector2 dest)
	{
		ITrajectory<IVector2> traj = gen.generatePositionTrajectory(moveConstraints, curPos, curVel, dest);
		return traj;
	}
	
	
	
	public TrajPathV2 pathTo(final MoveConstraints moveConstraints, final IVector2 curPos, final IVector2 curVel,
			final IVector2 dest)
	{
		ITrajectory<IVector2> traj = generateTrajectory(moveConstraints, curPos, curVel, dest);
		return new TrajPathV2(traj, traj.getTotalTime(), null);
	}
	
	
	
	public TrajPathV2 append(
			final TrajPathV2 parentPath,
			final MoveConstraints moveConstraints,
			final double connectionTime,
			final IVector2 dest)
	{
		IVector2 curPos = parentPath.getPositionMM(connectionTime);
		IVector2 curVel = parentPath.getVelocity(connectionTime);
		TrajPathV2 childPath = pathTo(moveConstraints, curPos, curVel, dest);
		return parentPath.connect(childPath, connectionTime);
	}
	
	
	
	public TrajPathV2 shortenPath(final TrajPathV2 path, final MoveConstraints moveConstraints, final double tCutFront)
	{
		TrajPathV2 shortenedPath = path.removeOldParts(tCutFront);
		double tCut = Math.min(tCutFront, shortenedPath.getTotalTime());
		IVector2 pos = shortenedPath.getPositionMM(tCut);
		IVector2 vel = shortenedPath.getVelocity(tCut);
		IVector2 dest = shortenedPath.getTrajectory().getPositionMM(Double.MAX_VALUE);
		ITrajectory<IVector2> traj = generateTrajectory(moveConstraints, pos, vel, dest);
		
		return new TrajPathV2(traj, shortenedPath.gettEnd() - tCut, shortenedPath.getChild());
	}
	
	
	
	public TrajectoryGenerator getGen()
	{
		return gen;
	}
}
