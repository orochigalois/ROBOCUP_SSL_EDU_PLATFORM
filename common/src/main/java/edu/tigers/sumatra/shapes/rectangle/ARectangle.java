/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.MathException;
import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector2f;



@Persistent
public abstract class ARectangle implements IRectangle
{
	
	public ARectangle()
	{
	}
	
	
	@Override
	public int hashCode()
	{
		return topLeft().hashCode() + Double.valueOf(xExtend()).hashCode() + Double.valueOf(yExtend()).hashCode();
	}
	
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		
		builder.append("(");
		builder.append(xExtend());
		builder.append("/");
		builder.append(yExtend());
		builder.append(")");
		builder.append("ref");
		builder.append(topLeft().toString());
		return builder.toString();
	}
	
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		if (obj == this)
		{
			return true;
		}
		
		if (!obj.getClass().equals(getClass()))
		{
			return false;
		}
		
		final ARectangle rectangle = (ARectangle) obj;
		
		if (SumatraMath.isEqual(xExtend(), rectangle.xExtend()) && (SumatraMath.isEqual(yExtend(), rectangle.yExtend()))
				&& topLeft().equals(rectangle.topLeft()))
		{
			return true;
		}
		return false;
	}
	
	
	@Override
	public double getArea()
	{
		return xExtend() * yExtend();
	}
	
	
	
	@Override
	public boolean isPointInShape(final IVector2 point, final double margin)
	{
		return (((point.x() + margin) >= topLeft().x()) && ((point.x() - margin) <= (topLeft().x() + xExtend()))
				&& ((point.y() - margin) <= topLeft().y()) && ((point.y() + margin) >= (topLeft().y() - yExtend())));
	}
	
	
	
	@Override
	@Deprecated
	public boolean isLineIntersectingShape(final ILine line)
	{
		final IVector2 s = new Vector2(line.supportVector());
		final IVector2 n = line.directionVector().normalizeNew();
		final boolean d1 = SumatraMath.isPositive(topLeft().subtractNew(s).scalarProduct(n));
		final boolean d2 = SumatraMath.isPositive(topRight().subtractNew(s).scalarProduct(n));
		final boolean d3 = SumatraMath.isPositive(bottomLeft().subtractNew(s).scalarProduct(n));
		final boolean d4 = SumatraMath.isPositive(bottomRight().subtractNew(s).scalarProduct(n));
		
		return !SumatraMath.allTheSame(d1, d2, d3, d4);
	}
	
	
	
	public List<IVector2> lineIntersection(final ILine line)
	{
		final List<IVector2> retList = new ArrayList<IVector2>();
		double vecT;
		double intercept;
		
		// If line is horizontal or vertical
		if (line.isHorizontal())
		{
			// Top border
			if (topLeft().y() == line.supportVector().y())
			{
				retList.add(topLeft());
				retList.add(new Vector2f((topLeft().x() + topRight().x()) / 2, topLeft().y()));
				retList.add(topRight());
				return retList;
			}
			// Bottom border
			else if (bottomLeft().y() == line.supportVector().y())
			{
				retList.add(topLeft());
				retList.add(new Vector2f((topLeft().x() + topRight().x()) / 2, topLeft().y()));
				retList.add(topRight());
				return retList;
			}
			// Between top and bottom border
			else if ((line.supportVector().y() < topLeft().y()) && (line.supportVector().y() > bottomLeft().y()))
			{
				retList.add(new Vector2f(topLeft().x(), line.supportVector().y()));
				retList.add(new Vector2f(topRight().x(), line.supportVector().y()));
				return retList;
			}
		} else if (line.isVertical())
		{
			// Left border
			if (topLeft().x() == line.supportVector().x())
			{
				retList.add(topLeft());
				retList.add(new Vector2f(topLeft().x(), (topLeft().y() + bottomLeft().y()) / 2.0));
				retList.add(bottomLeft());
				return retList;
			}
			// Right border
			else if (topRight().x() == line.supportVector().x())
			{
				retList.add(topRight());
				retList.add(new Vector2f(topRight().x(), (topRight().y() + bottomRight().y()) / 2.0));
				retList.add(bottomLeft());
				return retList;
			}
			// Between left and right border
			else if ((line.supportVector().x() > topLeft().x()) && (line.supportVector().x() < topRight().x()))
			{
				retList.add(new Vector2f(line.supportVector().x(), topLeft().y()));
				retList.add(new Vector2f(line.supportVector().x(), bottomLeft().y()));
				return retList;
			}
		}
		
		// Line is neither horizontal nor vertical, calculate intersections with all borders
		
		// Check top border intersection
		vecT = (topLeft().y() - line.supportVector().y()) / line.directionVector().y();
		intercept = line.supportVector().x() + (line.directionVector().x() * vecT);
		if ((intercept >= topLeft().x()) && (intercept <= topRight().x()))
		{
			retList.add(new Vector2f(intercept, topLeft().y()));
		}
		// Check bottom border intersection
		vecT = (bottomLeft().y() - line.supportVector().y()) / line.directionVector().y();
		intercept = line.supportVector().x() + (line.directionVector().x() * vecT);
		if ((intercept >= bottomLeft().x()) && (intercept <= bottomRight().x()))
		{
			retList.add(new Vector2f(intercept, bottomLeft().y()));
		}
		// Check left border intersection
		vecT = (bottomLeft().x() - line.supportVector().x()) / line.directionVector().x();
		intercept = line.supportVector().y() + (line.directionVector().y() * vecT);
		if ((intercept > bottomLeft().y()) && (intercept < topLeft().y()))
		{
			retList.add(new Vector2f(bottomLeft().x(), intercept));
		}
		// Check right border intersection
		vecT = (bottomRight().x() - line.supportVector().x()) / line.directionVector().x();
		intercept = line.supportVector().y() + (line.directionVector().y() * vecT);
		if ((intercept > bottomRight().y()) && (intercept < topRight().y()))
		{
			retList.add(new Vector2f(bottomRight().x(), intercept));
		}
		
		return retList;
	}
	
	
	
	public boolean isLineSegmentIntersectingRectangle(final IVector2 startPoint, final IVector2 dir1)
	{
		final IVector2 endPoint = startPoint.addNew(dir1);
		
		final double a = ((endPoint.y() - startPoint.y()) * topLeft().x())
				+ ((startPoint.x() - endPoint.x()) * topLeft().y())
				+ ((endPoint.x() * startPoint.y()) - (startPoint.x() * endPoint.y()));
		final double b = ((endPoint.y() - startPoint.y()) * topRight().x())
				+ ((startPoint.x() - endPoint.x()) * topRight().y())
				+ ((endPoint.x() * startPoint.y()) - (startPoint.x() * endPoint.y()));
		final double c = ((endPoint.y() - startPoint.y()) * bottomRight().x())
				+ ((startPoint.x() - endPoint.x()) * bottomRight().y())
				+ ((endPoint.x() * startPoint.y()) - (startPoint.x() * endPoint.y()));
		final double d = ((endPoint.y() - startPoint.y()) * bottomLeft().x())
				+ ((startPoint.x() - endPoint.x()) * bottomLeft().y())
				+ ((endPoint.x() * startPoint.y()) - (startPoint.x() * endPoint.y()));
				
		boolean allNeg = ((a < 0) && (b < 0) && (c < 0) && (d < 0));
		boolean allPos = ((a > 0) && (b > 0) && (c > 0) && (d > 0));
		if (allNeg || allPos)
		{
			return false;
		}
		
		if ((startPoint.x() > topRight().x()) && (endPoint.x() > topRight().x()))
		{
			return false;
		}
		if ((startPoint.x() < bottomLeft().x()) && (endPoint.x() < bottomLeft().x()))
		{
			return false;
		}
		if ((startPoint.y() > topRight().y()) && (endPoint.y() > topRight().y()))
		{
			return false;
		}
		if ((startPoint.y() < bottomLeft().y()) && (endPoint.y() < bottomLeft().y()))
		{
			return false;
		}
		
		return true;
	}
	
	
	
	@Override
	public IVector2 nearestPointOutside(final IVector2 point)
	{
		// if point is inside
		// if ((point.x() > topLeft().x()) && (point.x() < topRight().x()) && (point.y() > topLeft().y())
		// && (point.y() < bottomLeft().y()))
		if (isPointInShape(point))
		{
			IVector2 nearestPoint;
			double distance;
			
			// left
			distance = Math.abs(point.x() - topLeft().x());
			nearestPoint = new Vector2(topLeft().x(), point.y());
			
			// right
			if (distance > Math.abs(topRight().x() - point.x()))
			{
				distance = topRight().x() - point.x();
				nearestPoint = new Vector2(topRight().x(), point.y());
			}
			
			// top
			if (distance > Math.abs(point.y() - topLeft().y()))
			{
				distance = point.y() - topLeft().y();
				nearestPoint = new Vector2(point.x(), topLeft().y());
			}
			
			// bottom
			if (distance > Math.abs(bottomLeft().y() - point.y()))
			{
				nearestPoint = new Vector2(point.x(), bottomLeft().y());
			}
			
			return nearestPoint;
		}
		
		// else return point
		return point;
	}
	
	
	
	public IVector2 nearestPointInside(final IVector2 point)
	{
		final Vector2 inside = new Vector2(0, 0);
		// setx
		if (point.x() < topLeft().x())
		{
			inside.setX(topLeft().x());
		} else if (point.x() > (topLeft().x() + xExtend()))
		{
			inside.setX(topLeft().x() + xExtend());
		} else
		{
			inside.setX(point.x());
		}
		
		// sety
		if (point.y() > topLeft().y())
		{
			inside.setY(topLeft().y());
		} else if (point.y() < (topLeft().y() - yExtend()))
		{
			inside.setY(topLeft().y() - yExtend());
		} else
		{
			inside.setY(point.y());
		}
		
		return inside;
	}
	
	
	
	public IVector2 nearestPointInside(final IVector2 point, final double margin)
	{
		IVector2 inside = nearestPointInside(point);
		IVector2 dir = inside.subtractNew(point).normalizeNew();
		return inside.addNew(dir.multiplyNew(margin));
	}
	
	
	
	@Override
	public IVector2 getRandomPointInShape(final Random rnd)
	{
		double x;
		double y;
		
		if (SumatraMath.hasDigitsAfterDecimalPoint(xExtend()) || SumatraMath.hasDigitsAfterDecimalPoint(yExtend()))
		{
			// handle very small rectangles
			x = topLeft().x() + (rnd.nextDouble() * xExtend());
			y = topLeft().y() - (rnd.nextDouble() * yExtend());
			
			x = x - 1;
			
			if (y < 0)
			{
				y = y + 1;
			} else
			{
				y = y - 1;
			}
			
		} else
		{
			
			
			x = topLeft().x() + rnd.nextInt((int) xExtend());
			y = topLeft().y() - rnd.nextInt((int) yExtend());
		}
		
		return new Vector2(x, y);
	}
	
	
	@Override
	public List<Line> getEdges()
	{
		List<Line> lines = new ArrayList<Line>(4);
		
		lines.add(Line.newLine(topLeft(), bottomLeft()));
		lines.add(Line.newLine(bottomLeft(), bottomRight()));
		lines.add(Line.newLine(bottomRight(), topRight()));
		lines.add(Line.newLine(topRight(), topLeft()));
		
		return lines;
	}
	
	
	@Override
	public List<IVector2> lineIntersections(final ILine line)
	{
		List<IVector2> allIntersectionPoints = new ArrayList<IVector2>(4);
		
		List<IVector2> results = new ArrayList<IVector2>();
		
		List<Line> edges = getEdges();
		for (Line edge : edges)
		{
			try
			{
				allIntersectionPoints.add(GeoMath.intersectionPoint(edge, line));
			} catch (MathException ex)
			{
			}
		}
		
		for (IVector2 p : allIntersectionPoints)
		{
			if (!isPointInShape(p))
			{
				continue;
			}
			
			if (!line.isPointInFront(p))
			{
				continue;
			}
			
			results.add(p);
		}
		
		return results;
	}
	
	
	@Override
	public IVector2 getDistantIntersectionPoint(final ILine line) throws MathException
	{
		List<IVector2> points = lineIntersections(line);
		
		if (points.isEmpty())
		{
			throw new MathException("No intersection found");
		}
		
		if (points.size() == 1)
		{
			return points.get(0);
		}
		
		IVector2 a = points.get(0);
		IVector2 b = points.get(1);
		
		if (line.supportVector().subtractNew(a).getLength2() < line.supportVector().subtractNew(b).getLength2())
		{
			return b;
		}
		
		return a;
	}
	
	
	@Override
	public IVector2 getNearIntersectionPoint(final ILine line) throws MathException
	{
		List<IVector2> points = lineIntersections(line);
		
		if (points.isEmpty())
		{
			throw new MathException("No intersection found");
		}
		
		if (points.size() == 1)
		{
			return points.get(0);
		}
		
		IVector2 a = points.get(0);
		IVector2 b = points.get(1);
		
		if (line.supportVector().subtractNew(a).getLength2() < line.supportVector().subtractNew(b).getLength2())
		{
			return a;
		}
		
		return b;
	}
	
}
