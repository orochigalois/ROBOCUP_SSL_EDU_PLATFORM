/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.circle;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public abstract class ACircle implements ICircle
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	ACircle()
	{
	
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public double getArea()
	{
		return radius() * radius() * AngleMath.PI;
	}
	
	
	
	@Override
	public boolean isPointInShape(final IVector2 point, final double margin)
	{
		final Vector2 tmp = point.subtractNew(center());
		
		if (tmp.getLength2() <= (radius() + margin))
		{
			return true;
		}
		return false;
	}
	
	
	
	@Override
	public boolean isLineIntersectingShape(final ILine line)
	{
		return !lineIntersections(line).isEmpty();
	}
	
	
	
	@Override
	public List<IVector2> lineIntersections(final ILine line)
	{
		final List<IVector2> result = new ArrayList<IVector2>();
		
		if (line.directionVector().isZeroVector())
		{
			return result;
		}
		
		final double dx = line.directionVector().x();
		final double dy = line.directionVector().y();
		final double dr = line.directionVector().getLength2();
		final Vector2 newSupport = line.supportVector().subtractNew(center());
		final double det = (newSupport.x() * (newSupport.y() + dy)) - ((newSupport.x() + dx) * newSupport.y());
		
		final double inRoot = (radius() * radius() * dr * dr) - (det * det);
		
		if (inRoot < 0)
		{
			return result;
		}
		
		if (inRoot == 0)
		{
			final Vector2 temp = new Vector2();
			temp.setX((det * dy) / (dr * dr));
			temp.setY((-det * dx) / (dr * dr));
			// because of moved coordinate system (newSupport):
			temp.add(center());
			
			result.add(temp);
			
			return result;
		}
		final double sqRoot = Math.sqrt(inRoot);
		final Vector2 temp1 = new Vector2();
		final Vector2 temp2 = new Vector2();
		
		temp1.setX(((det * dy) + (dx * sqRoot)) / (dr * dr));
		temp1.setY(((-det * dx) + (dy * sqRoot)) / (dr * dr));
		temp2.setX(((det * dy) - (dx * sqRoot)) / (dr * dr));
		temp2.setY(((-det * dx) - (dy * sqRoot)) / (dr * dr));
		// because of moved coordinate system (newSupport):
		temp1.add(center());
		temp2.add(center());
		
		result.add(temp1);
		result.add(temp2);
		return result;
	}
	
	
	
	@Override
	public IVector2 nearestPointOutside(final IVector2 point)
	{
		final Vector2 direction = point.subtractNew(center());
		final double factor = radius() / direction.getLength2();
		
		if (Double.isFinite(factor))
		{
			if (factor <= 1)
			{
				return point;
			}
			direction.multiply(factor);
			direction.add(center());
			return direction;
		}
		return point.addNew(new Vector2(radius(), 0));
	}
	
	
	
	@Override
	public List<IVector2> tangentialIntersections(final IVector2 externalPoint)
	{
		IVector2 dir = externalPoint.subtractNew(center());
		double d = dir.getLength2();
		assert radius() <= d;
		double alpha = Math.acos(radius() / d);
		double beta = dir.getAngle();
		
		List<IVector2> points = new ArrayList<IVector2>(2);
		points.add(center().addNew(new Vector2(beta + alpha).scaleTo(radius())));
		points.add(center().addNew(new Vector2(beta - alpha).scaleTo(radius())));
		return points;
	}
}
