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

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;



@Persistent
public class DrawableText implements IDrawableShape
{
	private final IVector2	pos;
	private final String		text;
	private final Color		color;
	private int					fontSize	= 10;
	
	
	@SuppressWarnings("unused")
	private DrawableText()
	{
		pos = AVector2.ZERO_VECTOR;
		text = "";
		color = Color.red;
	}
	
	
	
	public DrawableText(final IVector2 pos, final String text, final Color color)
	{
		this.pos = pos;
		this.text = text;
		this.color = color;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		final IVector2 transPoint = tool.transformToGuiCoordinates(pos, invert);
		int pointSize = 3;
		final int drawingX = (int) transPoint.x() - (pointSize / 2);
		int drawingY = (int) transPoint.y() - (pointSize / 2);
		
		Font font = new Font("", Font.PLAIN, fontSize);
		g.setFont(font);
		g.setColor(color);
		for (String txt : text.split("\n"))
		{
			g.drawString(txt, drawingX, drawingY += g.getFontMetrics(font).getHeight());
		}
	}
	
	
	
	public final int getFontSize()
	{
		return fontSize;
	}
	
	
	
	public final void setFontSize(final int fontSize)
	{
		this.fontSize = fontSize;
	}
}
