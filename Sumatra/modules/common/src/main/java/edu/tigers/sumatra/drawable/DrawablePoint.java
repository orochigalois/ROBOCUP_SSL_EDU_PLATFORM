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

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2f;



@Persistent
public class DrawablePoint extends Vector2f implements IDrawableShape
{
	
	private static int	pointSize	= 25;
	private Color			color			= Color.red;
	private String			text			= "";
	
	
	@SuppressWarnings("unused")
	private DrawablePoint()
	{
		
	}
	
	
	
	public DrawablePoint(final IVector2 point, final Color color)
	{
		super(point);
		this.color = color;
	}
	
	
	
	public DrawablePoint(final IVector2 point)
	{
		super(point);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		// --- from SSLVision-mm to java2d-coordinates ---
		final IVector2 transPoint = tool.transformToGuiCoordinates(this, invert);
		int guiPointSize = tool.scaleXLength(pointSize);
		
		final int drawingX = (int) transPoint.x() - (guiPointSize / 2);
		final int drawingY = (int) transPoint.y() - (guiPointSize / 2);
		
		g.setColor(getColor());
		g.fillOval(drawingX, drawingY, guiPointSize, guiPointSize);
		g.drawString(text, drawingX, drawingY);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
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
	
	
	
	public final String getText()
	{
		return text;
	}
	
	
	
	public final void setText(final String text)
	{
		this.text = text;
	}
	
	
	
	public void setSize(final int size)
	{
		pointSize = size;
	}
}
