/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.triangle;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



public class TriangleTest
{
	
	
	@Test
	public void testIsPointInShapeWithMargin()
	{
		double margin = 0.5d;
		Vector2 a = new Vector2(0.0, 0.0);
		Vector2 b = new Vector2(1.0, 0.0);
		Vector2 c = new Vector2(0.0, 1.0);
		
		Triangle triangle = new Triangle(a, b, c);
		
		testCornersWithMargin(triangle, margin);
	}
	
	
	
	private void testCornersWithMargin(final Triangle triangle, final double margin)
	{
		List<IVector2> corners = triangle.getCorners();
		for (int i = 0; i < corners.size(); i++)
		{
			IVector2 corner = corners.get(i);
			IVector2 otherCorner = corners.get((i + 1) % 3);
			Vector2 edgePoint = corner.addNew(otherCorner.subtractNew(corner).scaleTo(margin * 0.999));
			
			for (int j = 0; j < 100; j++)
			{
				IVector2 curPoint = GeoMath.stepAlongCircle(edgePoint, corner, Math.toRadians((j * 360.0d) / 100.0d));
				assertTrue(triangle.isPointInShape(curPoint, margin));
			}
		}
	}
}
