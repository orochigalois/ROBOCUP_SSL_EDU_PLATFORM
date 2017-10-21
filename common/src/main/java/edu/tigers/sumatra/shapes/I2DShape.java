/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes;

import java.util.List;

import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;



public interface I2DShape
{
	
	
	double getArea();
	
	
	
	default boolean isPointInShape(final IVector2 point)
	{
		return isPointInShape(point, 0.0d);
	}
	
	
	
	boolean isPointInShape(IVector2 point, double margin);
	
	
	
	boolean isLineIntersectingShape(ILine line);
	
	
	
	IVector2 nearestPointOutside(IVector2 point);
	
	
	
	List<IVector2> lineIntersections(ILine line);
	
	
	
	default boolean isLineSegmentIntersectingShape(final IVector2 start, final IVector2 end)
	{
		List<IVector2> intersecs = lineIntersections(Line.newLine(start, end));
		Rectangle rect = new Rectangle(start, end);
		for (IVector2 inters : intersecs)
		{
			if (rect.isPointInShape(inters))
			{
				return true;
			}
		}
		return false;
	}
}
