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

import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;



public class GlobalPosCamDataCollector extends ACamDataCollector<IVector>
{
	private final BotID	botId;
	
	
	
	public GlobalPosCamDataCollector(final BotID botId)
	{
		super(EDataCollector.GLOBAL_POS_CAM);
		this.botId = botId;
	}
	
	
	@Override
	protected void onNewCamFrame(final ExtendedCamDetectionFrame frame)
	{
		final Optional<CamRobot> newBot = getCamRobot(frame, botId);
		if (newBot.isPresent())
		{
			addSample(new Vector3(newBot.get().getPos().multiplyNew(1e-3f), newBot.get().getOrientation()));
		}
	}
}
