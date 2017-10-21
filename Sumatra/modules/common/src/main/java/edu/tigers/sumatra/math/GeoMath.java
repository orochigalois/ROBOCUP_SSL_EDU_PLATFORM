/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.math;

import java.util.List;

import org.apache.log4j.Logger;



public final class GeoMath
{
	private static final Logger	log		= Logger.getLogger(GeoMath.class.getName());
														
	private static final double	ACCURACY	= 1e-3;
														
														
	
	private static class LowLevel
	{
		
		private LowLevel()
		{
			throw new RuntimeException("instance is forbidden");
		}
		
		
		
		public static double getLineIntersectionLambda(final IVector2 supp1, final IVector2 dir1, final IVector2 supp2,
				final IVector2 dir2)
		{
			final double s1 = supp1.x();
			final double s2 = supp1.y();
			final double d1 = dir1.x();
			final double d2 = dir1.y();
			
			final double x1 = supp2.x();
			final double x2 = supp2.y();
			final double r1 = dir2.x();
			final double r2 = dir2.y();
			
			
			final double detRS = (r1 * s2) - (r2 * s1);
			final double detRX = (r1 * x2) - (r2 * x1);
			final double detDR = (d1 * r2) - (d2 * r1);
			
			if (Math.abs(detDR) < (ACCURACY * ACCURACY))
			{
				throw new RuntimeException(
						"the two lines are parallel! Should not happen but when it does tell KaiE as this means there might be a bug");
			}
			return (detRS - detRX) / detDR;
		}
		
		
		
		public static Vector2 getPointOnLineForLambda(final IVector2 s, final IVector2 d, final double lambda)
		{
			
			final double xcut = s.x() + (d.x() * lambda);
			final double ycut = s.y() + (d.y() * lambda);
			return new Vector2(xcut, ycut);
		}
		
		
		
		public static boolean isLineParallel(final IVector2 s1, final IVector2 d1, final IVector2 s2, final IVector2 d2)
		{
			return (Math.abs((d1.x() * d2.y()) - (d2.x() * d1.y())) < (ACCURACY * ACCURACY));
		}
		
		
		
		public static boolean isLambdaInRange(final double lambda, final double min, final double max)
		{
			return (((min - ACCURACY) < lambda) && (lambda < (max + ACCURACY)));
		}
		
		
		
		public static double getLeadPointLambda(final IVector2 point, final IVector2 supp, final IVector2 dir)
		{
			
			final IVector2 ortho = new Vector2(dir.y(), -dir.x());
			if (LowLevel.isLineParallel(supp, dir, point, ortho))
			{
				return 0;
			}
			
			return getLineIntersectionLambda(supp, dir, point, ortho);
		}
		
	}
	
	
	
	private GeoMath()
	{
	}
	
	
	
	public static double distancePP(final IVector2 a, final IVector2 b)
	{
		return Math.sqrt(distancePPSqr(a, b));
	}
	
	
	
	public static double distancePPSqr(final IVector2 a, final IVector2 b)
	{
		final double abX = a.x() - b.x();
		final double abY = a.y() - b.y();
		return (abX * abX) + (abY * abY);
	}
	
	
	
	public static double distancePL(final IVector2 point, final IVector2 line1, final IVector2 line2)
	{
		return distancePP(point, leadPointOnLine(point, line1, line2));
	}
	
	
	
	public static double distancePL(final IVector2 point, final ILine line)
	{
		return distancePP(point, leadPointOnLine(point, line));
	}
	
	
	
	public static Vector2 leadPointOnLine(final IVector2 point, final IVector2 line1, final IVector2 line2)
	{
		return leadPointOnLine(point, Line.newLine(line1, line2));
	}
	
	
	
	public static Vector2 leadPointOnLine(final IVector2 point, final ILine line)
	{
		final IVector2 sline = line.supportVector();
		final IVector2 dline = line.directionVector();
		
		
		return LowLevel.getPointOnLineForLambda(sline, dline, LowLevel.getLeadPointLambda(point, sline, dline));
		
	}
	
	
	
	public static double angleBetweenXAxisAndLine(final IVector2 p1, final IVector2 p2)
	{
		return angleBetweenXAxisAndLine(Line.newLine(p1, p2));
	}
	
	
	
	public static double angleBetweenXAxisAndLine(final ILine l)
	{
		return l.directionVector().getAngle();
	}
	
	
	
	public static double angleBetweenVectorAndVector(final IVector2 v1, final IVector2 v2)
	{
		// The old version was numerically unstable, this one works better
		return Math.abs(angleBetweenVectorAndVectorWithNegative(v1, v2));
	}
	
	
	
	public static double angleBetweenVectorAndVectorWithNegative(final IVector2 v1, final IVector2 v2)
	{
		// angle between positive x-axis and first vector
		final double angleA = Math.atan2(v1.x(), v1.y());
		// angle between positive x-axis and second vector
		final double angleB = Math.atan2(v2.x(), v2.y());
		// rotation
		double rotation = angleB - angleA;
		// fix overflows
		if (rotation < (-Math.PI - ACCURACY))
		{
			rotation += 2 * Math.PI;
		} else if (rotation > (Math.PI + ACCURACY))
		{
			rotation -= 2 * Math.PI;
		}
		return rotation;
	}
	
	
	
	public static Vector2 calculateBisector(final IVector2 p1, final IVector2 p2, final IVector2 p3)
	{
		if (p1.equals(p2) || p1.equals(p3))
		{
			return new Vector2(p1);
		}
		if (p2.equals(p3))
		{
			return new Vector2(p2);
		}
		final Vector2 p1p2 = p2.subtractNew(p1);
		final Vector2 p1p3 = p3.subtractNew(p1);
		final Vector2 p3p2 = p2.subtractNew(p3);
		
		p3p2.scaleTo(p3p2.getLength2() / ((p1p2.getLength2() / p1p3.getLength2()) + 1));
		p3p2.add(p3);
		
		return p3p2;
	}
	
	
	
	
	public static double triangleDistance(final IVector2 p1, final IVector2 p2, final IVector2 p3, final IVector2 x)
	{
		ILine ball2LeftPost = Line.newLine(p1, p2);
		ILine ball2RightPost = Line.newLine(p1, p3);
		ILine defenseLine = new Line(x, x.subtractNew(p1).turnNew(AngleMath.PI_HALF));
		
		IVector2 defenseLineLeft = x;
		IVector2 defenseLineRight = x;
		try
		{
			defenseLineLeft = GeoMath.intersectionPoint(ball2LeftPost, defenseLine);
			defenseLineRight = GeoMath.intersectionPoint(ball2RightPost, defenseLine);
		} catch (MathException err)
		{
			log.warn("This should not happen!", err);
		}
		
		return GeoMath.distancePP(defenseLineLeft, defenseLineRight);
	}
	
	
	
	public static boolean isLineParallel(final ILine l1, final ILine l2)
	{
		return LowLevel.isLineParallel(l1.supportVector(), l1.directionVector(), l2.supportVector(),
				l2.directionVector());
	}
	
	
	
	public static double incompleteDistanceBetweenLineSegments(final IVector2 l1p1, final IVector2 l1p2,
			final IVector2 l2p1,
			final IVector2 l2p2)
					throws MathException
	{
		
		
		final IVector2 dir1 = l1p2.subtractNew(l1p1);
		final IVector2 dir2 = l2p2.subtractNew(l2p1);
		
		if ((dir1.getLength2() + dir2.getLength2()) < (ACCURACY))
		{
			return distancePP(l1p1, l2p1);
		}
		try
		{
			final double lambda = LowLevel.getLineIntersectionLambda(l1p1, dir1, l2p1, dir2);
			final double delta = LowLevel.getLineIntersectionLambda(l2p1, dir2, l1p1, dir1);
			
			if (LowLevel.isLambdaInRange(lambda, 0, 1) && LowLevel.isLambdaInRange(delta, 0, 1))
			{
				return 0;
			}
			final IVector2 closestEndPointToIntersectionA = lambda < (0.5 + ACCURACY) ? l1p1 : l1p2;
			final IVector2 closestEndPointToIntersectionB = delta < (0.5 + ACCURACY) ? l2p1 : l2p2;
			final double lambdaLead = LowLevel.getLeadPointLambda(closestEndPointToIntersectionB, l1p1, dir1);
			final double deltaLead = LowLevel.getLeadPointLambda(closestEndPointToIntersectionA, l2p1, dir2);
			double distanceLineAFromB = 0;
			if (LowLevel.isLambdaInRange(deltaLead, 0, 1))
			{
				distanceLineAFromB = distancePP(closestEndPointToIntersectionA,
						LowLevel.getPointOnLineForLambda(l2p1, dir2, deltaLead));
			} else
			{
				final IVector2 closestEndPointToLead = deltaLead <= (0.5 + ACCURACY) ? l2p1 : l2p2;
				distanceLineAFromB = distancePP(closestEndPointToIntersectionA, closestEndPointToLead);
			}
			double distanceLineBFromA = 0;
			
			if (LowLevel.isLambdaInRange(lambdaLead, 0, 1))
			{
				distanceLineBFromA = distancePP(closestEndPointToIntersectionB,
						LowLevel.getPointOnLineForLambda(l1p1, dir1, lambdaLead));
			} else
			{
				final IVector2 closestEndPointToLead = lambdaLead <= (0.5 + ACCURACY) ? l1p1 : l1p2;
				distanceLineBFromA = distancePP(closestEndPointToIntersectionB, closestEndPointToLead);
			}
			return Math.min(distanceLineAFromB, distanceLineBFromA);
			
		} catch (RuntimeException ex)
		{
			throw new MathException("lines were parallel with direction " + dir1 + " and " + dir2);
		}
		
		
	}
	
	
	
	public static double distanceBetweenLineSegments(final IVector2 l1p1, final IVector2 l1p2, final IVector2 l2p1,
			final IVector2 l2p2)
					throws MathException
	{
		// line crossing
		IVector2 lc = null;
		// special cases: one or both lines are points
		if (l1p1.equals(l1p2) && l2p1.equals(l2p2))
		{
			return distancePP(l1p1, l2p1);
		} else if (l1p1.equals(l1p2))
		{
			lc = leadPointOnLine(l1p1, new Line(l2p1, l2p2.subtractNew(l2p1)));
		} else if (l2p1.equals(l2p2))
		{
			lc = leadPointOnLine(l2p1, new Line(l1p1, l1p2.subtractNew(l1p1)));
		} else
		{
			// the normal case: both lines are real lines
			lc = GeoMath.intersectionPoint(l1p1, l1p2.subtractNew(l1p1), l2p1,
					l2p2.subtractNew(l2p1));
		}
		
		// limit to line segments
		IVector2 nearestPointToCrossingForLineSegement1 = new Vector2(lc);
		if (ratio(l1p1, lc, l1p2) > 1)
		{
			nearestPointToCrossingForLineSegement1 = new Vector2(l1p2);
		}
		if ((ratio(l1p2, lc, l1p1) > 1)
				&& ((ratio(l1p1, lc, l1p2) < 1) || (ratio(l1p2, lc, l1p1) < ratio(l1p1, lc, l1p2))))
		{
			nearestPointToCrossingForLineSegement1 = new Vector2(l1p1);
		}
		
		IVector2 nearestPointToCrossingForLineSegement2 = new Vector2(lc);
		if (ratio(l2p1, lc, l2p2) > 1)
		{
			nearestPointToCrossingForLineSegement2 = new Vector2(l2p2);
		}
		if ((ratio(l2p2, lc, l2p1) > 1)
				&& ((ratio(l2p1, lc, l2p2) < 1) || (ratio(l2p2, lc, l2p1) < ratio(l2p1, lc, l2p2))))
		{
			nearestPointToCrossingForLineSegement2 = new Vector2(l2p1);
		}
		return nearestPointToCrossingForLineSegement2.subtractNew(nearestPointToCrossingForLineSegement1).getLength2();
	}
	
	
	
	public static IVector2 nearestPointOnLineSegment(final IVector2 l1p1, final IVector2 l1p2, final IVector2 point)
	{
		final IVector2 dir = l1p2.subtractNew(l1p1);
		final double lambda = LowLevel.getLeadPointLambda(point, l1p1, dir);
		if (LowLevel.isLambdaInRange(lambda, 0, 1))
		{
			return LowLevel.getPointOnLineForLambda(l1p1, dir, lambda);
		}
		final double dist1 = distancePPSqr(l1p1, point);
		final double dist2 = distancePPSqr(l1p2, point);
		return dist1 < dist2 ? l1p1 : l1p2;
	}
	
	
	
	public static double ratio(final IVector2 root, final IVector2 point1, final IVector2 point2)
	{
		if (point2.equals(root))
		{
			// ratio is inifinite
			return Double.MAX_VALUE;
		}
		return (point1.subtractNew(root).getLength2() / point2.subtractNew(root).getLength2());
	}
	
	
	
	public static Vector2 intersectionPoint(final IVector2 p1, final IVector2 v1, final IVector2 p2, final IVector2 v2)
			throws MathException
	{
		try
		{
			final double lambda = LowLevel.getLineIntersectionLambda(p1, v1, p2, v2);
			return LowLevel.getPointOnLineForLambda(p1, v1, lambda);
		} catch (RuntimeException ex)
		{
			throw new MathException("lines were parallel!");
		}
	}
	
	
	
	public static Vector2 intersectionPoint(final ILine l1, final ILine l2) throws MathException
	{
		return intersectionPoint(l1.supportVector(), l1.directionVector(), l2.supportVector(), l2.directionVector());
	}
	
	
	
	public static Vector2 intersectionPointPath(final IVector2 p1, final IVector2 v1, final IVector2 p2,
			final IVector2 v2)
	{
		
		if (LowLevel.isLineParallel(p1, v1, p2, v2))
		{
			return null;
		}
		try
		{
			final double lambda = LowLevel.getLineIntersectionLambda(p1, v1, p2, v2);
			final double delta = LowLevel.getLineIntersectionLambda(p2, v2, p1, v1);
			if (LowLevel.isLambdaInRange(lambda, 0, 1) && LowLevel.isLambdaInRange(delta, 0, 1))
			{
				return LowLevel.getPointOnLineForLambda(p1, v1, lambda);
			}
		} catch (RuntimeException e)
		{
			log.error("Exception with parameter: p1=" + p1 + "v1=" + v1 + "p2=" + p2 + "v2=" + v2, e);
			// no operation
		}
		return null;
		
		
	}
	
	
	
	public static IVector2 intersectionBetweenPaths(final IVector2 p1p1, final IVector2 p1p2, final IVector2 p2p1,
			final IVector2 p2p2)
	{
		IVector2 intersectionPoint = intersectionPointPath(p1p1, p1p2.subtractNew(p1p1), p2p1, p2p2.subtractNew(p2p1));
		return intersectionPoint;
	}
	
	
	
	public static IVector2 intersectionPointLinePath(final ILine line, final IVector2 pPath,
			final IVector2 vPath)
	{
		final IVector2 pLine = line.supportVector();
		final IVector2 vLine = line.directionVector();
		if (LowLevel.isLineParallel(pLine, vLine, pPath, vPath))
		{
			return null;
		}
		try
		{
			final double lambda = LowLevel.getLineIntersectionLambda(pPath, vPath, pLine, vLine);
			if (LowLevel.isLambdaInRange(lambda, 0, 1))
			{
				return LowLevel.getPointOnLineForLambda(pPath, vPath, lambda);
			}
		} catch (RuntimeException e)
		{
			log.error("Exception with parameter: pLine=" + pLine + "vLine=" + vLine + "pPath=" + pPath + "vPath=" + vPath,
					e);
			// no operation
		}
		return null;
		
	}
	
	
	
	public static IVector2 intersectionPointLineHalfLine(final ILine line, final ILine halfLine)
	{
		if (isLineParallel(line, halfLine))
		{
			return null;
		}
		try
		{
			final IVector2 hLS = halfLine.supportVector();
			final IVector2 hLD = halfLine.directionVector();
			final IVector2 lS = line.supportVector();
			final IVector2 lD = line.directionVector();
			final double lambda = LowLevel.getLineIntersectionLambda(hLS, hLD, lS, lD);
			if (lambda > -(ACCURACY * ACCURACY))
			{
				return LowLevel.getPointOnLineForLambda(hLS, hLD, lambda);
			}
		} catch (RuntimeException ex)
		{
			// catch to avoid runtime exception
			log.error("Exception with parameter: line=" + line + "halfLine=" + halfLine, ex);
		}
		return null;
	}
	
	
	
	public static IVector2 intersectionPointHalfLinePath(final ILine halfLine, final IVector2 pP, final IVector2 pD)
	{
		if (isLineParallel(new Line(pP, pD), halfLine))
		{
			return null;
		}
		try
		{
			final IVector2 hLS = halfLine.supportVector();
			final IVector2 hLD = halfLine.directionVector();
			final double lambda = LowLevel.getLineIntersectionLambda(hLS, hLD, pP, pD);
			final double delta = LowLevel.getLineIntersectionLambda(pP, pD, hLS, hLD);
			if ((lambda > -(ACCURACY * ACCURACY)) && LowLevel.isLambdaInRange(delta, 0, 1))
			{
				return LowLevel.getPointOnLineForLambda(hLS, hLD, lambda);
			}
		} catch (RuntimeException ex)
		{
			// catch to avoid runtime exception
			log.error("Exception with parameter: path=" + new Line(pP, pD) + "halfLine=" + halfLine, ex);
		}
		return null;
	}
	
	
	
	public static boolean isPointOnPath(final ILine line, final IVector2 point)
	{
		IVector2 lp = GeoMath.leadPointOnLine(point, line);
		if (GeoMath.distancePP(point, lp) < ACCURACY)
		{
			return isVectorBetween(point, line.supportVector(), line.supportVector().addNew(line.directionVector()));
		}
		return false;
	}
	
	
	
	public static boolean isVectorBetween(final IVector2 point, final IVector2 min, final IVector2 max)
	{
		return (SumatraMath.isBetween(point.x(), min.x(), max.x()) && SumatraMath.isBetween(point.y(), min.y(), max.y()));
	}
	
	
	
	public static double yInterceptOfLine(final IVector2 point, final double slope)
	{
		return (point.y() - (slope * point.x()));
	}
	
	
	
	public static boolean isLineInterceptingCircle(final IVector2 center, final double radius, final double slope,
			final double yIntercept)
	{
		// based on equation of cirle and line
		// trying to intercept leads to a quadratic-equation
		// p-q-equation is used
		// point of interception doesn't matter --> checks only if value in sqrt is >= 0 (i.e. equation is solvable, i.e.
		// is intercepting
		
		final double p = (((-2 * center.x()) + (2 * slope * yIntercept)) - (2 * center.y() * slope))
				/ (1.0 + (slope * slope));
		final double q = (((((center.x() * center.x()) + (yIntercept * yIntercept)) - (2 * center.y() * yIntercept))
				+ (center
						.y() * center.y()))
				- (radius * radius))
				/ (1.0 + (slope * slope));
				
		if ((((p * p) / 4.0) - q) >= 0)
		{
			// yepp, is intercepting
			return true;
		}
		// nope, not intercepting
		return false;
	}
	
	
	
	public static Vector2 stepAlongCircle(final IVector2 current, final IVector2 center, final double angle)
	{
		
		final double x = (((current.x() - center.x()) * Math.cos(angle)) - ((current.y() - center.y()) * Math
				.sin(angle))) + center.x();
		final double y = ((current.x() - center.x()) * Math.sin(angle))
				+ ((current.y() - center.y()) * Math.cos(angle)) + center.y();
				
		return new Vector2(x, y);
	}
	
	
	
	public static double distancePPCircle(final IVector2 center, final IVector2 p1, final IVector2 p2)
	{
		IVector2 c2p1 = p1.subtractNew(center);
		IVector2 c2p2 = p2.subtractNew(center);
		double angle = angleBetweenVectorAndVector(c2p1, c2p2);
		double radius = GeoMath.distancePP(p1, center);
		double u = 2 * radius * AngleMath.PI;
		return (angle / AngleMath.PI_TWO) * u;
	}
	
	
	
	public static Vector2 stepAlongLine(final IVector2 start, final IVector2 end, final double stepSize)
	{
		final Vector2 result = new Vector2();
		
		final double distanceSqr = distancePPSqr(start, end);
		if (distanceSqr == 0)
		{
			result.setX(end.x());
			result.setY(end.y());
			return result;
		}
		
		final double distance = Math.sqrt(distanceSqr);
		final double coefficient = stepSize / distance;
		
		final double xDistance = end.x() - start.x();
		final double yDistance = end.y() - start.y();
		
		
		result.setX((xDistance * coefficient) + start.x());
		result.setY((yDistance * coefficient) + start.y());
		if (Double.isNaN(result.x()) || Double.isNaN(result.y()))
		{
			log.fatal("stepAlongLine: result contains NaNs. Very dangerous!!");
			final String seperator = " / ";
			log.fatal(start.toString() + seperator + end.toString() + seperator + distance + seperator + coefficient
					+ seperator + xDistance + seperator + yDistance + seperator + result.toString());
		}
		return result;
	}
	
	
	
	public static int checkQuadrant(final IVector2 position)
	{
		if ((position.x() >= 0) && (position.y() >= 0))
		{
			return 1;
		} else if ((position.x() < 0) && (position.y() > 0))
		{
			return 2;
		} else if ((position.x() <= 0) && (position.y() <= 0))
		{
			return 3;
		} else
		{
			return 4;
		}
		
	}
	
	
	
	public static IVector2 nearestPointInList(final List<IVector2> list, final IVector2 p)
	{
		if (list.isEmpty())
		{
			return p;
		}
		IVector2 closest = null;
		double closestDist = Double.MAX_VALUE;
		for (IVector2 vec : list)
		{
			double dist = GeoMath.distancePPSqr(vec, p);
			if (closestDist > dist)
			{
				closestDist = dist;
				closest = vec;
			}
		}
		return closest;
	}
	
	
	
	public static Vector2 convertLocalBotVector2Global(final IVector2 local, final double wpAngle)
	{
		return local.turnNew(-AngleMath.PI_HALF + wpAngle);
	}
	
	
	
	public static Vector2 convertGlobalBotVector2Local(final IVector2 global, final double wpAngle)
	{
		return global.turnNew(AngleMath.PI_HALF - wpAngle);
	}
	
	
	
	public static double convertGlobalBotAngle2Local(final double angle)
	{
		return AngleMath.PI_HALF - angle;
	}
	
	
	
	public static double convertLocalBotAngle2Global(final double angle)
	{
		return -AngleMath.PI_HALF + angle;
	}
	
	
	
	public static IVector2 getBotKickerPos(final IVector2 botPos, final double orientation, final double center2Dribbler)
	{
		
		return botPos.addNew(new Vector2(orientation).scaleTo(center2Dribbler));
	}
}
