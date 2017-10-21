/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.path;

import java.util.List;

import edu.tigers.sumatra.math.IVector2;



public interface IPath
{
	
	
	IVector2 getStart();
	
	
	
	IVector2 getEnd();
	
	
	
	List<IVector2> getPathPoints();
	
	
	
	List<IVector2> getUnsmoothedPathPoints();
	
	
	
	double getTargetOrientation();
}
