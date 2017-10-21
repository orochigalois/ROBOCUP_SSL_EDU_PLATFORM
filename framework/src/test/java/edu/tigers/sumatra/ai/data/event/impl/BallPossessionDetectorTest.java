/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event.impl;

import junit.framework.Assert;

import org.junit.Test;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.event.GameEventFrame;
import edu.tigers.sumatra.ai.data.event.IGameEventDetector;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.metis.testutils.BaseAIFrameTestUtils;
import edu.tigers.sumatra.ai.data.metis.testutils.TacticalFieldTestUtils;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class BallPossessionDetectorTest
{
	
	
	@Test
	public void shouldDetectBallPossessionForBot1BlueIfThisBotGotTheBall()
	{
		BotID touchingBot = BotID.createBotId(1, ETeamColor.BLUE);
		
		GameEventFrame expectedEvent = new GameEventFrame(touchingBot);
		TacticalField stubTacticalField = TacticalFieldTestUtils
				.initializeTacticalFieldJustWithPossessingBot(touchingBot);
		
		BaseAiFrame mockedFrame = BaseAIFrameTestUtils.mockBaseAiFrameForWorldFrameID(5);
		
		IGameEventDetector detector = new BallPossessionDetector();
		
		GameEventFrame actualEvent = detector.getActiveParticipant(stubTacticalField, mockedFrame);
		
		Assert.assertEquals(expectedEvent, actualEvent);
	}
}
