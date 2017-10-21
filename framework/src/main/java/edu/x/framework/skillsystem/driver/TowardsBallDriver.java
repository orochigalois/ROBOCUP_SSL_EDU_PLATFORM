/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.drawable.DrawableLine;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class TowardsBallDriver extends PositionDriver
{
	private double maxSpeedPos = 0;
	
	
	
	public TowardsBallDriver(final double maxSpeedPos)
	{
		this.maxSpeedPos = maxSpeedPos;
	}
	
	
	@Override
	public boolean isDone()
	{
		return false;
	}
	
	
	@Override
	public void update(final ITrackedBot bot, final ABot aBot, final WorldFrame wFrame)
	{
		List<IDrawableShape> shapes = new ArrayList<IDrawableShape>();
		IVector2 dest = wFrame.getBall().getPos();
		IVector2 direction = wFrame.getBall().getPos().subtractNew(bot.getPos());
		IVector2 botToKicker = bot.getBotKickerPos().subtractNew(bot.getPos());
		dest = dest.subtractNew(botToKicker);
		
		if (GeoMath.distancePP(bot.getPos(), dest) > maxSpeedPos)
		{
			IVector2 botToDest = dest.subtractNew(bot.getPos()).normalizeNew();
			dest = bot.getPos().addNew(botToDest.multiplyNew(maxSpeedPos));
		}
		setDestination(dest);
		setOrientation(direction.getAngle());
		shapes.add(new DrawableLine(Line.newLine(bot.getBotKickerPos(), wFrame.getBall().getPos()), Color.blue));
		setShapes(EShapesLayer.UNSORTED, shapes);
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.TOWARDS_BALL;
	}
}
