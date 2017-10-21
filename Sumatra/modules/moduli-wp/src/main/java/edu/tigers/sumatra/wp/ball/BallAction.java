/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.ball;

import edu.tigers.sumatra.math.IVector3;



public class BallAction implements IAction
{
	private IVector3 accTorque;
	
	
	
	public BallAction(final IVector3 accTorque)
	{
		this.accTorque = accTorque;
	}
	
	
	@Override
	public IVector3 getAccTorque()
	{
		return accTorque;
	}
	
}
