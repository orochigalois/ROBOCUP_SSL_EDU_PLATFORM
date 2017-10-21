/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.flyingBalls;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.Geometry;



public class FlyingBall
{
	private final IVector2	bottomPos;
	private IVector2			flyingPos		= new Vector2(0, 0);
	
	private double				flyingHeight	= 0;
	private double				distance			= 0;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public FlyingBall(final IVector2 ballPos)
	{
		bottomPos = new Vector2(ballPos);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void setFlyPositionAndCalculateFlyingHeight(final IVector2 flyPosition, final int camId)
	{
		flyingPos = flyPosition;
		final double bfX = Math.abs(flyingPos.x() - bottomPos.x());
		final double bfY = Math.abs(flyingPos.y() - bottomPos.y());
		final double bf = Math.sqrt((bfX * bfX) + (bfY * bfY));
		
		double prinX = Geometry.getCameraPrincipalPointX()[camId];
		double prinY = Geometry.getCameraPrincipalPointY()[camId];
		final double cfX = Math.abs(flyingPos.x() - prinX);
		final double cfY = Math.abs(flyingPos.y() - prinY);
		
		final double cf = Math.sqrt((cfX * cfX) + (cfY * cfY));
		
		double camHeight = Geometry.getCameraHeights()[camId];
		flyingHeight = (camHeight * bf) / (bf + cf);
	}
	
	
	
	public void calculateDistanceToStart(final IVector2 point)
	{
		if (Double.isInfinite(flyingPos.x()) || Double.isInfinite(flyingPos.y()))
		{
			throw new IllegalArgumentException(
					"FlyingBall: calculateDistanceToPoint is not possible, before setting the flying Position.");
		}
		
		final double x2 = Math.pow(Math.abs(point.x() - flyingPos.x()), 2);
		final double y2 = Math.pow(Math.abs(point.y() - flyingPos.y()), 2);
		distance = Math.sqrt(x2 + y2);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public double getDistance()
	{
		return distance;
	}
	
	
	
	public IVector2 getFlyingPosition()
	{
		return flyingPos;
	}
	
	
	
	public IVector2 getBottomPosition()
	{
		return bottomPos;
	}
	
	
	
	public double getFlyingHeight()
	{
		return flyingHeight;
	}
	
	
	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		str.append("###Flying Ball###\n");
		str.append("bottomPosX:    " + bottomPos.x() + "\n");
		str.append("bottomPosY:    " + bottomPos.y() + "\n");
		str.append("flyingPosX:    " + flyingPos.x() + "\n");
		str.append("flyingPosY:    " + flyingPos.y() + "\n");
		str.append("flyingHeight:  " + flyingHeight + "\n");
		
		return str.toString();
	}
}
