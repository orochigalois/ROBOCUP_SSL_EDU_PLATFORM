/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.wp.kalman2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.SubnodeConfiguration;

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.wp.AWorldPredictor;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.kalman2.bot.CamBotFilter;





public class KalmanWorldPredictor extends AWorldPredictor
{
	private final Map<BotID, CamBotFilter>	botFilters	= new HashMap<>();
	
	private double									lookahead	= 0.0;
	
	
	
	public KalmanWorldPredictor(final SubnodeConfiguration config)
	{
		super(config);
	}
	
	
	@Override
	protected void processCameraDetectionFrame(final ExtendedCamDetectionFrame frame)
	{
		SimpleWorldFrame swf = predictSimpleWorldFrame(frame);
		pushFrame(swf);
	}
	
	
	
	public SimpleWorldFrame predictSimpleWorldFrame(final ExtendedCamDetectionFrame frame)
	{
		List<CamRobot> bots = new ArrayList<>();
		bots.addAll(frame.getRobotsYellow());
		bots.addAll(frame.getRobotsBlue());
		
		for (CamRobot bot : bots)
		{
			CamBotFilter filter = botFilters.get(bot.getBotId());
			if (filter == null)
			{
				filter = new CamBotFilter(bot);
				botFilters.put(bot.getBotId(), filter);
			} else
			{
				filter.update(bot);
			}
		}
		
		IBotIDMap<ITrackedBot> tBots = new BotIDMap<>(botFilters.size());
		TrackedBall ball = new TrackedBall(AVector3.ZERO_VECTOR, AVector3.ZERO_VECTOR, AVector3.ZERO_VECTOR);
		long ts = frame.gettCapture() + (long) (lookahead * 1e9);
		for (CamBotFilter filter : botFilters.values())
		{
			
			if (frame.gettCapture() < filter.getFilter().getCurTimestamp())
			{
				continue;
			}
			ITrackedBot tBot = filter.predict(ts);
			tBots.put(tBot.getBotId(), tBot);
		}
		SimpleWorldFrame swf = new SimpleWorldFrame(tBots, ball, frame.getFrameNumber(), frame.gettCapture());
		return swf;
	}
}
