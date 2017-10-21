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

import java.util.LinkedList;
import java.util.List;

import edu.tigers.sumatra.ai.sisyphus.errt.TuneableParameter;



public class ParameterDebugger
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private final List<PathInformation>	configurations			= new LinkedList<PathInformation>();
	private PathInformation					currentlyTested;
	private boolean							testing					= false;
	private long								calculationStarted	= 0;
																				
																				
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ParameterDebugger()
	{
		currentlyTested = new PathInformation(new TuneableParameter());
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void calculationStarted()
	{
		if (testing)
		{
			calculationStarted = System.nanoTime();
		}
	}
	
	
	
	public void calculationFinished()
	{
		if (testing)
		{
			currentlyTested.addCalculation(System.nanoTime() - calculationStarted);
		}
	}
	
	
	
	public void goalReached()
	{
		if (testing)
		{
			currentlyTested.goalReached();
		}
	}
	
	
	
	public void ramboChosen()
	{
		if (testing)
		{
			currentlyTested.ramboChosen();
		}
	}
	
	
	
	public void secondTry()
	{
		if (testing)
		{
			currentlyTested.secondTry();
		}
	}
	
	
	
	@Override
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		for (final PathInformation pi : configurations)
		{
			output.append(pi.toString());
			output.append("\n");
		}
		return output.toString();
	}
	
	
	
	public void changeParameterConfigToTest(final TuneableParameter adjustableParams)
	{
		configurations.add(currentlyTested);
		final PathInformation newCurrentlyTested = new PathInformation(adjustableParams);
		currentlyTested = newCurrentlyTested;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public boolean isTesting()
	{
		return testing;
	}
	
	
	
	public void setTesting(final boolean testing)
	{
		this.testing = testing;
	}
}
