/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.Color;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PersistentProxy;



@Persistent(proxyFor = Color.class)
public class ColorProxy implements PersistentProxy<Color>
{
	private ColorWrapper wrapper = null;
	
	
	@Override
	public Color convertProxy()
	{
		if (wrapper != null)
		{
			return wrapper.getColor();
		}
		return Color.BLACK;
	}
	
	
	@Override
	public void initializeProxy(final Color arg0)
	{
		wrapper = new ColorWrapper(arg0);
	}
	
}
