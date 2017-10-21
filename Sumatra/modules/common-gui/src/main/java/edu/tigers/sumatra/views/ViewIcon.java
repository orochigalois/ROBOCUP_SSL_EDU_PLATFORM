/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;



public class ViewIcon implements Icon
{
	
	private static final int ICON_SIZE = 8;
	
	
	@Override
	public int getIconHeight()
	{
		return ICON_SIZE;
	}
	
	
	@Override
	public int getIconWidth()
	{
		return ICON_SIZE;
	}
	
	
	@Override
	public void paintIcon(final Component c, final Graphics g, final int x, final int y)
	{
		Color oldColor = g.getColor();
		
		g.setColor(new Color(70, 70, 70));
		g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
		
		g.setColor(new Color(100, 230, 100));
		g.fillRect(x + 1, y + 1, ICON_SIZE - 2, ICON_SIZE - 2);
		
		g.setColor(oldColor);
	}
}
