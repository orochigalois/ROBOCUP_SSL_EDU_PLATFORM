/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;



@Persistent
public class DrawableBotShape implements IDrawableShape
{
	private final IVector2	pos;
	private final double		angle;
	private Color				color			= Color.WHITE;
	private Color				fontColor	= Color.black;
	private String				id				= "";
													
	private double				center2DribblerDist;
	private double				radius;
	private boolean			fill			= true;
													
													
	@SuppressWarnings("unused")
	private DrawableBotShape()
	{
		this(AVector2.ZERO_VECTOR, 0, 0, 0);
	}
	
	
	// 
	// public DrawableBotShape(final TrackedBot bot)
	// {
	// this(bot.getPos(), bot.getAngle());
	// if (bot.isVisible())
	// {
	// color = bot.getTeamColor() == ETeamColor.YELLOW ? Color.yellow : Color.blue;
	// } else
	// {
	// color = bot.getTeamColor() == ETeamColor.YELLOW ? Color.yellow.darker() : Color.cyan.darker();
	// }
	// fontColor = bot.getTeamColor() == ETeamColor.YELLOW ? Color.black : Color.white;
	// id = String.valueOf(bot.getBotId().getNumber());
	// }
	//
	
	
	public DrawableBotShape(final IVector2 pos, final double angle, final double radius,
			final double center2DribblerDist)
	{
		this.pos = pos;
		this.angle = angle;
		this.radius = radius;
		this.center2DribblerDist = center2DribblerDist;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		final int robotRadius = tool.scaleXLength(radius);
		
		g.setStroke(new BasicStroke());
		
		// --- determinate drawing-position ---
		int drawingX = 0;
		int drawingY = 0;
		
		// --- from SSLVision-mm to java2d-coordinates ---
		final IVector2 transBotPos = tool.transformToGuiCoordinates(pos, invert);
		drawingX = (int) transBotPos.x() - robotRadius;
		drawingY = (int) transBotPos.y() - robotRadius;
		
		
		g.setColor(color);
		double r = radius;
		double alpha = Math.acos(center2DribblerDist / r);
		double startAngleRad = (angle - AngleMath.PI_HALF) + tool.getFieldTurn().getAngle() + alpha
				+ (invert ? AngleMath.PI : 0);
		double startAngle = AngleMath.rad2deg(startAngleRad);
		double endAngle = 360 - AngleMath.rad2deg(2 * alpha);
		
		Shape botShape = new Arc2D.Double(drawingX, drawingY, robotRadius * 2, robotRadius * 2, startAngle,
				endAngle, Arc2D.CHORD);
				
		if (fill)
		{
			g.fill(botShape);
		} else
		{
			g.draw(botShape);
		}
		
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(2));
		final IVector2 kickerPos = tool.transformToGuiCoordinates(
				GeoMath.getBotKickerPos(pos, angle, center2DribblerDist - 20), invert);
		g.drawLine(drawingX + robotRadius, drawingY + robotRadius, (int) kickerPos.x(), (int) kickerPos.y());
		
		// --- check and determinate id-length for margin ---
		int idX;
		int idY;
		if (id.length() == 1)
		{
			idX = drawingX + (int) (robotRadius * 0.5);
			idY = drawingY + (int) (robotRadius * 1.5);
		} else if (id.length() == 2)
		{
			idX = drawingX + (int) (robotRadius * 0.1);
			idY = drawingY + (int) (robotRadius * 1.5);
		} else
		{
			return;
		}
		
		// --- draw id and direction-sign ---
		g.setColor(fontColor);
		g.setFont(new Font("Courier", Font.BOLD, (int) (robotRadius * 1.5)));
		g.drawString(id, idX, idY);
	}
	
	
	
	public final void setCenter2DribblerDist(final double center2DribblerDist)
	{
		this.center2DribblerDist = center2DribblerDist;
	}
	
	
	
	@Override
	public final void setColor(final Color color)
	{
		this.color = color;
	}
	
	
	
	public final void setFontColor(final Color fontColor)
	{
		this.fontColor = fontColor;
	}
	
	
	
	public final void setId(final String id)
	{
		this.id = id;
	}
	
	
	
	public final void setRadius(final double radius)
	{
		this.radius = radius;
	}
	
	
	
	public final void setFill(final boolean fill)
	{
		this.fill = fill;
	}
}
