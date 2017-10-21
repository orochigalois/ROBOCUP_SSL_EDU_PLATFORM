/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.BotIDMapConst;
import edu.tigers.sumatra.ids.IBotIDMap;



@Persistent(version = 3)
public class SimpleWorldFrame
{
	private final long									frameNumber;
	private final IBotIDMap<ITrackedBot>			bots;
	private final TrackedBall							ball;
	private final long									timestamp;
	private transient ExtendedCamDetectionFrame	frame	= null;
	
	
	@SuppressWarnings("unused")
	private SimpleWorldFrame()
	{
		frameNumber = 0;
		bots = null;
		ball = null;
		timestamp = 0;
	}
	
	
	
	public SimpleWorldFrame(final IBotIDMap<ITrackedBot> bots, final TrackedBall ball, final long frameNumber,
			final long timestamp)
	{
		this.ball = ball;
		this.timestamp = timestamp;
		this.frameNumber = frameNumber;
		this.bots = BotIDMapConst.unmodifiableBotIDMap(bots);
	}
	
	
	
	public SimpleWorldFrame(final SimpleWorldFrame swf)
	{
		ball = swf.getBall();
		timestamp = swf.timestamp;
		frameNumber = swf.frameNumber;
		bots = swf.bots;
		frame = swf.frame;
	}
	
	
	
	public SimpleWorldFrame mirrorNew()
	{
		IBotIDMap<ITrackedBot> bots = new BotIDMap<>();
		for (ITrackedBot bot : this.bots.values())
		{
			ITrackedBot mBot = bot.mirrorNew();
			bots.put(bot.getBotId(), mBot);
		}
		TrackedBall mBall = new TrackedBall(getBall()).mirrorNew();
		SimpleWorldFrame frame = new SimpleWorldFrame(bots, mBall, frameNumber, timestamp);
		frame.setCamFrame(getCamFrame());
		return frame;
	}
	
	
	
	public static SimpleWorldFrame createEmptyWorldFrame(final long frameNumber, final long timestamp)
	{
		final IBotIDMap<ITrackedBot> bots = new BotIDMap<>();
		final TrackedBall ball = TrackedBall.defaultInstance();
		
		SimpleWorldFrame swf = new SimpleWorldFrame(bots, ball, frameNumber, timestamp);
		return swf;
	}
	
	
	
	public final IBotIDMap<ITrackedBot> getBots()
	{
		return bots;
	}
	
	
	
	public final ITrackedBot getBot(final BotID botId)
	{
		return bots.getWithNull(botId);
	}
	
	
	
	public final TrackedBall getBall()
	{
		return ball;
	}
	
	
	
	public final long getId()
	{
		return frameNumber;
	}
	
	
	
	public final long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public void setCamFrame(final ExtendedCamDetectionFrame frame)
	{
		this.frame = frame;
	}
	
	
	
	public ExtendedCamDetectionFrame getCamFrame()
	{
		return frame;
	}
}
