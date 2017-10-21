/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam.data;

import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.shapes.circle.ICircle;



public class CamFieldArc extends Circle
{
	private final String	name;
	private final double	thickness;
	private final double	startAngle, endAngle;
	
	
	
	public CamFieldArc(final ICircle circle, final String name, final double thickness, final double startAngle,
			final double endAngle)
	{
		super(circle);
		this.name = name;
		this.thickness = thickness;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}
	
	
	
	public final String getName()
	{
		return name;
	}
	
	
	
	public final double getThickness()
	{
		return thickness;
	}
	
	
	
	public final double getStartAngle()
	{
		return startAngle;
	}
	
	
	
	public final double getEndAngle()
	{
		return endAngle;
	}
}
