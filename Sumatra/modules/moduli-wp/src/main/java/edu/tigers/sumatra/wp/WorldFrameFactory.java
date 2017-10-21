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

import java.util.Random;

import edu.tigers.sumatra.bot.DummyBot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.data.TrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class WorldFrameFactory
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final Random RND = new Random();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public WorldFrameFactory()
	{
	}
	
	
	
	public static void setRandomSeed(final long seed)
	{
		RND.setSeed(seed);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public static WorldFrame createWorldFrame(final long frameNumber, final long timestamp)
	{
		return new WorldFrame(createSimpleWorldFrame(frameNumber, timestamp), ETeamColor.YELLOW, false);
	}
	
	
	
	public static SimpleWorldFrame createSimpleWorldFrame(final long frameNumber, final long timestamp)
	{
		final IBotIDMap<ITrackedBot> bots = new BotIDMap<>();
		
		for (int i = 0; i < 6; i++)
		{
			BotID idF = BotID.createBotId(i, ETeamColor.BLUE);
			bots.put(idF, createBot(timestamp, idF, ETeamColor.BLUE));
			
			BotID idT = BotID.createBotId(i, ETeamColor.YELLOW);
			bots.put(idT, createBot(timestamp, idT, ETeamColor.YELLOW));
		}
		
		final TrackedBall ball = TrackedBall.defaultInstance();
		
		SimpleWorldFrame swf = new SimpleWorldFrame(bots, ball, frameNumber, timestamp);
		return swf;
	}
	
	
	
	public static SimpleWorldFrame createEmptyWorldFrame(final long frameNumber, final long timestamp)
	{
		return SimpleWorldFrame.createEmptyWorldFrame(frameNumber, timestamp);
	}
	
	
	
	public static ITrackedBot createBot(final long timestamp, final BotID id, final ETeamColor color)
	{
		double x = (RND.nextDouble() * Geometry.getFieldLength())
				- (Geometry.getFieldLength() / 2.0);
		double y = (RND.nextDouble() * Geometry.getFieldWidth())
				- (Geometry.getFieldWidth() / 2.0);
		final IVector2 pos = new Vector2(x, y);
		
		TrackedBot tBot = new TrackedBot(timestamp, id);
		tBot.setPos(pos);
		DummyBot bot = new DummyBot(id);
		bot.setAvail2Ai(true);
		tBot.setBot(bot);
		return tBot;
	}
}
