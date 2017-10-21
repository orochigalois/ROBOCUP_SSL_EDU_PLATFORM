/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.wp.kalman.filter;

import Jama.Matrix;
import edu.tigers.sumatra.wp.kalman.data.AMotionResult;
import edu.tigers.sumatra.wp.kalman.data.AWPCamObject;
import edu.tigers.sumatra.wp.kalman.data.IControl;
import edu.tigers.sumatra.wp.kalman.data.PredictionContext;
import edu.tigers.sumatra.wp.kalman.motionModels.IMotionModel;



public interface IFilter
{
	
	void init(IMotionModel motionModel, PredictionContext context, final long firstTimestamp,
			AWPCamObject firstObservation);
	
	
	
	default void reset(final long firstTimestamp, final Matrix measurement)
	{
	}
	
	
	
	long getTimestamp();
	
	
	
	AMotionResult getPrediction(final long timestamp);
	
	
	
	void observation(long timestamp, AWPCamObject observation);
	
	
	
	void setControl(IControl control);
	
	
	
	int getId();
	
	
	
	IMotionModel getMotion();
}
