/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.math;

import org.apache.log4j.Logger;

import com.github.g3force.s2vconverter.IString2ValueConverter;



public class VectorConverter implements IString2ValueConverter
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(VectorConverter.class.getName());
	
	
	@Override
	public boolean supportedClass(final Class<?> impl)
	{
		return impl.equals(IVector.class)
				|| impl.equals(IVector2.class)
				|| impl.equals(IVector3.class)
				|| impl.equals(IVectorN.class);
	}
	
	
	@Override
	public Object parseString(final Class<?> impl, final String value)
	{
		if (impl.equals(IVector2.class))
		{
			try
			{
				return Vector2.valueOf(value);
			} catch (NumberFormatException err)
			{
				log.error("Could not parse vector.", err);
				return AVector2.ZERO_VECTOR;
			}
		} else if (impl.equals(IVector3.class))
		{
			try
			{
				return Vector3.valueOf(value);
			} catch (NumberFormatException err)
			{
				log.error("Could not parse vector.", err);
				return AVector3.ZERO_VECTOR;
			}
		} else if (impl.equals(IVectorN.class) || impl.equals(IVector.class))
		{
			try
			{
				return AVector.valueOf(value);
			} catch (NumberFormatException err)
			{
				log.error("Could not parse vector.", err);
				return new VectorN(0);
			}
		}
		return null;
	}
	
	
	@Override
	public String toString(final Class<?> impl, final Object value)
	{
		IVector vec = (IVector) value;
		return vec.getSaveableString();
	}
	
}
