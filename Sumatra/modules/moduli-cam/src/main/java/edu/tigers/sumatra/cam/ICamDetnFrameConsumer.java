/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam;

import edu.tigers.sumatra.cam.data.CamDetectionFrame;



public interface ICamDetnFrameConsumer
{
	
	void onNewCamDetectionFrame(CamDetectionFrame camDetectionFrame);
}
