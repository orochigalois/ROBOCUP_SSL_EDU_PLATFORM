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
import java.awt.Font;
import java.awt.Graphics2D;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;



@Persistent
public class DrawableAnnotation implements IDrawableShape
{
	private final IVector2	center;
	private final String		text;
									
	private double				margin	= 0;
	private ELocation			location	= ELocation.TOP;
	private Color				color		= Color.BLACK;
	private int					fontSize	= 10;
	private transient Font	font		= null;
												
	
	public enum ELocation
	{
		
		TOP,
		
		BOTTOM,
		
		LEFT,
		
		RIGHT
	}
	
	
	@SuppressWarnings("unused")
	private DrawableAnnotation()
	{
		center = null;
		text = null;
	}
	
	
	
	public DrawableAnnotation(final IVector2 center, final String text)
	{
		super();
		this.center = center;
		this.text = text;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		if (font == null)
		{
			font = new Font("", Font.PLAIN, fontSize);
		}
		g.setFont(font);
		g.setColor(color);
		
		final IVector2 transPoint = tool.transformToGuiCoordinates(center, invert);
		
		String[] lines = text.split("\n");
		int numLines = lines.length;
		double maxWidth = 0;
		for (String line : lines)
		{
			maxWidth = Math.max(maxWidth, g.getFontMetrics(font).getStringBounds(line, g).getWidth());
		}
		double lineHeight = g.getFontMetrics(font).getHeight();
		double textHeight = lineHeight * numLines;
		
		double drawingX;
		double drawingY;
		switch (location)
		{
			case BOTTOM:
				drawingX = transPoint.x() - (maxWidth / 2.0);
				drawingY = transPoint.y() + tool.scaleYLength(margin);
				break;
			case LEFT:
				drawingX = transPoint.x() - maxWidth - tool.scaleXLength(margin);
				drawingY = transPoint.y() - (textHeight / 2.0) - (lineHeight / 2.0);
				break;
			case RIGHT:
				drawingX = transPoint.x() + tool.scaleXLength(margin);
				drawingY = transPoint.y() - (textHeight / 2.0) - (lineHeight / 2.0);
				break;
			case TOP:
				drawingX = transPoint.x() - (maxWidth / 2.0);
				drawingY = transPoint.y() - tool.scaleYLength(margin) - textHeight;
				break;
			default:
				return;
		}
		
		for (String txt : lines)
		{
			g.drawString(txt, (float) drawingX, (float) (drawingY += lineHeight));
		}
	}
	
	
	
	public final double getMargin()
	{
		return margin;
	}
	
	
	
	public final void setMargin(final double margin)
	{
		this.margin = margin;
	}
	
	
	
	public final ELocation getLocation()
	{
		return location;
	}
	
	
	
	public final void setLocation(final ELocation location)
	{
		this.location = location;
	}
	
	
	
	public final Color getColor()
	{
		return color;
	}
	
	
	
	@Override
	public final void setColor(final Color color)
	{
		this.color = color;
	}
	
	
	
	public final int getFontSize()
	{
		return fontSize;
	}
	
	
	
	public final void setFontSize(final int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	
	
	public final IVector2 getCenter()
	{
		return center;
	}
	
	
	
	public final String getText()
	{
		return text;
	}
	
}
