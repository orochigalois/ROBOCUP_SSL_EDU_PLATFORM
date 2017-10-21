/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event;

import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.ballpossession.EBallPossession;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.metis.testutils.BaseAIFrameTestUtils;
import edu.tigers.sumatra.ai.data.metis.testutils.TacticalFieldTestUtils;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import junit.framework.Assert;



public class GameEventManagerTest
{
	
	@Test
	public void testCreationOfManagerWithBallPossessionEvent()
	{
		Set<EGameEvent> expectedEvents = createSetWithBallPossession();
		
		GameEventManager manager = new GameEventManager(expectedEvents);
		
		Set<EGameEvent> actualEvents = manager.getTrackedEventTypes();
		
		Assert.assertEquals(expectedEvents, actualEvents);
	}
	
	
	private Set<EGameEvent> createSetWithBallPossession()
	{
		Set<EGameEvent> eventsWithBallPossession = new HashSet<EGameEvent>();
		eventsWithBallPossession.add(EGameEvent.BALL_POSSESSION);
		
		return eventsWithBallPossession;
	}
	
	
	
	@Test
	public void testCreationOfManagerWithNullInputSet()
	{
		GameEventManager manager = new GameEventManager(null);
		
		Set<EGameEvent> expectedEvents = new HashSet<>();
		
		Set<EGameEvent> actualEvents = manager.getTrackedEventTypes();
		
		Assert.assertEquals(expectedEvents, actualEvents);
	}
	
	
	
	@Ignore
	@Test
	public void testDetectionOfEventsByManagerWithBallPossession()
	{
		Set<EGameEvent> inputEvents = createSetWithBallPossession();
		
		GameEventManager manager = new GameEventManager(inputEvents);
		
		TacticalField newTacticalField = TacticalFieldTestUtils
				.initializeTacticalFieldJustWithBallPossessionForSide(EBallPossession.WE);
		
		BaseAiFrame baseAiFrame = BaseAIFrameTestUtils.mockBaseAiFrameForWorldFrameID(5);
		
		manager.detectEvents(newTacticalField, baseAiFrame);
		
		IGameEventStorage trackedBallPossessionEvents = manager.getTrackedEventsForType(EGameEvent.BALL_POSSESSION);
		
		GameEvent expectedEvent = new GameEvent(5, BotID.createBotId(0, ETeamColor.BLUE));
		GameEvent actualEvent = trackedBallPossessionEvents.getEvents().get(0);
		
		Assert.assertEquals(expectedEvent, actualEvent);
	}
}
