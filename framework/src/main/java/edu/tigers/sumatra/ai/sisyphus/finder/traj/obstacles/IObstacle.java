/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj.obstacles;

import java.util.List;
import java.util.Random;

import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector2;



public interface IObstacle extends IDrawableShape
{
	
	@Deprecated
	default IVector2 shiftDestination(final IVector2 dest)
	{
		if (isPointCollidingWithObstacle(dest, Double.MAX_VALUE))
		{
			return nearestPointOutsideObstacle(dest, Double.MAX_VALUE);
		}
		return dest;
	}
	
	
	
	boolean isPointCollidingWithObstacle(IVector2 point, double t);
	
	
	
	default boolean isPointCollidingWithObstacle(final IVector2 point, final double t, final double margin)
	{
		return isPointCollidingWithObstacle(point, t);
	}
	
	
	
	IVector2 nearestPointOutsideObstacle(IVector2 point, double t);
	
	
	
	void generateObstacleAvoidancePoints(IVector2 curBotPos, Random rnd, List<IVector2> subPoints);
	
	
	
	default boolean isSensitiveToTouch()
	{
		return false;
	}
}
