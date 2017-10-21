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

import junit.framework.Assert;

import org.junit.Test;



public class GameEventTest
{
	
	
	@Test
	public void shouldGetAnEventAtStart5AndEnd5ForOneFrameLongEventStartingAtFrame5()
	{
		GameEvent actualEvent = new GameEvent(5);
		
		Assert.assertEquals(5, actualEvent.getStartFrame());
		Assert.assertEquals(5, actualEvent.getEndFrame());
		Assert.assertEquals(1, actualEvent.getDuration());
	}
	
	
	
	@Test
	public void shouldGetStartOf0_EndOf2AndDurationOf3For3FramesLongEvent()
	{
		GameEvent actualGameEvent = new GameEvent(0);
		actualGameEvent.signalEventActiveAtFrame(1);
		actualGameEvent.signalEventActiveAtFrame(2);
		
		Assert.assertEquals(0, actualGameEvent.getStartFrame());
		Assert.assertEquals(2, actualGameEvent.getEndFrame());
		Assert.assertEquals(3, actualGameEvent.getDuration());
	}
	
	
	
	@Test
	public void shouldGetStartOf5_EndOf7For3FramesLongEventStartingAt5AndAnActiveStatusOfInactiveForEndedEvent()
	{
		GameEvent gameEvent = new GameEvent(5);
		gameEvent.signalEventActiveAtFrame(6);
		gameEvent.signalEventActiveAtFrame(7);
		gameEvent.signalEventActiveAtFrame(0);
		
		Assert.assertEquals(5, gameEvent.getStartFrame());
		Assert.assertEquals(7, gameEvent.getEndFrame());
		Assert.assertEquals(3, gameEvent.getDuration());
	}
}
