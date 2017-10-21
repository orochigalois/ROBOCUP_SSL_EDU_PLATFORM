/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class ObjectIDTester
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Test
	public void testID()
	{
		final BotID botId = BotID.createBotId(1, ETeamColor.YELLOW);
		
		BotID botId2 = botId;
		assertTrue(botId.equals(botId2));
		
		botId2 = BotID.createBotId(1, ETeamColor.YELLOW);
		assertTrue(botId.equals(botId2));
		
		botId2 = BotID.createBotId(2, ETeamColor.YELLOW);
		assertFalse(botId.equals(botId2));
		
		assertFalse(botId.equals(null));
		assertFalse(botId.equals(new BallID()));
		assertFalse(botId.equals(this));
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
