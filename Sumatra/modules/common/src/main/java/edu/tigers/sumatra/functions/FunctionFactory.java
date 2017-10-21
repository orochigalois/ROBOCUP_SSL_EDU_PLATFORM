/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.functions;

import org.apache.commons.lang.StringUtils;

import com.github.g3force.instanceables.InstanceableClass.NotCreateableException;



public final class FunctionFactory
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	private FunctionFactory()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static IFunction1D createFunctionFromString(final String text)
	{
		String[] parts = text.split(":");
		
		if (parts.length != 2)
		{
			throw new IllegalArgumentException("No splitting ':' found in text");
		}
		
		final String doubles[] = parts[1].split(";");
		final double params[] = new double[doubles.length];
		for (int i = 0; i < doubles.length; i++)
		{
			params[i] = Double.parseDouble(doubles[i]);
		}
		
		for (EFunction ef : EFunction.values())
		{
			if (ef.getId().equals(parts[0]))
			{
				try
				{
					return (IFunction1D) ef.getInstanceableClass().newInstance(params);
				} catch (NotCreateableException err)
				{
					throw new IllegalArgumentException("Could not instantiate class: " + text, err);
				}
			}
		}
		
		throw new IllegalArgumentException("Unknown function");
	}
	
	
	
	public static String createStringFromFunction(final IFunction1D function)
	{
		if (function == null)
		{
			return "";
		}
		String text = function.getIdentifier().getId() + ":";
		
		text += StringUtils.join(function.getParameters(), ";");
		
		return text;
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
