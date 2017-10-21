/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.filter;

import java.util.List;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.shapes.path.IPath;



public interface IPathFilter
{
	
	boolean accept(PathFinderInput pathFinderInput, IPath newPath, IPath currentPath);
	
	
	
	void getDrawableShapes(List<IDrawableShape> shapes);
}
