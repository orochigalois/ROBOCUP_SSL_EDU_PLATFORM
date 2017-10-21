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

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class SplineTrajectory<ReturnType> implements ITrajectory<ReturnType>
{
	private final List<SplinePart<ReturnType>>	parts			= new ArrayList<SplinePart<ReturnType>>();
	private double											totalTime	= 0;
																				
	
	@Persistent(version = 1)
	public static class SplinePart<ReturnType>
	{
		
		public double							startTime;
		
		public double							endTime;
		
		public ITrajectory<ReturnType>	spline;
													
													
		@SuppressWarnings("unused")
		private SplinePart()
		{
		}
		
		
		
		public SplinePart(final double sT, final double eT, final ITrajectory<ReturnType> p)
		{
			startTime = sT;
			endTime = eT;
			spline = p;
		}
		
		
		
		public SplinePart(final SplinePart<ReturnType> part)
		{
			startTime = part.startTime;
			endTime = part.endTime;
			spline = part.spline;
		}
	}
	
	
	@SuppressWarnings("unused")
	private SplineTrajectory()
	{
	}
	
	
	
	public SplineTrajectory(final List<ITrajectory<ReturnType>> splines)
	{
		init(splines);
	}
	
	
	
	public SplineTrajectory(final ITrajectory<ReturnType> spline)
	{
		List<ITrajectory<ReturnType>> list = new ArrayList<ITrajectory<ReturnType>>();
		list.add(spline);
		
		init(list);
	}
	
	
	
	public SplineTrajectory(final SplineTrajectory<ReturnType> position)
	{
		for (SplinePart<ReturnType> part : position.parts)
		{
			parts.add(new SplinePart<ReturnType>(part));
		}
		totalTime = position.totalTime;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private void init(final List<ITrajectory<ReturnType>> splines)
	{
		parts.clear();
		totalTime = 0;
		
		for (ITrajectory<ReturnType> p : splines)
		{
			parts.add(new SplinePart<ReturnType>(totalTime, totalTime + p.getTotalTime(), p));
			totalTime += p.getTotalTime();
		}
		assert Double.isFinite(totalTime);
	}
	
	
	
	public void append(final SplineTrajectory<ReturnType> traj)
	{
		List<ITrajectory<ReturnType>> list = new ArrayList<>();
		
		// add original parts
		for (SplinePart<ReturnType> part : parts)
		{
			list.add(part.spline);
		}
		
		// append new parts
		for (SplinePart<ReturnType> part : traj.parts)
		{
			list.add(part.spline);
		}
		
		// and re-init :)
		init(list);
	}
	
	
	
	public int findPart(final double t)
	{
		for (int i = 0; i < parts.size(); i++)
		{
			SplinePart<ReturnType> p = parts.get(i);
			
			if ((t >= p.startTime) && (t < p.endTime))
			{
				return i;
			}
		}
		
		return parts.size() - 1;
	}
	
	
	@Override
	public ReturnType getPositionMM(final double t)
	{
		SplinePart<ReturnType> p = parts.get(findPart(t));
		
		return p.spline.getPositionMM(t - p.startTime);
	}
	
	
	@Override
	public ReturnType getPosition(final double t)
	{
		SplinePart<ReturnType> p = parts.get(findPart(t));
		
		return p.spline.getPosition(t - p.startTime);
	}
	
	
	@Override
	public ReturnType getVelocity(final double t)
	{
		SplinePart<ReturnType> p = parts.get(findPart(t));
		
		return p.spline.getVelocity(t - p.startTime);
	}
	
	
	@Override
	public ReturnType getAcceleration(final double t)
	{
		SplinePart<ReturnType> p = parts.get(findPart(t));
		
		return p.spline.getAcceleration(t - p.startTime);
	}
	
	
	@Override
	public double getTotalTime()
	{
		return totalTime;
	}
	
	
	
	public int getNumParts()
	{
		return parts.size();
	}
	
	
	
	public SplinePart<ReturnType> getPart(final int part)
	{
		return parts.get(part);
	}
	
	
	
	public ITrajectory<ReturnType> getSpline(final int part)
	{
		return parts.get(part).spline;
	}
}
