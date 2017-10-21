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

import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchFeedback;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector3;



public class GlobalPosBotDataCollector extends ABotDataCollector<IVector>
{
	private final BotID	botId;
	
	
	
	public GlobalPosBotDataCollector(final BotID botId)
	{
		super(EDataCollector.GLOBAL_POS_BOT);
		this.botId = botId;
	}
	
	
	@Override
	public void onNewMatchFeedback(final BotID botId, final TigerSystemMatchFeedback feedback)
	{
		if (!this.botId.equals(botId))
		{
			return;
		}
		IVector2 pos = feedback.getPosition();
		double angle = feedback.getOrientation();
		addSample(new Vector3(pos, angle));
	}
}
