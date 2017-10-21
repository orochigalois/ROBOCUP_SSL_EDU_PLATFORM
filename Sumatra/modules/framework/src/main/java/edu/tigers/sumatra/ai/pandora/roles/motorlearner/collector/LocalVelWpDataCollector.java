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
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class LocalVelWpDataCollector extends AWpDataCollector<IVector>
{
	private final BotID	botId;
	
	
	
	public LocalVelWpDataCollector(final BotID botId)
	{
		super(EDataCollector.LOCAL_VEL_WP);
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
		IVector2 velXYglob = bot.getVel();
		double velW = bot.getaVel();
		double curAngle = bot.getAngle();
		IVector2 velXYloc = GeoMath.convertGlobalBotVector2Local(velXYglob, curAngle);
		addSample(new Vector3(velXYloc, velW));
	}
}
