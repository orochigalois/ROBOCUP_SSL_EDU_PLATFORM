/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.control;

import java.util.Arrays;

import Jama.Matrix;



public class LinearPolicy implements IPolicyController
{
	
	private final int	stateDimension	= 6;
												
	private Double[]	pid_p				= { 3.5, 3.5, 6.0 };
	private Double[]	pid_d				= { 0.4, 0.4, 1.0 };
												
												
	
	public LinearPolicy(final Double[] pid_p, final Double[] pid_d)
	{
		this.pid_p = Arrays.copyOf(pid_p, 3);
		this.pid_d = Arrays.copyOf(pid_d, 3);
	}
	
	
	@Override
	public Matrix getControl(final Matrix state)
	{
		assert state.getRowDimension() == 1;
		
		
		Matrix u = new Matrix(1, state.getColumnDimension() / 2);
		for (int a = 0; a < (state.getColumnDimension() / 2.0); a++)
		{
			double action = (pid_p[a] * state.get(0, a)) - (pid_d[a] * state.get(0, (state.getColumnDimension() / 2) + a));
			u.set(0, a, action);
		}
		
		return u;
	}
	
	
	@Override
	public int getStateDimension()
	{
		return stateDimension;
	}
	
	
	@Override
	public double getDt()
	{
		return 0.1;
	}
	
}
