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

import java.util.List;
import java.util.Set;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class MixedPathDriver extends ABaseDriver
{
	
	
	private final IPathDriver	pathDriverFar;
	private final IPathDriver	pathDriverClose;
	private IPathDriver			currentDriver;
	private final IVector2		destination;
	
	@Configurable(comment = "Dist [mm] - this is the threshold distance to destination, when the close path driver will be activated")
	private static double		changeToClosePathDriverThreshold	= 100;
	
	
	static
	{
		ConfigRegistration.registerClass("skills", MixedPathDriver.class);
	}
	
	
	
	public MixedPathDriver(final IPathDriver pathDriverFar, final IPathDriver pathDriverClose,
			final IVector2 destination)
	{
		this.pathDriverFar = pathDriverFar;
		this.pathDriverClose = pathDriverClose;
		currentDriver = pathDriverFar;
		this.destination = destination;
	}
	
	
	private IPathDriver getCurrentPathDriver(final ITrackedBot bot)
	{
		IVector2 currentPos = bot.getPos();
		double dist = GeoMath.distancePP(currentPos, destination);
		
		if (dist < (changeToClosePathDriverThreshold))
		{
			currentDriver = pathDriverClose;
			return pathDriverClose;
		}
		currentDriver = pathDriverFar;
		return pathDriverFar;
	}
	
	
	@Override
	public IVector3 getNextDestination(final ITrackedBot bot, final WorldFrame wFrame)
	{
		return getCurrentPathDriver(bot).getNextDestination(bot, wFrame);
	}
	
	
	@Override
	public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
	{
		return getCurrentPathDriver(bot).getNextVelocity(bot, wFrame);
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.MIXED;
	}
	
	
	@Override
	public Set<EBotSkill> getSupportedCommands()
	{
		return currentDriver.getSupportedCommands();
	}
	
	
	@Override
	public boolean isDone()
	{
		return currentDriver.isDone();
	}
	
	
	@Override
	public void update(final ITrackedBot bot, final ABot aBot, final WorldFrame wFrame)
	{
		getCurrentPathDriver(bot).update(bot, aBot, wFrame);
	}
	
	
	@Override
	public IVector3 getNextLocalVelocity(final ITrackedBot bot, final WorldFrame wFrame, final double dt)
	{
		return getCurrentPathDriver(bot).getNextLocalVelocity(bot, wFrame, dt);
	}
	
	
	@Override
	public ShapeMap getShapes()
	{
		return currentDriver.getShapes();
	}
	
	
	@Override
	public void setShapes(final EShapesLayer layer, final List<IDrawableShape> shapes)
	{
		currentDriver.setShapes(layer, shapes);
	}
}
