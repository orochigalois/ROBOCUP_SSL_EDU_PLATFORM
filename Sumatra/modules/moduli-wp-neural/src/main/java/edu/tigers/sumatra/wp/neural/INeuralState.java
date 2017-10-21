/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.neural;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;



public interface INeuralState
{
	
	
	void loadNetwork(final String filenameStub);
	
	
	
	void saveNetwork(final String filenameStub);
	
	
	
	void updateState(final ExtendedCamDetectionFrame newframe);
	
	
	
	void trainNetworks();
	
	
	
	void performPrediction();
	
	
	
	Iterable<INeuralPredicitonData> getPredictedObjects();
	
	
	
	ETeamColor getTeamColor();
	
	
}
