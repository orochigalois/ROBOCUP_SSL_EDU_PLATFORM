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

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class GlobalPosWpDataCollector extends AWpDataCollector<IVector>
{
	private final BotID	botId;
	
	
	
	public GlobalPosWpDataCollector(final BotID botId)
	{
		super(EDataCollector.GLOBAL_POS_WP);
		this.botId = botId;
	}
	
	
	@Override
	protected void onNewWorldFrameWrapper(final WorldFrameWrapper wfw)
	{
		ITrackedBot bot = wfw.getSimpleWorldFrame().getBot(botId);
		if (bot == null)
		{
			return;
		}
		addSample(new Vector3(bot.getPos().multiplyNew(1e-3f), bot.getAngle()));
	}
}
