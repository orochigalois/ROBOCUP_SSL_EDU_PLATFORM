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

import java.awt.Color;
import java.util.List;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;
import edu.tigers.sumatra.drawable.DrawableCircle;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class BotAvailableVisCalc extends ACalculator
{
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		
		List<IDrawableShape> shapes = newTacticalField.getDrawableShapes().get(EShapesLayer.BOTS_AVAILABLE);
		for (ITrackedBot bot : baseAiFrame.getWorldFrame().getBots().values())
		{
			if (!bot.isAvailableToAi())
			{
				DrawableCircle arc = new DrawableCircle(bot.getPos(), Geometry.getBotRadius() * 2, Color.red);
				shapes.add(arc);
			}
		}
	}
	
}
