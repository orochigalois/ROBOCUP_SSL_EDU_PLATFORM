/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer.view;

import edu.tigers.sumatra.wp.data.ShapeMap.IShapeLayer;



public enum EVisualizerOptions implements IShapeLayer
{
	
	FANCY("Visualizer", "fancy drawing"),
	
	TURN_NEXT("Visualizer", "horizontal field"),
	
	RESET_FIELD("Visualizer", "reset field"),
	
	PAINT_COORD("Visualizer", "paint coord."),
	
	YELLOW_AI("Visualizer", "Yellow AI"),
	
	BLUE_AI("Visualizer", "Blue AI"),;
	
	
	private final String	name;
	private final String	category;
								
								
	
	private EVisualizerOptions(final String category, final String name)
	{
		this.name = name;
		this.category = category;
	}
	
	
	
	@Override
	public final String getLayerName()
	{
		return name;
	}
	
	
	@Override
	public String getCategory()
	{
		return category;
	}
	
	
	@Override
	public String getId()
	{
		return name();
	}
	
	
	@Override
	public boolean isVisibleByDefault()
	{
		return true;
	}
}
