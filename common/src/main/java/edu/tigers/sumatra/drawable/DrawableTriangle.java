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
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.triangle.Triangle;



@Persistent
public class DrawableTriangle extends Triangle implements IDrawableShape
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private Color		color;
	private boolean	fill	= false;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	private DrawableTriangle()
	{
		super(AVector2.ZERO_VECTOR, new Vector2(1, 1), new Vector2(1 - 1));
	}
	
	
	
	public DrawableTriangle(final IVector2 a, final IVector2 b, final IVector2 c)
	{
		super(a, b, c);
		setColor(Color.red);
	}
	
	
	
	public DrawableTriangle(final IVector2 a, final IVector2 b, final IVector2 c, final Color color)
	{
		super(a, b, c);
		setColor(color);
	}
	
	
	
	public DrawableTriangle(final Triangle triangle)
	{
		super(triangle);
	}
	
	
	
	public DrawableTriangle(final Triangle triangle, final Color color)
	{
		super(triangle);
		setColor(color);
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		final IVector2 a = tool.transformToGuiCoordinates(getCorners().get(0), invert);
		final IVector2 b = tool.transformToGuiCoordinates(getCorners().get(1), invert);
		final IVector2 c = tool.transformToGuiCoordinates(getCorners().get(2), invert);
		
		int[] x = { (int) a.x(), (int) b.x(), (int) c.x() };
		int[] y = { (int) a.y(), (int) b.y(), (int) c.y() };
		int number = 3;
		
		g.setColor(getColor());
		g.drawPolygon(x, y, number);
		if (fill)
		{
			g.fillPolygon(x, y, number);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public Color getColor()
	{
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
