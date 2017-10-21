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
import java.awt.Rectangle;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.ellipse.Ellipse;
import edu.tigers.sumatra.shapes.ellipse.IEllipse;



@Persistent(version = 1)
public class DrawableEllipse extends Ellipse implements IDrawableShape
{
	private Color		color	= Color.red;
	private boolean	fill	= false;
	
	
	@SuppressWarnings("unused")
	private DrawableEllipse()
	{
		super(AVector2.ZERO_VECTOR, 1, 1);
	}
	
	
	
	public DrawableEllipse(final IEllipse ellipse, final Color color)
	{
		super(ellipse);
		this.color = color;
	}
	
	
	
	public DrawableEllipse(final IEllipse ellipse)
	{
		super(ellipse);
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		// this may happen with old databases
		if (color == null)
		{
			// standard color
			g.setColor(Color.red);
		} else
		{
			g.setColor(color);
		}
		
		Rectangle r = getBounds(tool, invert);
		
		g.rotate(-getTurnAngle(), r.x + (r.width / 2.0), r.y + (r.height / 2.0));
		g.drawOval(r.x, r.y, r.width, r.height);
		if (fill)
		{
			g.fillOval(r.x, r.y, r.width, r.height);
		}
		g.rotate(getTurnAngle(), r.x + (r.width / 2.0), r.y + (r.height / 2.0));
	}
	
	
	private Rectangle getBounds(final IDrawableTool fieldPanel, final boolean invert)
	{
		IVector2 center = fieldPanel.transformToGuiCoordinates(getCenter(), invert);
		int x = (int) (center.x() - fieldPanel.scaleXLength(getRadiusY()));
		int y = (int) (center.y() - fieldPanel.scaleYLength(getRadiusX()));
		int width = fieldPanel.scaleXLength(getRadiusY()) * 2;
		int height = fieldPanel.scaleYLength(getRadiusX()) * 2;
		return new Rectangle(x, y, width, height);
	}
	
	
	
	public void setFill(final boolean fill)
	{
		this.fill = fill;
	}
	
	
	@Override
	public void setColor(final Color color)
	{
		this.color = color;
	}
}
