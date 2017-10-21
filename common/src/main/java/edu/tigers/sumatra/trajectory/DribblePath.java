/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.trajectory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.drawable.DrawablePath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public class DribblePath
{
	private final HermiteSplinePart2D	simplePath;
	private IVector2							target;
	private IVector2							offset	= new Vector2(0, 0);
																
																
	
	public DribblePath()
	{
		simplePath = null;
	}
	
	
	
	public DribblePath(final HermiteSplinePart2D path)
	{
		simplePath = path;
	}
	
	
	
	public IVector2 getPosition(final double t)
	{
		return simplePath.getPosition(t).addNew(offset);
	}
	
	
	
	public DrawablePath getDrawablePath(final int iterations, final Color color)
	{
		List<IVector2> points = new ArrayList<IVector2>();
		for (int i = 0; i <= iterations; i++)
		{
			points.add(simplePath.value(i / ((double) iterations)).addNew(offset));
		}
		return new DrawablePath(points, color);
	}
	
	
	
	public IVector2 getTarget()
	{
		return target;
	}
	
	
	
	public void setTarget(final IVector2 target)
	{
		this.target = target;
	}
	
	
	
	public IVector2 getOffset()
	{
		return offset;
	}
	
	
	
	public void setOffset(final IVector2 offset)
	{
		this.offset = offset;
	}
}
