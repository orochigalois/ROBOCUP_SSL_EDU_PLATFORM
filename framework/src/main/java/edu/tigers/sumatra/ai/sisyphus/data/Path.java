/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.path.IPath;



@Persistent
public class Path implements IPath
{
	@SuppressWarnings("unused")
	private static final Logger	log						= Logger.getLogger(Path.class.getName());
																		
	private final List<IVector2>	pathPoints;
	private final double				targetOrientation;
	private List<IVector2>			unsmoothedPathPoints	= new ArrayList<>(0);
																		
																		
	@SuppressWarnings("unused")
	private Path()
	{
		this(Arrays.asList(new IVector2[] { AVector2.ZERO_VECTOR }), 0);
	}
	
	
	
	public Path(final List<IVector2> pathPoints, final double targetOrientation)
	{
		this.pathPoints = new ArrayList<IVector2>(pathPoints);
		this.targetOrientation = targetOrientation;
		if (pathPoints.isEmpty())
		{
			throw new IllegalArgumentException("A path must have at least one node!");
		}
	}
	
	
	
	public Path(final IPath orig)
	{
		this(orig.getPathPoints(), orig.getTargetOrientation());
		unsmoothedPathPoints = orig.getUnsmoothedPathPoints();
	}
	
	
	@Override
	public IVector2 getStart()
	{
		return pathPoints.get(0);
	}
	
	
	@Override
	public IVector2 getEnd()
	{
		return pathPoints.get(pathPoints.size() - 1);
	}
	
	
	@Override
	public double getTargetOrientation()
	{
		return targetOrientation;
	}
	
	
	@Override
	public List<IVector2> getPathPoints()
	{
		return Collections.unmodifiableList(pathPoints);
	}
	
	
	
	@Override
	public List<IVector2> getUnsmoothedPathPoints()
	{
		return unsmoothedPathPoints;
	}
	
	
	
	public void setUnsmoothedPathPoints(final List<IVector2> unsmoothedPathPoints)
	{
		this.unsmoothedPathPoints = unsmoothedPathPoints;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((pathPoints == null) ? 0 : pathPoints.hashCode());
		long temp;
		temp = Double.doubleToLongBits(targetOrientation);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Path other = (Path) obj;
		if (pathPoints == null)
		{
			if (other.pathPoints != null)
			{
				return false;
			}
		} else if (!pathPoints.equals(other.pathPoints))
		{
			return false;
		}
		if (Double.doubleToLongBits(targetOrientation) != Double.doubleToLongBits(other.targetOrientation))
		{
			return false;
		}
		return true;
	}
	
}
