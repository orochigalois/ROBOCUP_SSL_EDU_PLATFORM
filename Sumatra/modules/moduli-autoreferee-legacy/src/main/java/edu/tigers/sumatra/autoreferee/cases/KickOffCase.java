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

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg.EMsgType;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class KickOffCase extends ARefereeCase
{
	@Override
	protected void checkCase(final MetisAiFrame frame, final List<RefereeCaseMsg> caseMsgs)
	{
		EGameStateTeam gameState = frame.getTacticalField().getGameState();
		if ((gameState == EGameStateTeam.PREPARE_KICKOFF_WE))
		{
			for (ITrackedBot bot : frame.getWorldFrame().getFoeBots().values())
			{
				// opponents in their half?
				if (bot.getPos().x() < Geometry.getBotRadius())
				{
					RefereeCaseMsg msg = new RefereeCaseMsg(bot.getTeamColor(), EMsgType.KICKOFF_PLACEMENT);
					msg.setBotAtFault(bot.getBotId());
				}
			}
			for (ITrackedBot bot : frame.getWorldFrame().getTigerBotsVisible().values())
			{
				// in our half?
				if (bot.getPos().x() > -Geometry.getBotRadius())
				{
					RefereeCaseMsg msg = new RefereeCaseMsg(bot.getTeamColor(), EMsgType.KICKOFF_PLACEMENT);
					msg.setBotAtFault(bot.getBotId());
				}
			}
		}
	}
}
