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

import static edu.tigers.sumatra.math.AVector2.X_AXIS;
import static edu.tigers.sumatra.math.AVector2.Y_AXIS;
import static edu.tigers.sumatra.math.AVector2.ZERO_VECTOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;



public class GeoMathTestV2
{
	
	private static final Random	RANDOM_GENERATOR		= new Random();
	private static final double	TEST_ACCURANCY			= 1e-4;
	private static final double	TEST_ACCURANCY_SQR	= TEST_ACCURANCY * TEST_ACCURANCY;
																		
																		
	
	private static final double normalizeAngle(final double angle)
	{
		double newAngle = angle;
		while (newAngle <= -Math.PI)
		{
			newAngle += 2 * Math.PI;
		}
		while (newAngle > Math.PI)
		{
			newAngle -= 2 * Math.PI;
		}
		return newAngle;
	}
	
	
	private Vector2 createRandomVector(final double scale)
	{
		return new Vector2(scale * RANDOM_GENERATOR.nextDouble(), scale * RANDOM_GENERATOR.nextDouble());
	}
	
	
	
	@Test
	public void testDistancePP()
	{
		// 1)
		assertEquals(1, GeoMath.distancePP(X_AXIS, ZERO_VECTOR), TEST_ACCURANCY);
		assertEquals(1, GeoMath.distancePP(Y_AXIS, ZERO_VECTOR), TEST_ACCURANCY);
		assertEquals(Math.sqrt(2), GeoMath.distancePP(X_AXIS, Y_AXIS), TEST_ACCURANCY);
		
		// 2)
		for (double i = 0; i < (2 * Math.PI); i += 0.1)
		{
			final double spiralCoeff = i + 1e-3;
			IVector2 p = new Vector2(spiralCoeff * Math.cos(i), spiralCoeff * Math.sin(i));
			assertEquals(spiralCoeff, GeoMath.distancePP(ZERO_VECTOR, p), TEST_ACCURANCY_SQR);
		}
		
		// 3)
		for (int i = 0; i <= 10; ++i)
		{
			Vector2 a = createRandomVector(Math.pow(10, (i - 5)));
			Vector2 b = createRandomVector((i - 5));
			double distance = GeoMath.distancePP(a, b);
			assertEquals(Math.sqrt(((b.x() - a.x()) * (b.x() - a.x())) + ((b.y() - a.y()) * (b.y() - a.y()))), distance,
					TEST_ACCURANCY_SQR);
		}
		
	}
	
	
	
	@Test
	public void testDistancePPSqr()
	{
		
		final IVector2 testpoints[][] = {
				{ createRandomVector(1e5), createRandomVector(1e5) },
				{ createRandomVector(1e4), createRandomVector(1e4) },
				{ createRandomVector(1e3), createRandomVector(1e3) },
				{ createRandomVector(1e2), createRandomVector(1e2) },
				{ createRandomVector(1e1), createRandomVector(1e1) },
				{ X_AXIS, ZERO_VECTOR },
				{ Y_AXIS, ZERO_VECTOR },
				{ X_AXIS, Y_AXIS },
				{ createRandomVector(1e-1), createRandomVector(1e-1) },
				{ createRandomVector(1e-2), createRandomVector(1e-2) },
				{ createRandomVector(1e-3), createRandomVector(1e-3) },
				{ createRandomVector(1e-4), createRandomVector(1e-4) },
				{ createRandomVector(1e-5), createRandomVector(1e-5) },
				{ createRandomVector(1e-6), createRandomVector(1e-6) }
		};
		
		for (IVector2[] pnts : testpoints)
		{
			final double euclDis = GeoMath.distancePP(pnts[0], pnts[1]);
			assertEquals(Math.sqrt(GeoMath.distancePPSqr(pnts[0], pnts[1])), euclDis,
					TEST_ACCURANCY_SQR);
		}
		
		
	}
	
	
	
	@Test
	public void testDistancePL()
	{
		List<ILine> lines = new ArrayList<ILine>();
		List<Double> idx = new ArrayList<Double>();
		for (double i = 0; i < (2 * Math.PI); i += 0.1)
		{
			idx.add(i);
			final double spiralCoeff = i + 1e-3;
			IVector2 p2 = new Vector2(spiralCoeff * Math.cos(i), spiralCoeff * Math.sin(i));
			lines.add(new Line(ZERO_VECTOR, p2));
		}
		for (int i = 0; i < idx.size(); ++i)
		{
			final double scalefactor = idx.get(i) * Math.pow(10, idx.get(i) - Math.PI);
			final IVector2 scaledX = X_AXIS.multiplyNew(scalefactor);
			final IVector2 scaledY = Y_AXIS.multiplyNew(scalefactor);
			final double distanceX = GeoMath.distancePL(scaledX, lines.get(i));
			final double distanceY = GeoMath.distancePL(scaledY, lines.get(i));
			assertEquals(Math.abs(scalefactor * Math.sin(idx.get(i))), distanceX, TEST_ACCURANCY_SQR);
			assertEquals(Math.abs(scalefactor * Math.cos(idx.get(i))), distanceY, TEST_ACCURANCY_SQR);
			
			final double distanceX2 = GeoMath.distancePL(scaledX, ZERO_VECTOR, lines.get(i).directionVector());
			final double distanceY2 = GeoMath.distancePL(scaledY, ZERO_VECTOR, lines.get(i).directionVector());
			assertEquals(Math.abs(scalefactor * Math.sin(idx.get(i))), distanceX2, TEST_ACCURANCY_SQR);
			assertEquals(Math.abs(scalefactor * Math.cos(idx.get(i))), distanceY2, TEST_ACCURANCY_SQR);
			
			assertTrue(distanceX == distanceX2);
			assertTrue(distanceY == distanceY2);
		}
	}
	
	
	
	@Test
	public void testLeadPointOnLine()
	{
		
		ILine line = Line.newLine(ZERO_VECTOR, X_AXIS);
		for (double i = 0.0; i < 10; i += 0.1)
		{
			IVector2 point = new Vector2(i * i, Math.pow(10, i - 5));
			IVector2 expected = new Vector2(i * i, 0);
			
			assertTrue(expected.equals(GeoMath.leadPointOnLine(point, line), TEST_ACCURANCY_SQR));
			assertTrue(expected.equals(GeoMath.leadPointOnLine(point, line.supportVector(), line.directionVector()),
					TEST_ACCURANCY_SQR));
		}
		
		line = Line.newLine(ZERO_VECTOR, Y_AXIS);
		for (double i = 0.0; i < 10; i += 0.1)
		{
			IVector2 point = new Vector2(Math.pow(10, i - 5), i * i);
			IVector2 expected = new Vector2(0, i * i);
			
			assertTrue(expected.equals(GeoMath.leadPointOnLine(point, line), TEST_ACCURANCY_SQR));
			assertTrue(expected.equals(GeoMath.leadPointOnLine(point, line.supportVector(), line.directionVector()),
					TEST_ACCURANCY_SQR));
		}
		// three complete unit-circle runs
		for (double i = 0; i < (3 * (2 * Math.PI)); i += 0.1)
		{
			final double spiralCoeff = i + 1e-3;
			IVector2 linepoint = new Vector2(spiralCoeff * Math.cos(i), spiralCoeff * Math.sin(i));
			IVector2 point = new Vector2(spiralCoeff * Math.cos(i + (Math.PI / 2)),
					spiralCoeff * Math.sin(i + (Math.PI / 2)));
			line = Line.newLine(ZERO_VECTOR, linepoint);
			assertTrue(ZERO_VECTOR.equals(GeoMath.leadPointOnLine(point, line), TEST_ACCURANCY_SQR));
			assertTrue(ZERO_VECTOR.equals(GeoMath.leadPointOnLine(point, line.supportVector(), line.directionVector()),
					TEST_ACCURANCY_SQR));
					
		}
	}
	
	
	
	@Test
	public void testAngleBetweenObjects()
	{
		double angle = Math.PI - (Math.PI / 180);
		assertEquals(angle, GeoMath.angleBetweenVectorAndVector(X_AXIS,
				new Vector2(Math.cos(angle), Math.sin(angle))), TEST_ACCURANCY);
		angle = normalizeAngle(angle + (Math.PI / 180));
		assertEquals(angle, GeoMath.angleBetweenVectorAndVector(X_AXIS,
				new Vector2(Math.cos(angle), Math.sin(angle))), TEST_ACCURANCY);
		angle += normalizeAngle(angle + (Math.PI / 180));
		assertEquals(angle, GeoMath.angleBetweenVectorAndVector(X_AXIS,
				new Vector2(Math.cos(angle), Math.sin(angle))), TEST_ACCURANCY);
				
				
		for (double i = 0; i < (2 * Math.PI); i += 0.01)
		{
			final double rawAngle = i;
			IVector2 pointOnUnitCircle = new Vector2(Math.cos(i), Math.sin(i));
			final double expectedAngleAbs = Math.abs(normalizeAngle(rawAngle));
			final double expectedAngleNeg = normalizeAngle(rawAngle);
			final double resultVV = GeoMath.angleBetweenVectorAndVector(X_AXIS, pointOnUnitCircle);
			// final double resultVVN = GeoMath.angleBetweenVectorAndVectorWithNegative(X_AXIS, pointOnUnitCircle);
			final double resultXALL = GeoMath.angleBetweenXAxisAndLine(Line.newLine(ZERO_VECTOR, pointOnUnitCircle));
			final double resultXALVV = GeoMath.angleBetweenXAxisAndLine(ZERO_VECTOR, pointOnUnitCircle);
			
			
			assertEquals(expectedAngleAbs, resultVV, TEST_ACCURANCY_SQR);
			// assertEquals(expectedAngleNeg, resultVVN, TEST_ACCURANCY_SQR);
			// TODO: check if method is correct or why assert fails
			assertEquals(expectedAngleNeg, resultXALL, TEST_ACCURANCY_SQR);
			assertEquals(expectedAngleNeg, resultXALVV, TEST_ACCURANCY_SQR);
			
			
		}
	}
	
	
	
	@Test
	public void testBisector()
	{
		for (double i = 0; i < (2 * Math.PI); i += 0.01)
		{
			final double spiralCoeff = Math.pow(10, i - (0.5 * Math.PI));
			IVector2 p1 = new Vector2(spiralCoeff * Math.cos(i), spiralCoeff * Math.sin(i));
			IVector2 p2 = new Vector2(spiralCoeff * Math.cos(i + 0.5), spiralCoeff * Math.sin(i + 0.5));
			IVector2 e = new Vector2(p1.x() + (0.5 * (p2.x() - p1.x())), p1.y() + (0.5 * (p2.y() - p1.y())));
			final IVector calculated = GeoMath.calculateBisector(ZERO_VECTOR, p1, p2);
			assertTrue(calculated.equals(e, TEST_ACCURANCY));
		}
	}
	
	
	
	@Test
	public void testIntersectionPointLineHalfLine()
	{
		final IVector2 d1 = new Vector2(1, 1);
		final IVector2 d2 = new Vector2(-1, 1);
		final ILine halfLine = new Line(ZERO_VECTOR, d1);
		for (int i = 0; i < 1000; ++i)
		{
			final IVector2 s2 = d1.multiplyNew(i);
			final ILine line = new Line(s2, d2);
			final IVector2 result = GeoMath.intersectionPointLineHalfLine(line, halfLine);
			assertTrue(s2.equals(result, TEST_ACCURANCY));
		}
	}
	
	
}
