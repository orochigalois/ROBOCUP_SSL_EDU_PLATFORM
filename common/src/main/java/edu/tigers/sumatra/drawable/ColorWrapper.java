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
import java.io.Serializable;

import com.sleepycat.persist.model.Persistent;



@Persistent(version = 1)
public class ColorWrapper implements Serializable
{
	
	private static final long	serialVersionUID	= 952878454594587069L;
	private transient Color		color;
	private int						colorValue;
	private int						colorAlpha;
	
	
	@SuppressWarnings("unused")
	private ColorWrapper()
	{
		color = null;
	}
	
	
	
	public ColorWrapper(final Color color)
	{
		setColor(color);
	}
	
	
	
	public final Color getColor()
	{
		if (color == null)
		{
			color = new Color(colorValue);
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha);
		}
		return color;
	}
	
	
	
	public final void setColor(final Color color)
	{
		this.color = color;
		colorValue = color.getRGB();
		colorAlpha = color.getAlpha();
	}
}
