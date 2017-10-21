/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.neural;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationTANH;



public class ActivationFunctionFactory
{
	
	public static ActivationFunction create()
	{
		return new ActivationTANH();
	}
	
	
	
	public static double getNormalizedHigh()
	{
		return 1;
	}
	
	
	
	public static double getNormalizedLow()
	{
		return -1;
	}
}
