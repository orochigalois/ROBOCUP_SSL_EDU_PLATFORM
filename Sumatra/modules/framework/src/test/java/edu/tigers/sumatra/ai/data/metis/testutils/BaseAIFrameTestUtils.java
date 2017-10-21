/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.metis.testutils;

import org.mockito.Mockito;

import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class BaseAIFrameTestUtils
{
	
	public static BaseAiFrame mockBaseAiFrameForWorldFrameID(final long idToSet)
	{
		SimpleWorldFrame simpleWorldFrame = new SimpleWorldFrame(new BotIDMap<ITrackedBot>(), null, idToSet, 0);
		WorldFrame worldFrame = new WorldFrame(simpleWorldFrame, ETeamColor.BLUE, true);
		
		BaseAiFrame baseAiFrame = Mockito.mock(BaseAiFrame.class);
		Mockito.when(baseAiFrame.getWorldFrame()).thenReturn(worldFrame);
		
		return baseAiFrame;
	}
}
