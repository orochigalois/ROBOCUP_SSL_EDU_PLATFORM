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

import edu.tigers.sumatra.ai.data.ITacticalField;



public class MetisAiFrame extends BaseAiFrame
{
	
	private final ITacticalField	tacticalField;
	
	
	
	public MetisAiFrame(final BaseAiFrame baseAiFrame, final ITacticalField tacticalInfo)
	{
		super(baseAiFrame);
		tacticalField = tacticalInfo;
	}
	
	
	
	public ITacticalField getTacticalField()
	{
		return tacticalField;
	}
}
