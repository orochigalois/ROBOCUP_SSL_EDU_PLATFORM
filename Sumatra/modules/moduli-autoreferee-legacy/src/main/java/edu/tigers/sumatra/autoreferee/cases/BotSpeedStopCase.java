/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.autoreferee.cases;

import java.util.List;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg.EMsgType;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class BotSpeedStopCase extends ARefereeCase
{
	@Override
	protected void checkCase(final MetisAiFrame frame, final List<RefereeCaseMsg> caseMsgs)
	{
		for (ITrackedBot bot : frame.getWorldFrame().getBots().values())
		{
			if (bot.getVel().getLength2() > (Geometry.getStopSpeed() + 0.1))
			{
				RefereeCaseMsg msg = new RefereeCaseMsg(bot.getBotId().getTeamColor(), EMsgType.BOT_SPEED_STOP);
				msg.setBotAtFault(bot.getBotId());
				msg.setAdditionalInfo("Vel: " + bot.getVel().getLength2() + "m/s");
				caseMsgs.add(msg);
			}
		}
	}
}
