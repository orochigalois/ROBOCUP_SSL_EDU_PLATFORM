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

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.drawable.DrawableBot;
import edu.tigers.sumatra.drawable.DrawableLine;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class PositionDriver extends ABaseDriver
{
	
	private IVector2	destination			= null;
	private IVector3	nextDestination	= null;
	private double		orientation			= 0;
	private boolean	allowOutsideField	= false;
	
	
	@Configurable(comment = "max dist [mm] that destination may differ from bot pos. If greater, dest is modified.", spezis = {
			"", "TIGER_V3" }, defValueSpezis = { "1000", "500" })
	private double		maxDistance			= Double.MAX_VALUE;
	
	
	private double		maxDist				= maxDistance;
	
	
	static
	{
		ConfigRegistration.registerClass("skills", PositionDriver.class);
	}
	
	
	
	public PositionDriver()
	{
		addSupportedCommand(EBotSkill.GLOBAL_POSITION);
	}
	
	
	@Override
	public IVector3 getNextDestination(final ITrackedBot bot, final WorldFrame wFrame)
	{
		if (destination == null)
		{
			return new Vector3(bot.getPos(), bot.getAngle());
		}
		IVector2 dest = destination;
		double dist = GeoMath.distancePP(dest, bot.getPos());
		if (dist > maxDist)
		{
			dest = GeoMath.stepAlongLine(bot.getPos(), dest, maxDist);
		}
		if (!allowOutsideField && !Geometry.getFieldWBorders().isPointInShape(dest, -Geometry.getBotRadius()))
		{
			// List<IVector2> intersects = Geometry.getFieldWBorders()
			// .lineIntersections(Line.newLine(bot.getPos(), dest));
			// Rectangle rect = new Rectangle(bot.getPos(), dest);
			// dest = Geometry.getFieldWBorders().nearestPointInside(dest);
			// for (IVector2 inter : intersects)
			// {
			// if (rect.isPointInShape(inter))
			// {
			// dest = inter;
			// break;
			// }
			// }
		}
		nextDestination = new Vector3(dest, orientation);
		return nextDestination;
	}
	
	
	@Override
	protected void onFirstUpdate(final ITrackedBot bot, final ABot aBot, final WorldFrame wFrame)
	{
		super.onFirstUpdate(bot, aBot, wFrame);
		maxDist = maxDistance;
	}
	
	
	
	public IVector2 getDestination()
	{
		return destination;
	}
	
	
	
	public void setDestination(final IVector2 destination)
	{
		this.destination = destination;
	}
	
	
	
	public void setDestination(final IVector3 destination)
	{
		this.destination = destination.getXYVector();
		orientation = destination.z();
	}
	
	
	
	public double getOrientation()
	{
		return orientation;
	}
	
	
	
	public void setOrientation(final double orientation)
	{
		this.orientation = orientation;
	}
	
	
	@Override
	public EPathDriver getType()
	{
		return EPathDriver.POSITION;
	}
	
	
	@Override
	public void update(final ITrackedBot bot, final ABot aBot, final WorldFrame wFrame)
	{
		super.update(bot, aBot, wFrame);
		List<IDrawableShape> shapes = new ArrayList<>(2);
		if (destination != null)
		{
			shapes.add(new DrawableBot(destination, orientation, Color.magenta, Geometry.getBotRadius(),
					bot.getCenter2DribblerDist()));
		}
		if (nextDestination != null)
		{
			shapes.add(
					new DrawableBot(nextDestination.getXYVector(), nextDestination.z(), Color.red, Geometry.getBotRadius(),
							bot.getCenter2DribblerDist()));
			shapes.add(new DrawableLine(Line.newLine(bot.getPos(), nextDestination.getXYVector()), Color.PINK));
		}
		setShapes(EShapesLayer.POSITION_DRIVER, shapes);
	}
	
	
	
	public double getMaxDist()
	{
		return maxDist;
	}
	
	
	
	public void setMaxDist(final double maxDist)
	{
		this.maxDist = maxDist;
	}
	
	
	
	public boolean isAllowOutsideField()
	{
		return allowOutsideField;
	}
	
	
	
	public void setAllowOutsideField(final boolean allowOutsideField)
	{
		this.allowOutsideField = allowOutsideField;
	}
}
