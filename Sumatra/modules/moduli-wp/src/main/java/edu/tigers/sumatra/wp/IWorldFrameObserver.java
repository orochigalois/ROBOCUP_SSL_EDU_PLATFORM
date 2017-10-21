/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp;

import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public interface IWorldFrameObserver
{
	
	default void onNewWorldFrame(final WorldFrameWrapper wFrameWrapper)
	{
	}
	
	
	
	default void onClearWorldFrame()
	{
	}
	
	
	
	default void onNewCamDetectionFrame(final ExtendedCamDetectionFrame frame)
	{
	}
	
	
	
	default void onClearCamDetectionFrame()
	{
	}
}
