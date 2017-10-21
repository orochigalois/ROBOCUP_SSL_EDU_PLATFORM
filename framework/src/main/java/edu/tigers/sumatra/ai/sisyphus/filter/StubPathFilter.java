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



public class StubPathFilter implements IPathFilter
{
	@Override
	public boolean accept(final PathFinderInput pathFinderInput, final IPath newPath, final IPath currentPath)
	{
		return true;
	}
	
	
	@Override
	public void getDrawableShapes(final List<IDrawableShape> shapes)
	{
	}
}
