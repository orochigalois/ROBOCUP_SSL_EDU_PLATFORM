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

import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.Line;



public class CamFieldLine extends Line
{
	private final String	name;
	private final double	thickness;
	
	
	
	public CamFieldLine(final ILine line, final String name, final double thickness)
	{
		super(line);
		this.name = name;
		this.thickness = thickness;
	}
	
	
	
	public final String getName()
	{
		return name;
	}
	
	
	
	public final double getThickness()
	{
		return thickness;
	}
}
