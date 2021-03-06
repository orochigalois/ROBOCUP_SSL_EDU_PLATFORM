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

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ids.ETeam;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.I2DShape;



public class ExtendedPenaltyArea implements I2DShape
{
	
	private final PenaltyArea		wrappedArea;
	private final double				sign;
	private final double				leftBound, rightBound, upperBound;
	private static final double	DBL_EPSILON	= 1e-7;
															
															
	
	public ExtendedPenaltyArea(final PenaltyArea area)
	{
		wrappedArea = area;
		sign = area.getOwner() == ETeam.TIGERS ? 1 : -1;
		
		final IVector2 p1 = area.getPenaltyCircleNegCentre().addNew(new Vector2(0.0, -area.getRadiusOfPenaltyArea()));
		leftBound = p1.y();
		upperBound = p1.x();
		rightBound = -p1.y();
	}
	
	
	
	public boolean isBehindPenaltyArea(final IVector2 point, final double margin)
	{
		// x > upperBound
		// y in range left-right +-margin
		final boolean b1 = (point.x() * sign) < (upperBound * sign);
		final boolean b2 = point.y() > (leftBound - margin);
		final boolean b3 = point.y() < (rightBound + margin);
		
		return b1 && (b2 && b3);
	}
	
	
	
	public PenaltyArea getBaseArea()
	{
		return wrappedArea;
	}
	
	
	
	@Override
	public double getArea()
	{
		return wrappedArea.getArea();
	}
	
	
	@Override
	public boolean isPointInShape(final IVector2 point, final double margin)
	{
		return isBehindPenaltyArea(point, margin) || wrappedArea.isPointInShape(point, margin);
	}
	
	
	@Override
	public boolean isLineIntersectingShape(final ILine line)
	{
		return !lineIntersections(line).isEmpty();
	}
	
	
	@Override
	public IVector2 nearestPointOutside(final IVector2 point)
	{
		
		if (isBehindPenaltyArea(point, -1e-7))
		{
			return new Vector2(point.x(), ((point.y() < 0) ? leftBound : rightBound));
		}
		return wrappedArea.nearestPointOutside(point);
		
	}
	
	
	
	public IVector2 nearestPointOutside(final IVector2 point, final double margin)
	{
		if (isBehindPenaltyArea(point, margin - 1e-7))
		{
			return new Vector2(point.x(), ((point.y() < 0) ? leftBound - margin : rightBound + margin));
		}
		return wrappedArea.nearestPointOutside(point, margin);
	}
	
	
	
	public IVector2 nearestPointOutside(final IVector2 point, final IVector2 pointToBuildLine, final double margin)
	{
		
		if (isBehindPenaltyArea(point, margin))
		{
			if (point.equals(pointToBuildLine, DBL_EPSILON))
			{
				return nearestPointOutside(point, margin);
			}
			IVector2 backPathSupp = new Vector2(upperBound + (sign * 1e-3), leftBound - margin);
			IVector2 backPathDir = new Vector2(0, (rightBound - leftBound) + (2 * margin));
			
			ILine builtLine = Line.newLine(point, pointToBuildLine);
			IVector2 pointInPA = GeoMath.intersectionPointLinePath(builtLine, backPathSupp,
					backPathDir);
			if (pointInPA != null)
			{
				return wrappedArea.nearestPointOutside(pointInPA, pointToBuildLine, margin);
			}
			List<IVector2> intersections = calcLineIntersections(builtLine);
			return GeoMath.nearestPointInList(intersections, point);
		}
		return wrappedArea.nearestPointOutside(point, pointToBuildLine, margin);
	}
	
	
	private List<IVector2> calcLineIntersections(final ILine line)
	{
		List<IVector2> intersections = new ArrayList<IVector2>(2);
		final IVector2 isecLeft = GeoMath.intersectionPointLineHalfLine(line,
				new Line(new Vector2(upperBound, leftBound), new Vector2(-sign, 0)));
		if (isecLeft != null)
		{
			intersections.add(isecLeft);
		}
		
		final IVector2 isecRight = GeoMath.intersectionPointLineHalfLine(line,
				new Line(new Vector2(upperBound, rightBound), new Vector2(-sign, 0)));
				
		if (isecRight != null)
		{
			intersections.add(isecRight);
		}
		return intersections;
	}
	
	
	@Override
	public List<IVector2> lineIntersections(final ILine line)
	{
		List<IVector2> intersections = wrappedArea.lineIntersections(line);
		
		if (intersections.isEmpty())
		{
			return calcLineIntersections(line);
		}
		return intersections;
	}
	
}
