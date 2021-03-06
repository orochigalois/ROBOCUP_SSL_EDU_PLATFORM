/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman2.bot;

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.TrackedBot;



public class CamBotFilter
{
	// private final double maxAccXy = 3;
	// private final double maxAccW = 50;
	
	private final BotID			botId;
	private final IBotFilter	filter;
	
	
	
	public CamBotFilter(final CamRobot firstObservation)
	{
		botId = firstObservation.getBotId();
		
		IVector3 initState = new Vector3(firstObservation.getPos().multiplyNew(1e-3), firstObservation.getOrientation());
		// filter = new BotKalmanFilterSingle(initState, firstObservation.getTimestamp());
		// filter = new BotKalmanFilterMulti(initState, firstObservation.getTimestamp());
		filter = new BotUnscentedKalmanFilter(initState, firstObservation.getTimestamp());
	}
	
	
	
	public void update(final CamRobot camRobot)
	{
		IVector3 pos = new Vector3(camRobot.getPos().multiplyNew(1e-3), camRobot.getOrientation());
		// IVector3 preVel = filter.getCurVel();
		// long preT = filter.getCurTimestamp();
		filter.update(pos, camRobot.getTimestamp());
		// IVector3 postVel = filter.getCurVel();
		// long postT = filter.getCurTimestamp();
		//
		// double dt = (postT - preT) / 1e9;
		// IVector3 acc = postVel.subtractNew(preVel).multiplyNew(1 / dt);
		// IVector2 accXy = acc.getXYVector().scaleToNew(Math.min(acc.getLength2(), maxAccXy));
		// double accW = Math.signum(acc.z()) * Math.min(Math.abs(acc.z()), maxAccW);
		// filter.setControl(new Vector3(accXy, accW));
	}
	
	
	
	public ITrackedBot predict(final long timestamp)
	{
		filter.predict(timestamp);
		
		TrackedBot tBot = new TrackedBot(timestamp, botId);
		tBot.setPos(filter.getPos().getXYVector().multiplyNew(1000));
		tBot.setVel(filter.getVel().getXYVector());
		tBot.setAngle(filter.getPos().z());
		tBot.setaVel(filter.getVel().z());
		tBot.setAcc(filter.getAcc().getXYVector());
		tBot.setaAcc(filter.getAcc().z());
		return tBot;
	}
	
	
	
	public BotID getBotId()
	{
		return botId;
	}
	
	
	
	public IBotFilter getFilter()
	{
		return filter;
	}
	
}
