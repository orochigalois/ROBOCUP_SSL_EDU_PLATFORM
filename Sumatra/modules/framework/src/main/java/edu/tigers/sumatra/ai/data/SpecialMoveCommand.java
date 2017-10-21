/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;



@Persistent
public class SpecialMoveCommand
{
	
	private List<IVector2>	movePosition			= new ArrayList<IVector2>();
	private List<Double>		moveTimes				= new ArrayList<Double>();
	// if response Step == 0, dont send any response
	private int					responseStep			= 0;
	private double				forceResponseTime		= 10.0;
	private double				timeUntilPassArrives	= 0.0;
	
	
	
	public List<IVector2> getMovePosition()
	{
		return movePosition;
	}
	
	
	
	public void setMovePosition(final List<IVector2> movePosition)
	{
		this.movePosition = movePosition;
	}
	
	
	
	public List<Double> getMoveTimes()
	{
		return moveTimes;
	}
	
	
	
	public void setMoveTimes(final List<Double> moveTimes)
	{
		this.moveTimes = moveTimes;
	}
	
	
	
	public double getForceResponseTime()
	{
		return forceResponseTime;
	}
	
	
	
	public void setForceResponseTime(final double forceResponseTime)
	{
		this.forceResponseTime = forceResponseTime;
	}
	
	
	
	public int getResponseStep()
	{
		return responseStep;
	}
	
	
	
	public void setResponseStep(final int responseStep)
	{
		this.responseStep = responseStep;
	}


	
	public double getTimeUntilPassArrives()
	{
		return timeUntilPassArrives;
	}


	
	public void setTimeUntilPassArrives(double timeUntilPassArrives)
	{
		this.timeUntilPassArrives = timeUntilPassArrives;
	}
}