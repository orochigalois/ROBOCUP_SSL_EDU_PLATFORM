/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj.obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.drawable.IDrawableTool;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;



@Persistent
public class RectangleObstacle extends Rectangle implements IObstacle
{
	
	@SuppressWarnings("unused")
	private RectangleObstacle()
	{
		super();
	}
	
	
	
	public RectangleObstacle(final Rectangle rect)
	{
		super(rect);
	}
	
	
	@Override
	public boolean isPointCollidingWithObstacle(final IVector2 point, final double t)
	{
		return isPointInShape(point);
	}
	
	
	@Override
	public IVector2 nearestPointOutsideObstacle(final IVector2 point, final double t)
	{
		return nearestPointOutside(point);
	}
	
	
	@Override
	public void generateObstacleAvoidancePoints(final IVector2 curBotPos, final Random rnd,
			final List<IVector2> subPoints)
	{
		CircleObstacle circle = new CircleObstacle(new Circle(getMidPoint(), Math.max(getyExtend(), getxExtend())));
		circle.generateObstacleAvoidancePoints(curBotPos, rnd, subPoints);
	}
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		// --- from SSLVision-mm to java2d-coordinates ---
		final IVector2 topLeft = tool.transformToGuiCoordinates(topLeft(), invert);
		final IVector2 bottomRight = tool.transformToGuiCoordinates(bottomRight(), invert);
		
		int x = (int) (topLeft.x() < bottomRight.x() ? topLeft.x() : bottomRight.x());
		int y = (int) (topLeft.y() < bottomRight.y() ? topLeft.y() : bottomRight.y());
		
		int width = Math.abs((int) (bottomRight.x() - topLeft.x()));
		int height = Math.abs((int) (bottomRight.y() - topLeft.y()));
		
		g.setColor(Color.RED);
		g.drawRect(x, y, width, height);
	}
}
