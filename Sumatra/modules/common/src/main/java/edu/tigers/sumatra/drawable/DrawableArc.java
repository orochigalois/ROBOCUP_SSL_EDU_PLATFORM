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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.circle.Arc;



@Persistent
public class DrawableArc extends Arc implements IDrawableShape
{
	private final Color color;
	
	
	
	protected DrawableArc()
	{
		super(AVector2.ZERO_VECTOR, 1, 0, 1);
		color = Color.red;
	}
	
	
	
	public DrawableArc(final Arc arc, final Color color)
	{
		super(arc);
		this.color = color;
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		final IVector2 transBotPos = tool.transformToGuiCoordinates(center(), invert);
		double radius = tool.scaleXLength(radius());
		int drawingX = (int) (transBotPos.x() - radius);
		int drawingY = (int) (transBotPos.y() - radius);
		
		double startAngle = AngleMath
				.rad2deg((getStartAngle() + tool.getFieldTurn().getAngle()) - (AngleMath.PI_HALF * (invert ? -1 : 1)));
		double extendAngle = AngleMath.rad2deg(getRotation());
		Shape arcShape = new Arc2D.Double(drawingX, drawingY, radius * 2, radius * 2, startAngle,
				extendAngle, Arc2D.PIE);
		g.setColor(color);
		g.draw(arcShape);
	}
	
}
