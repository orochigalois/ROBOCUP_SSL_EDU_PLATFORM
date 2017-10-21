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
public class MoveConstraints
{
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "3.5", "3" })
	private double						velMax	= 3.5;
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "10", "10" })
	private double						velMaxW	= 10;
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "3", "3" })
	private double						accMax	= 3;
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "25", "25" })
	private double						accMaxW	= 25;
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "50", "50" })
	private double						jerkMax	= 50;
	@Configurable(spezis = { "", "v2013" }, defValueSpezis = { "500", "500" })
	private double						jerkMaxW	= 500;
	
	private final MoveConstraints	defConstraints;
	
	static
	{
		ConfigRegistration.registerClass("botmgr", MoveConstraints.class);
	}
	
	
	
	public MoveConstraints()
	{
		defConstraints = null;
	}
	
	
	
	public MoveConstraints(final MoveConstraints o)
	{
		velMax = o.velMax;
		velMaxW = o.velMaxW;
		accMax = o.accMax;
		accMaxW = o.accMaxW;
		jerkMax = o.jerkMax;
		jerkMaxW = o.jerkMaxW;
		defConstraints = o;
	}
	
	
	
	public double getVelMax()
	{
		return velMax;
	}
	
	
	
	public void setVelMax(final double velMax)
	{
		this.velMax = velMax;
	}
	
	
	
	public double getVelMaxW()
	{
		return velMaxW;
	}
	
	
	
	public void setVelMaxW(final double velMaxW)
	{
		this.velMaxW = velMaxW;
	}
	
	
	
	public double getAccMax()
	{
		return accMax;
	}
	
	
	
	public void setAccMax(final double accMax)
	{
		this.accMax = accMax;
	}
	
	
	
	public double getAccMaxW()
	{
		return accMaxW;
	}
	
	
	
	public void setAccMaxW(final double accMaxW)
	{
		this.accMaxW = accMaxW;
	}
	
	
	
	public MoveConstraints getDefConstraints()
	{
		return defConstraints;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(velMax);
		builder.append(",");
		builder.append(velMaxW);
		builder.append("|");
		builder.append(accMax);
		builder.append(",");
		builder.append(accMaxW);
		builder.append("|");
		builder.append(jerkMax);
		builder.append(",");
		builder.append(jerkMaxW);
		return builder.toString();
	}
	
	
	
	public void setDefaultVelLimit()
	{
		if (defConstraints != null)
		{
			velMax = defConstraints.velMax;
		}
	}
	
	
	
	public void setDefaultAccLimit()
	{
		if (defConstraints != null)
		{
			accMax = defConstraints.accMax;
		}
	}
	
	
	
	public double getJerkMax()
	{
		return jerkMax;
	}
	
	
	
	public void setJerkMax(final double jerkMax)
	{
		this.jerkMax = jerkMax;
	}
	
	
	
	public double getJerkMaxW()
	{
		return jerkMaxW;
	}
	
	
	
	public void setJerkMaxW(final double jerkMaxW)
	{
		this.jerkMaxW = jerkMaxW;
	}
}
