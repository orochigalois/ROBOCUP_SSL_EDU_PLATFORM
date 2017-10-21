/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.bot;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;
import com.sleepycat.persist.model.Persistent;



@Persistent
@Deprecated
public class Performance
{
	
	
	@Configurable(spezis = { "" }, defValue = "3")
	private double	accMax			= 0;
	@Configurable(spezis = { "" }, defValue = "4")
	private double	brkMax			= 0;
	
	@Configurable(spezis = { "" }, defValue = "3")
	private double	velMax			= 0;
	
	@Configurable(spezis = { "" }, defValue = "30")
	private double	accMaxW			= 0;
	@Configurable(spezis = { "" }, defValue = "30")
	private double	brkMaxW			= 0;
	
	@Configurable(spezis = { "" }, defValue = "10")
	private double	velMaxW			= 0;
	
	private double	velMaxOverride	= 0;
	private double	accMaxOverride	= 0;
	
	
	static
	{
		ConfigRegistration.registerClass("botmgr", Performance.class);
	}
	
	
	
	public Performance()
	{
		ConfigRegistration.applySpezis(this, "botmgr", "");
	}
	
	
	
	public final double getAccMax()
	{
		if (accMaxOverride > 0)
		{
			return accMaxOverride;
		}
		return accMax;
	}
	
	
	
	public final double getAccMaxW()
	{
		return accMaxW;
	}
	
	
	
	public final double getBrkMax()
	{
		if (accMaxOverride > 0)
		{
			return accMaxOverride;
		}
		return brkMax;
	}
	
	
	
	public final double getBrkMaxW()
	{
		return brkMaxW;
	}
	
	
	
	public final double getVelMax()
	{
		if (velMaxOverride > 0)
		{
			return velMaxOverride;
		}
		return velMax;
	}
	
	
	
	public final double getVelMaxW()
	{
		return velMaxW;
	}
	
	
	
	public final void setAccMax(final double accMax)
	{
		this.accMax = accMax;
	}
	
	
	
	public final void setAccMaxW(final double accMaxW)
	{
		this.accMaxW = accMaxW;
	}
	
	
	
	public final void setBrkMax(final double brkMax)
	{
		this.brkMax = brkMax;
	}
	
	
	
	public final void setBrkMaxW(final double brkMaxW)
	{
		this.brkMaxW = brkMaxW;
	}
	
	
	
	public final void setVelMax(final double velMax)
	{
		this.velMax = velMax;
	}
	
	
	
	public final void setVelMaxW(final double velMaxW)
	{
		this.velMaxW = velMaxW;
	}
	
	
	
	public final double getVelMaxOverride()
	{
		return velMaxOverride;
	}
	
	
	
	public final void setVelMaxOverride(final double velMaxOverride)
	{
		this.velMaxOverride = velMaxOverride;
	}
	
	
	
	public double getAccMaxOverride()
	{
		return accMaxOverride;
	}
	
	
	
	public void setAccMaxOverride(final double accMaxOverride)
	{
		this.accMaxOverride = accMaxOverride;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getAccMax());
		builder.append(",");
		builder.append(getBrkMax());
		builder.append(",");
		builder.append(getVelMax());
		builder.append(",");
		builder.append(getAccMaxW());
		builder.append(",");
		builder.append(getBrkMaxW());
		builder.append(",");
		builder.append(getVelMaxW());
		return builder.toString();
	}
}
