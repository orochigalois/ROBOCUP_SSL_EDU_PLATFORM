/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.errt.TuneableParameter;
import edu.tigers.sumatra.shapes.path.IPath;



public interface IPathFinder
{
	
	IPath calcPath(PathFinderInput pathFinderInput);
	
	
	
	TuneableParameter getAdjustableParams();
	
}
