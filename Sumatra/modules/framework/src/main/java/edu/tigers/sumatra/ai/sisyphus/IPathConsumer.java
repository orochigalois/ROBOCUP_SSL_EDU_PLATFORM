/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus;

import edu.tigers.sumatra.shapes.path.IPath;



public interface IPathConsumer
{
	
	void onNewPath(IPath path);
	
	
	
	void onPotentialNewPath(IPath path);
}
