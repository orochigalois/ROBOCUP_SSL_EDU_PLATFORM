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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.shapes.circle.ICircle;



@Persistent(version = 0)
public class DrawableCircle extends Circle implements IDrawableShape
{
	private Color		color	= Color.red;
	private boolean	fill	= false;
	
	
	
	@SuppressWarnings("unused")
	private DrawableCircle()
	{
		super(new Circle(AVector2.ZERO_VECTOR, 1));
	}
	
	
	
	public DrawableCircle(final ICircle circle)
	{
		super(circle);
	}
	
	
	
	public DrawableCircle(final ICircle circle, final Color color)
	{
		super(circle);
		this.color = color;
	}
	
	
	
	public DrawableCircle(final IVector2 center, final double radius, final Color color)
	{
		super(center, radius);
		this.color = color;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		// --- from SSLVision-mm to java2d-coordinates ---
		final IVector2 center = tool.transformToGuiCoordinates(center(), invert);
		final double radius = tool.scaleXLength(radius());
		
		g.setColor(getColor());
		g.drawOval((int) (center.x() - radius), (int) (center.y() - radius), (int) radius * 2, (int) radius * 2);
		if (fill)
		{
			g.fillOval((int) (center.x() - radius), (int) (center.y() - radius), (int) radius * 2, (int) radius * 2);
		}
	}
	
	
	
	public Color getColor()
	{
		// this may happen with old databases
		if (color == null)
		{
			// standard color
			return Color.red;
		}
		return color;
	}
	
	
	
	@Override
	public void setColor(final Color color)
	{
		this.color = color;
	}
	
	
	
	public void setFill(final boolean fill)
	{
		this.fill = fill;
	}
}
