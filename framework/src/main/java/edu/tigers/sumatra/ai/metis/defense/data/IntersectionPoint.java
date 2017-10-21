/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.defense.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedObject;



@Persistent(version = 1)
public class IntersectionPoint extends Vector2
{
	
	public static final Comparator<? super IntersectionPoint>	DIST_TO_GOAL	= new Dist2GoalComparator();
	
	private final List<ITrackedObject>									passingBots;
	
	
	
	public IntersectionPoint()
	{
		super();
		passingBots = new ArrayList<>(2);
	}
	
	
	
	public IntersectionPoint(final IVector2 point, final ITrackedObject firstObject, final ITrackedObject secondObject)
	{
		super(point);
		passingBots = new ArrayList<>(2);
		passingBots.add(firstObject);
		passingBots.add(secondObject);
	}
	
	
	private static class Dist2GoalComparator implements Comparator<IntersectionPoint>
	{
		@Override
		public int compare(final IntersectionPoint point1, final IntersectionPoint point2)
		{
			IVector2 goalCenter = Geometry.getGoalOur().getGoalCenter();
			
			return (int) Math
					.signum(goalCenter.subtractNew(point2).getLength() - goalCenter.subtractNew(point1).getLength());
		}
	}
}
