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

import edu.tigers.sumatra.wp.data.WorldFrame;


public class AIInfoFrame extends AthenaAiFrame
{
	
	public AIInfoFrame(final AthenaAiFrame athenaAiFrame)
	{
		super(athenaAiFrame, athenaAiFrame.getPlayStrategy());
	}
	
	
	
	public AIInfoFrame(final AIInfoFrame original)
	{
		super(original, original.getPlayStrategy());
	}
}
