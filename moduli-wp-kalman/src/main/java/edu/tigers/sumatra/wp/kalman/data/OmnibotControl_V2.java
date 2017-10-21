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


public class OmnibotControl_V2 implements IControl
{
	
	public final double	vt;
	
	public final double	vo;
	
	public final double	omega;
	
	public final double	eta;
								
	
	public final double	at;
	
	public final double	ao;
								
	
	public final boolean	useAcc;
								
								
	
	public OmnibotControl_V2(final double vt, final double vo, final double omega, final double eta, final double at,
			final double ao)
	{
		this.vt = vt;
		this.vo = vo;
		this.omega = omega;
		this.eta = eta;
		this.at = at;
		this.ao = ao;
		useAcc = true;
	}
	
	
	
	public OmnibotControl_V2(final double vt, final double vo, final double omega, final double eta)
	{
		this.vt = vt;
		this.vo = vo;
		this.omega = omega;
		this.eta = eta;
		at = 0;
		ao = 0;
		useAcc = false;
	}
}
