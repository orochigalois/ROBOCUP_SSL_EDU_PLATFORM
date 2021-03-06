/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.ball.collision;

import java.util.Optional;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.MathException;



public class LineCollision implements ICollisionObject
{
	private final ILine		obstacleLine;
	private final IVector2	vel;
	private final IVector2	normal;
	
	
	
	public LineCollision(final ILine obstacleLine, final IVector2 vel, final IVector2 normal)
	{
		this.obstacleLine = obstacleLine;
		this.vel = vel;
		this.normal = normal;
	}
	
	
	@Override
	public IVector2 getVel()
	{
		return vel;
	}
	
	
	@Override
	public Optional<ICollision> getCollision(final IVector3 prePos, final IVector3 postPos)
	{
		ILine stateLine = Line.newLine(prePos.getXYVector(), postPos.getXYVector());
		
		// pre and post identical
		IVector2 lp = GeoMath.leadPointOnLine(postPos.getXYVector(), obstacleLine);
		IVector2 lp2Pos = postPos.getXYVector().subtractNew(lp);
		if (lp2Pos.isZeroVector() || (GeoMath.angleBetweenVectorAndVector(lp2Pos, normal) < 0.1))
		{
			// point is outside
			return Optional.empty();
		}
		
		double dist2Line = lp2Pos.getLength();
		if (dist2Line > 50)
		{
			return Optional.empty();
		}
		
		IVector2 collisionPoint = lp;
		if (stateLine.directionVector().getLength() > 0.01)
		{
			try
			{
				collisionPoint = GeoMath.intersectionPoint(obstacleLine, stateLine);
			} catch (MathException e)
			{
			}
		}
		
		if (!obstacleLine.isPointOnLine(collisionPoint, 1))
		{
			return Optional.empty();
		}
		
		Collision collision = new Collision(collisionPoint, normal, getVel());
		return Optional.of(collision);
	}
}
