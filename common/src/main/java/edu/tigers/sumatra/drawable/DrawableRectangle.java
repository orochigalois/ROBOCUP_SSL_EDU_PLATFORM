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
import edu.tigers.sumatra.shapes.rectangle.IRectangle;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;



@Persistent(version = 1)
public class DrawableRectangle extends Rectangle implements IDrawableShape
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
	private DrawableRectangle()
	{
		super(new Rectangle(AVector2.ZERO_VECTOR, new Vector2(1, 1)));
	}
	
	
	
	public DrawableRectangle(final IRectangle rec)
	{
		super(rec);
		setColor(Color.red);
	}
	
	
	
	public DrawableRectangle(final IRectangle rec, final Color color)
	{
		super(rec);
		setColor(color);
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		// --- from SSLVision-mm to java2d-coordinates ---
		final IVector2 topLeft = tool.transformToGuiCoordinates(topLeft(), invert);
		final IVector2 bottomRight = tool.transformToGuiCoordinates(bottomRight(), invert);
		
		int x = (int) (topLeft.x() < bottomRight.x() ? topLeft.x() : bottomRight.x());
		int y = (int) (topLeft.y() < bottomRight.y() ? topLeft.y() : bottomRight.y());
		
		int width = Math.abs((int) (bottomRight.x() - topLeft.x()));
		int height = Math.abs((int) (bottomRight.y() - topLeft.y()));
		
		g.setColor(getColor());
		g.drawRect(x, y, width, height);
		if (fill)
		{
			g.fillRect(x, y, width, height);
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
