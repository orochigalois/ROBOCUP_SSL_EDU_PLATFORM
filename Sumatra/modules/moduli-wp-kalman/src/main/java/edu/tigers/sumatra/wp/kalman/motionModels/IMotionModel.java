/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.wp.kalman.motionModels;

import Jama.Matrix;
import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.wp.data.MotionContext;
import edu.tigers.sumatra.wp.kalman.data.AMotionResult;
import edu.tigers.sumatra.wp.kalman.data.AWPCamObject;
import edu.tigers.sumatra.wp.kalman.data.IControl;
import edu.tigers.sumatra.wp.kalman.filter.IFilter;



public interface IMotionModel
{
	
	Matrix dynamics(Matrix state, Matrix control, double dt, MotionContext context);
	
	
	
	Matrix getDynamicsJacobianWRTstate(Matrix state, double dt);
	
	
	
	Matrix getDynamicsJacobianWRTnoise(Matrix state, double dt);
	
	
	
	Matrix getDynamicsCovariance(Matrix state, double dt);
	
	
	
	AMotionResult generateMotionResult(int id, Matrix state, boolean onCam);
	
	
	
	Matrix generateMeasurementMatrix(AWPCamObject observation, Matrix state);
	
	
	
	Matrix generateStateMatrix(Matrix measurement, Matrix control);
	
	
	
	Matrix updateStateOnNewControl(IControl control, Matrix state);
	
	
	
	Matrix updateCovarianceOnNewControl(IControl control, Matrix covariance);
	
	
	
	Matrix generateControlMatrix(IControl control, Matrix state);
	
	
	
	Matrix generateCovarianceMatrix(Matrix state);
	
	
	
	int extractObjectID(AWPCamObject observation);
	
	
	
	Matrix measurementDynamics(Matrix state);
	
	
	
	Matrix getMeasurementJacobianWRTstate(Matrix state);
	
	
	
	Matrix getMeasurementJacobianWRTnoise(Matrix state);
	
	
	
	Matrix getMeasurementCovariance(Matrix measurement);
	
	
	
	Matrix getStateOnNoObservation(Matrix state);
	
	
	
	Matrix getCovarianceOnNoObservation(Matrix covariance);
	
	
	
	Matrix getControlOnNoObservation(Matrix control);
	
	
	
	Matrix statePostProcessing(Matrix state, Matrix preState);
	
	
	
	default void newMeasurement(final Matrix measurement, final Matrix matrix, final double dt)
	{
	}
	
	
	
	void estimateControl(final IFilter bot, final AMotionResult oldState, final CamRobot newBot,
			CamRobot lastVisionBotCam, final double dt);
	
	
	
	Matrix getDynamicsState(Matrix fullState);
}
