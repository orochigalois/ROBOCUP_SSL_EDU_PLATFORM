/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import com.github.g3force.s2vconverter.IString2ValueConverter;



public class DynamicPositionConverter implements IString2ValueConverter
{
	
	@Override
	public boolean supportedClass(final Class<?> clazz)
	{
		return clazz.equals(DynamicPosition.class);
	}
	
	
	@Override
	public Object parseString(final Class<?> impl, final String value)
	{
		return DynamicPosition.valueOf(value);
	}
	
	
	@Override
	public String toString(final Class<?> impl, final Object value)
	{
		DynamicPosition pos = (DynamicPosition) value;
		return pos.getSaveableString();
	}
	
}
