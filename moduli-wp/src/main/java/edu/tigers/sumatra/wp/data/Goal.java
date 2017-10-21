/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.MathException;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector2f;



public class Goal
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private final double		size;
	private final Vector2f	goalCenter;
	private final Vector2f	goalPostLeft;
	private final Vector2f	goalPostRight;
	
	private final Vector2	goalPostLeftBack;
	private final Vector2	goalPostRightBack;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Goal(final double size, final IVector2 goalCenter, final double depth)
	{
		this.size = size;
		this.goalCenter = new Vector2f(goalCenter);
		
		goalPostLeft = new Vector2f(goalCenter.x(), goalCenter.y() + (size / 2.0));
		goalPostRight = new Vector2f(goalCenter.x(), goalCenter.y() - (size / 2.0));
		
		goalPostLeftBack = goalPostLeft.addNew(AVector2.X_AXIS.scaleToNew(depth));
		goalPostRightBack = goalPostRight.addNew(AVector2.X_AXIS.scaleToNew(depth));
		
	}
	
	
	
	public boolean isLineCrossingGoal(final IVector2 point, final IVector2 point2, final double margin)
	{
		try
		{
			if (GeoMath.distanceBetweenLineSegments(goalPostLeft, goalPostLeftBack, point, point2) < margin)
			{
				return true;
			}
			if (GeoMath.distanceBetweenLineSegments(goalPostLeftBack, goalPostRightBack, point, point2) < margin)
			{
				return true;
			}
			if (GeoMath.distanceBetweenLineSegments(goalPostRight, goalPostRightBack, point, point2) < margin)
			{
				return true;
			}
		} catch (MathException err)
		{
			return false;
		}
		return false;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public double getSize()
	{
		return size;
	}
	
	
	
	public Vector2f getGoalCenter()
	{
		return goalCenter;
	}
	
	
	
	public Vector2f getGoalPostLeft()
	{
		return goalPostLeft;
		
	}
	
	
	
	public Vector2f getGoalPostRight()
	{
		return goalPostRight;
	}
	
	
}
