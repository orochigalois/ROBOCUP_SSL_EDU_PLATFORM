/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

import javax.swing.border.Border;



public class RoundedCornerBorder implements Border, Serializable
{
	
	
	private static final long	serialVersionUID	= 713546174759345308L;
	
	private final int				radii;
	private final int				thickness;
	private final Color			borderColor;
	
	
	
	public RoundedCornerBorder(final int radii, final int thickness, final Color borderColor)
	{
		this.radii = radii;
		this.thickness = thickness;
		this.borderColor = borderColor;
	}
	
	
	@Override
	public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width,
			final int height)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		Area borderArea = calcBorderRectangle(x, y, width, height);
		Area outerPaintArea = calcOuterPaintArea(x, y, width, height);
		
		
		Component parent = c.getParent();
		if (parent != null)
		{
			g2.setColor(parent.getBackground());
			g2.fill(outerPaintArea);
		}
		
		
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(borderColor);
		g2.draw(borderArea);
	}
	
	
	private Area calcOuterPaintArea(final int x, final int y, final int width, final int height)
	{
		Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
		Area paintArea = new Area(rect);
		paintArea.subtract(calcBorderRectangle(x, y, width, height));
		return paintArea;
	}
	
	
	private Area calcBorderRectangle(final int x, final int y, final int width, final int height)
	{
		double posOffset = thickness / 2.0f;
		double sizeOffset = thickness;
		
		double xr = x + posOffset;
		double yr = y + posOffset;
		double wr = width - sizeOffset;
		double hr = height - sizeOffset;
		double diameter = radii * 2;
		
		RoundRectangle2D rect = new RoundRectangle2D.Double(xr, yr, wr, hr, diameter, diameter);
		return new Area(rect);
	}
	
	
	@Override
	public Insets getBorderInsets(final Component c)
	{
		int inset = radii + thickness;
		return new Insets(inset, inset, inset, inset);
	}
	
	
	@Override
	public boolean isBorderOpaque()
	{
		return true;
	}
}
