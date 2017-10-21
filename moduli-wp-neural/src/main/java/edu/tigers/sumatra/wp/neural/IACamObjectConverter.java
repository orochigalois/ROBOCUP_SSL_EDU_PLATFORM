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

import java.util.Collection;

import edu.tigers.sumatra.cam.data.ACamObject;



public interface IACamObjectConverter
{
	
	
	
	double[] convertInput(Collection<ACamObject> data, double lookahead);
	
	
	
	double[] convertOutput(double[] dataToConvert, ACamObject newest);
	
	
	
	double[] createIdealOutput(Collection<ACamObject> frames, final ACamObject referenceItem, final double timestep);
}
