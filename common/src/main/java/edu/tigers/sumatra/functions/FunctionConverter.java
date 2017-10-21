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

import org.apache.log4j.Logger;

import com.github.g3force.s2vconverter.IString2ValueConverter;



public class FunctionConverter implements IString2ValueConverter
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FunctionConverter.class.getName());
	
	
	@Override
	public boolean supportedClass(final Class<?> impl)
	{
		return impl.equals(IFunction1D.class);
	}
	
	
	@Override
	public Object parseString(final Class<?> impl, final String value)
	{
		try
		{
			return FunctionFactory.createFunctionFromString(value);
		} catch (IllegalArgumentException err)
		{
			log.warn("The function " + value
					+ " could not be parsed correctly and was replaced by a constant 0 function.", err);
			return Function1dPoly.constant(0);
		}
	}
	
	
	@Override
	public String toString(final Class<?> impl, final Object value)
	{
		return FunctionFactory.createStringFromFunction((IFunction1D) value);
	}
}
