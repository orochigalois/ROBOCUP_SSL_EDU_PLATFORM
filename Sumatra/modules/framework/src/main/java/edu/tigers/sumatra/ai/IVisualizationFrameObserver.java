/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai;

import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.ids.ETeamColor;



public interface IVisualizationFrameObserver
{
	
	default void onNewVisualizationFrame(final VisualizationFrame frame)
	{
	}
	
	
	
	default void onClearVisualizationFrame(final ETeamColor teamColor)
	{
	}
	
	
	
	default void onAIException(final Throwable ex, final VisualizationFrame frame, final VisualizationFrame prevFrame)
	{
	}
}
