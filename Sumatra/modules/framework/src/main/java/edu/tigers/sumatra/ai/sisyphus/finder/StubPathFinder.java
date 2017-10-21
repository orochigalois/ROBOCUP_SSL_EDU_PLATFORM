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

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.sisyphus.PathFinderInput;
import edu.tigers.sumatra.ai.sisyphus.data.Path;
import edu.tigers.sumatra.ai.sisyphus.errt.TuneableParameter;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.path.IPath;



public class StubPathFinder implements IPathFinder
{
	@Override
	public IPath calcPath(final PathFinderInput pathFinderInput)
	{
		List<IVector2> nodes = new ArrayList<IVector2>(1);
		nodes.add(pathFinderInput.getDestination());
		Path path = new Path(nodes, pathFinderInput.getDstOrient());
		return path;
	}
	
	
	@Override
	public TuneableParameter getAdjustableParams()
	{
		// TODO dirk: Auto-generated method stub
		return null;
	}
}
