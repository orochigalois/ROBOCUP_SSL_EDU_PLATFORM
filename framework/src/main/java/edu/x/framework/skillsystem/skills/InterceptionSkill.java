/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;


import java.util.List;

import edu.tigers.sumatra.ai.metis.offense.OffensiveConstants;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.x.framework.skillsystem.ESkill;



public class InterceptionSkill extends BlockSkill
{
	private IVector2	nearestEnemyBotPos	= null;
	private IVector2	lastValidDestination	= null;
	
	
	
	public InterceptionSkill()
	{
		super(ESkill.INTERCEPTION);
	}
	
	
	@Override
	protected IVector2 calcDefendingDestination()
	{
		if (nearestEnemyBotPos == null)
		{
			nearestEnemyBotPos = new Vector2(0, 0);
		}
		
		if (GeoMath.distancePP(nearestEnemyBotPos, getWorldFrame().getBall().getPos()) < 200)
		{
			getMatchCtrl().setDribblerSpeed(10000);
		} else
		{
			getMatchCtrl().setDribblerSpeed(0);
		}
		
		IVector2 destination;
		boolean overAccelerationNecessary = false;
		IVector2 intersectPoint;
		
		IVector2 ballPos = getWorldFrame().getBall().getPos();
		IVector2 botToBall = ballPos.subtractNew(nearestEnemyBotPos).normalizeNew();
		
		intersectPoint = ballPos.addNew(botToBall.multiplyNew(Geometry.getBotToBallDistanceStop()
				+ Geometry.getBotRadius() + OffensiveConstants.getInterceptionSkillSecurityDist()));
		
		destination = intersectPoint; // GeoMath.leadPointOnLine(getPos(), getWorldFrame().getBall().getPos(),
		// intersectPoint);
		
		double distance = GeoMath.distancePP(destination, getPos());
		
		// if we are already blocking the ball we can do the fine tuning: position on the exact shooting line and
		if (distance < (Geometry.getBotRadius() / 2.0))
		{
			overAccelerationNecessary = false;
		}
		
		if (overAccelerationNecessary)
		{
			destination = getAccelerationTarget(getWorldFrame().getTiger(getBot().getBotId()), destination);
		}
		
		
		// keep stop distance
		Circle forbiddenCircle = new Circle(ballPos, Geometry.getBotToBallDistanceStop()
				+ Geometry.getBotRadius());
		Circle driveCircle = new Circle(ballPos, Geometry.getBotToBallDistanceStop()
				+ Geometry.getBotRadius() + OffensiveConstants.getInterceptionSkillSecurityDist());
		
		if (isMoveTargetValid(destination) && forbiddenCircle.isLineSegmentIntersectingShape(getPos(), destination))
		{
			List<IVector2> circleIntersections = driveCircle.lineIntersections(new Line(getPos(), ballPos));
			IVector2 nextCirclePoint;
			if ((circleIntersections.size() >= 2) &&
					(circleIntersections.get(0).subtractNew(getPos()).getLength() > circleIntersections.get(1)
							.subtractNew(getPos()).getLength()))
			{
				nextCirclePoint = circleIntersections.get(1);
			} else if ((circleIntersections.size() >= 1))
			{
				nextCirclePoint = circleIntersections.get(0);
			} else
			{
				nextCirclePoint = new Vector2();
			}
			
			IVector2 wayLeftTarget = GeoMath.stepAlongCircle(nextCirclePoint, ballPos, -SumatraMath.PI / 8);
			IVector2 wayRightTarget = GeoMath.stepAlongCircle(nextCirclePoint, ballPos, SumatraMath.PI / 8);
			
			boolean takeLeft = destination.subtractNew(wayLeftTarget).getLength() < destination
					.subtractNew(wayRightTarget)
					.getLength();
			
			double angle = GeoMath.angleBetweenVectorAndVector(destination.subtractNew(ballPos),
					getPos().subtractNew(ballPos));
			for (int i = 0; i < 16; i++)
			{
				if (!isMoveTargetValid(
						GeoMath.stepAlongCircle(nextCirclePoint, ballPos, ((takeLeft ? -1 : 1) * angle) / 16)))
				{
					takeLeft = !takeLeft;
					break;
				}
			}
			
			if (takeLeft)
			{
				destination = wayLeftTarget;
			} else
			{
				destination = wayRightTarget;
			}
			
		}
		
		if (isMoveTargetValid(destination))
		{
			lastValidDestination = destination;
			return destination;
		}
		if (lastValidDestination == null)
		
		{
			lastValidDestination = getPos();
		}
		return lastValidDestination;
	}
	
	
	private boolean isMoveTargetValid(final IVector2 destination)
	{
		double marginPenalty = 350;
		if (!Geometry.getField().isPointInShape(destination))
		{
			return false;
		}
		if (Geometry.getPenaltyAreaOur().isPointInShape(destination, marginPenalty))
		{
			return false;
		}
		if (Geometry.getPenaltyAreaTheir().isPointInShape(destination, marginPenalty))
		{
			return false;
		}
		return true;
	}
	
	
	
	public void setNearestEnemyBotPos(final IVector2 nearestEnemyBot)
	{
		nearestEnemyBotPos = nearestEnemyBot;
	}
}
