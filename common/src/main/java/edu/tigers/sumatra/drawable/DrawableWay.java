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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;



@Persistent
public class DrawableWay implements IDrawableShape
{
	private final List<IVector2>	path;
	private Color						color		= Color.red;
	
	private transient Stroke		stroke	= new BasicStroke(3);
	
	
	@SuppressWarnings("unused")
	private DrawableWay()
	{
		path = new ArrayList<>(0);
	}
	
	
	
	public DrawableWay(final Collection<IVector2> path)
	{
		// make sure we have a persistent implementation of List
		this.path = new ArrayList<>(path);
		assert !path.isEmpty();
	}
	
	
	
	public DrawableWay(final Collection<IVector2> path, final Color color)
	{
		this(path);
		this.color = color;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		g.setColor(color);
		g.setStroke(stroke);
		final GeneralPath drawPath = new GeneralPath();
		
		IVector2 startPos = path.get(0);
		final IVector2 transBotPos = tool.transformToGuiCoordinates(startPos, invert);
		final int robotX = (int) transBotPos.x();
		final int robotY = (int) transBotPos.y();
		drawPath.moveTo(robotX, robotY);
		
		for (IVector2 point : path)
		{
			final IVector2 transPathPoint = tool.transformToGuiCoordinates(point, invert);
			// g.drawOval((int) transPathPoint.x() - 1, (int) transPathPoint.y() - 1, 3, 3);
			drawPath.lineTo((int) transPathPoint.x(), (int) transPathPoint.y());
		}
		g.draw(drawPath);
	}
	
}
