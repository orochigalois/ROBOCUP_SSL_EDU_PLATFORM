/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;



public class BotCenterTreeNode extends DefaultMutableTreeNode
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long		serialVersionUID	= 1110743948837617965L;
	
	private String						title;
	private final ETreeIconType	icon;
	private final Component			userComponent;
	private Color						color;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BotCenterTreeNode(final String title, final ETreeIconType type, final Component component,
			final boolean useScrollbar)
	{
		this(title, type, Color.black, component, useScrollbar);
	}
	
	
	
	public BotCenterTreeNode(final String title, final ETreeIconType type, final Color color, final Component component,
			final boolean useScrollbar)
	{
		super(title);
		this.title = title;
		icon = type;
		if (useScrollbar)
		{
			userComponent = new JScrollPane(component);
		} else
		{
			userComponent = component;
		}
		this.color = color;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public String toString()
	{
		return title;
	}
	
	
	
	public ETreeIconType getIconType()
	{
		return icon;
	}
	
	
	
	public Component getUserComponent()
	{
		return userComponent;
	}
	
	
	
	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	
	
	public final String getTitle()
	{
		return title;
	}
	
	
	
	public final Color getColor()
	{
		return color;
	}
	
	
	
	public final void setColor(final Color color)
	{
		this.color = color;
	}
}
