/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.vis;

import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.ai.data.BotAiInformation;
import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;
import edu.tigers.sumatra.drawable.DrawableAnnotation;
import edu.tigers.sumatra.drawable.DrawableAnnotation.ELocation;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class RoleNameVisCalc extends ACalculator
{
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		List<IDrawableShape> shapes = newTacticalField.getDrawableShapes().get(EShapesLayer.ROLE_NAMES);
		for (Map.Entry<BotID, BotAiInformation> entry : newTacticalField.getBotAiInformation().entrySet())
		{
			BotID botID = entry.getKey();
			BotAiInformation aiInfo = entry.getValue();
			
			ITrackedBot bot = baseAiFrame.getWorldFrame().getTigerBotsVisible().getWithNull(botID);
			if (bot == null)
			{
				continue;
			}
			IVector2 pos = bot.getPos();
			String text = aiInfo.getRole() + "\n" + aiInfo.getRoleState();
			DrawableAnnotation dTxtRole = new DrawableAnnotation(pos, text);
			dTxtRole.setColor(baseAiFrame.getTeamColor().getColor());
			dTxtRole.setFontSize(10);
			dTxtRole.setMargin(130);
			dTxtRole.setLocation(ELocation.RIGHT);
			shapes.add(dTxtRole);
		}
	}
}
