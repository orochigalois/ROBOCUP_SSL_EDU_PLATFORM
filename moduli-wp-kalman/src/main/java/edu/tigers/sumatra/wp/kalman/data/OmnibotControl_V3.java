/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.wp.kalman.data;


public class OmnibotControl_V3 implements IControl
{
	private final double		vx, vy, vw;
	private final double		ax, ay, aw;
	private final boolean	useAcc;
									
									
	
	public OmnibotControl_V3(final double vx, final double vy, final double vw)
	{
		super();
		this.vx = vx;
		this.vy = vy;
		this.vw = vw;
		ax = 0;
		ay = 0;
		aw = 0;
		useAcc = false;
	}
	
	
	
	public OmnibotControl_V3(final double vx, final double vy, final double vw, final double ax, final double ay,
			final double aw)
	{
		super();
		this.vx = vx;
		this.vy = vy;
		this.vw = vw;
		this.ax = ax;
		this.ay = ay;
		this.aw = aw;
		useAcc = true;
	}
	
	
	
	public double getVx()
	{
		return vx;
	}
	
	
	
	public double getVy()
	{
		return vy;
	}
	
	
	
	public double getVw()
	{
		return vw;
	}
	
	
	
	public double getAx()
	{
		return ax;
	}
	
	
	
	public double getAy()
	{
		return ay;
	}
	
	
	
	public double getAw()
	{
		return aw;
	}
	
	
	
	public boolean isUseAcc()
	{
		return useAcc;
	}
	
}
