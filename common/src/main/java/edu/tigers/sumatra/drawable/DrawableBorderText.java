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
public class DrawableBorderText implements IDrawableShape
{
	private final IVector2	pos;
	private final String		text;
	private final Color		color;
	private int					fontSize	= 5;
	
	
	@SuppressWarnings("unused")
	private DrawableBorderText()
	{
		pos = AVector2.ZERO_VECTOR;
		text = "";
		color = Color.red;
	}
	
	
	
	public DrawableBorderText(final IVector2 pos, final String text, final Color color)
	{
		this.pos = pos;
		this.text = text;
		this.color = color;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		g.scale(1f / tool.getScaleFactor(), 1.0 / tool.getScaleFactor());
		g.translate(-tool.getFieldOriginX(), -tool.getFieldOriginY());
		
		final IVector2 transPoint = pos;
		int pointSize = 3;
		final int drawingX = (int) transPoint.x() - (pointSize / 2);
		final int drawingY = (int) transPoint.y() - (pointSize / 2);
		
		Font font = new Font("", Font.PLAIN, fontSize);
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, drawingX, drawingY);
		
		g.translate(tool.getFieldOriginX(), tool.getFieldOriginY());
		g.scale(tool.getScaleFactor(), tool.getScaleFactor());
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
