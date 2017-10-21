/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman2.bot;

import org.apache.commons.math3.linear.RealVector;



public interface IKalmanFilter
{
	
	void correct(RealVector meas, long timestamp);
	
	
	
	void predict(long timestamp);
	
	
	
	RealVector getState();
	
}