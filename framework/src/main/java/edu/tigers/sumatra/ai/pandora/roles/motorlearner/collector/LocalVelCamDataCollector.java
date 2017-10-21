/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.motorlearner.collector;

import java.util.Optional;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;



public class LocalVelCamDataCollector extends ACamDataCollector<IVector>
{
	@SuppressWarnings("unused")
	private static final Logger	log			= Logger.getLogger(LocalVelCamDataCollector.class.getName());
	
	private final BotID				botId;
	private Optional<CamRobot>		preBot		= Optional.empty();
	private Optional<CamRobot>		prePreBot	= Optional.empty();
	private int							camId			= -1;
	private final double				minDt;
	
	
	
	public LocalVelCamDataCollector(final BotID botId, final double minDt)
	{
		super(EDataCollector.LOCAL_VEL_CAM);
		this.botId = botId;
		this.minDt = minDt;
	}
	
	
	
	public LocalVelCamDataCollector(final BotID botId)
	{
		this(botId, 0);
	}
	
	
	@Override
	protected void onNewCamFrame(final ExtendedCamDetectionFrame frame)
	{
		final Optional<CamRobot> newBot = getCamRobot(frame, botId);
		if (!newBot.isPresent())
		{
			return;
		}
		
		if (camId == -1)
		{
			camId = newBot.get().getCameraId();
		} else if (camId != newBot.get().getCameraId())
		{
			return;
		}
		
		if (!preBot.isPresent())
		{
			preBot = newBot;
			return;
		}
		
		
		double dt = (newBot.get().getTimestamp() - preBot.get().getTimestamp()) / 1e9;
		if (dt > minDt)
		{
			if (!prePreBot.isPresent())
			{
				prePreBot = preBot;
				preBot = newBot;
				return;
			}
			
			IVector3 velLocal = getVel(prePreBot.get(), preBot.get(), newBot.get());
			addSample(velLocal);
			
			prePreBot = preBot;
			preBot = newBot;
		}
	}
	
	
	@Override
	public void start()
	{
		super.start();
		camId = -1;
		preBot = Optional.empty();
		prePreBot = Optional.empty();
	}
}
