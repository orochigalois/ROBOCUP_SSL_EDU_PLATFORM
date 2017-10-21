/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.ValuePoint;



@Persistent
public class DrawableValuePoints implements IDrawableShape
{
	private List<ValuePoint>	points;
	private final int				radius	= 15;
	
	
	@SuppressWarnings("unused")
	private DrawableValuePoints()
	{
	}
	
	
	
	public DrawableValuePoints(final List<ValuePoint> distancePoints)
	{
		points = new ArrayList<>();
		points.addAll(distancePoints);
		
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		
		IVector2 tempPos;
		for (ValuePoint point : points)
		{
			tempPos = tool.transformToGuiCoordinates(point.getXYVector(), invert);
			int colorVal = (int) (255. * point.value);
			g.setColor(
					new Color(colorVal % 256, colorVal % 256, colorVal % 256, 150));
			g.fillOval((int) tempPos.x() - (radius / 2), (int) tempPos.y() - (radius / 2), radius, radius);
			g.setColor(Color.RED);
			g.drawString(Double.toString(point.value), (int) tempPos.x(), (int) tempPos.y());
			// g.drawChars(Double.toString(point.value).toCharArray(), 0, 15, (int) tempPos.x(), (int) tempPos.y());
			// g.fillOval((int) tempPos.x() - (radius / 2), (int) tempPos.y() - (radius / 2), radius, radius);
		}
	}
}
