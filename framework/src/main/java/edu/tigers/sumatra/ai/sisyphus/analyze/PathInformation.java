/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.analyze;

import edu.tigers.sumatra.ai.sisyphus.errt.TuneableParameter;



public class PathInformation
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private int								calculationsAmount	= 0;
	private int								pathAmount				= 0;
	private int								ramboAmount				= 0;
	private int								secondTry				= 0;
	private long							midCalculationTime	= 0;
	private long							minCalculationTime	= Long.MAX_VALUE;
	private long							maxCalculationTime	= 0;
	private long							midCompleteTime		= 0;
	private long							minCompleteTime		= Long.MAX_VALUE;
	private long							maxCompleteTime		= 0;
	private long							startTime;
	private final TuneableParameter	accordingParams;
												
												
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public PathInformation(final TuneableParameter accordingParams)
	{
		this.accordingParams = accordingParams;
		startTime = System.nanoTime();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addCalculation(final long neededTime)
	{
		calculationsAmount++;
		if (neededTime < minCalculationTime)
		{
			minCalculationTime = neededTime;
		}
		if (neededTime > maxCalculationTime)
		{
			maxCalculationTime = neededTime;
		}
		midCalculationTime = ((midCalculationTime * (calculationsAmount - 1)) + neededTime) / calculationsAmount;
	}
	
	
	
	public void goalReached()
	{
		pathAmount++;
		final long endTime = System.nanoTime();
		final long completeTime = endTime - startTime;
		if (completeTime < minCompleteTime)
		{
			minCompleteTime = completeTime;
		}
		if (completeTime > maxCompleteTime)
		{
			maxCompleteTime = completeTime;
		}
		midCompleteTime = ((midCompleteTime * (pathAmount - 1)) + completeTime) / pathAmount;
		startTime = System.nanoTime();
	}
	
	
	
	public void ramboChosen()
	{
		ramboAmount++;
	}
	
	
	
	public void secondTry()
	{
		secondTry++;
	}
	
	
	
	@Override
	public String toString()
	{
		String output = "";
		output += accordingParams.toString();
		output += " " + minCalculationTime;
		output += " " + midCalculationTime;
		output += " " + maxCalculationTime;
		output += " " + minCompleteTime;
		output += " " + midCompleteTime;
		output += " " + maxCompleteTime;
		output += " " + calculationsAmount;
		output += " " + secondTry;
		output += " " + ramboAmount;
		return output;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public TuneableParameter getAccordingParams()
	{
		return accordingParams;
	}
	
	
}
