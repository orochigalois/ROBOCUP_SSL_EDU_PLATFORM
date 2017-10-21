/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.frames;

import edu.tigers.sumatra.ai.data.IPlayStrategy;



public class AthenaAiFrame extends MetisAiFrame
{
	private final IPlayStrategy	playStrategy;
	
	
	
	public AthenaAiFrame(final MetisAiFrame metisAiFrame, final IPlayStrategy playStrategy)
	{
		super(metisAiFrame, metisAiFrame.getTacticalField());
		this.playStrategy = playStrategy;
	}
	
	
	
	public IPlayStrategy getPlayStrategy()
	{
		return playStrategy;
	}
}
