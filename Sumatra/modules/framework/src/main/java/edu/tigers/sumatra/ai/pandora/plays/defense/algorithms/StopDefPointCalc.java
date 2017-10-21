/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.defense.algorithms;

import java.util.Map;
import java.util.Map.Entry;

import edu.tigers.sumatra.ai.metis.defense.data.DefensePoint;
import edu.tigers.sumatra.ai.pandora.roles.defense.DefenderRole;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.PenaltyArea;



public class StopDefPointCalc
{
	
	
	public static void modifyDistributionForStop(final IVector2 ballPos,
			final Map<DefenderRole, DefensePoint> defenderDistribution)
	{
		for (Entry<DefenderRole, DefensePoint> entry : defenderDistribution.entrySet())
		{
			DefensePoint defPoint = entry.getValue();
			DefenderRole defRole = entry.getKey();
			
			defPoint.set(modifyPositionForStopSituation(ballPos, defPoint, defPoint.getProtectAgainst()));
			defenderDistribution.put(defRole, defPoint);
		}
	}
	
	
	
	public static void modifyDistributionForBallPlacement(final IVector2 ballPos, final IVector2 placementPos,
			final Map<DefenderRole, DefensePoint> defenderDistribution)
	{
		for (Entry<DefenderRole, DefensePoint> entry : defenderDistribution.entrySet())
		{
			DefensePoint defPoint = entry.getValue();
			DefenderRole defRole = entry.getKey();
			
			defPoint.set(modifyPositionForStopSituation(ballPos, defPoint, defPoint.getProtectAgainst()));
			defPoint.set(modifyPositionForPlacementSituation(placementPos, defPoint, defPoint.getProtectAgainst()));
			
			defenderDistribution.put(defRole, defPoint);
		}
	}
	
	
	
	private static IVector2 modifyPositionForPlacementSituation(final IVector2 placementPos, final IVector2 oldPosition,
			final IVector2 protectAgainst)
	{
		IVector2 newDefPointPosition = new Vector2(oldPosition);
		
		double stopDistance = Geometry.getBotToBallDistanceStop() + (1.5f * Geometry.getBotRadius());
		Circle forbiddenCircle = Circle.getNewCircle(placementPos, stopDistance);
		
		if (forbiddenCircle.isPointInShape(newDefPointPosition))
		{
			newDefPointPosition = GeoMath.stepAlongLine(placementPos, newDefPointPosition, stopDistance);
		}
		
		return newDefPointPosition;
	}
	
	
	
	public static IVector2 modifyPositionForStopSituation(final IVector2 ballPos, final IVector2 oldPosition,
			final IVector2 protectAgainst)
	{
		
		IVector2 newDefPointPosition = new Vector2(oldPosition);
		
		double stopDistance = Geometry.getBotToBallDistanceStop() + (5f * Geometry.getBotRadius());
		Circle forbiddenCircle = Circle.getNewCircle(ballPos, stopDistance);
		double penAreaMargin = Geometry.getBotRadius() + Geometry.getPenaltyAreaMargin();
		PenaltyArea penAreaOur = Geometry.getPenaltyAreaOur();
		
		// if ((newDefPointPosition.x() > ZoneDefenseCalc.getMaxXBotBlockingDefender()))
		// {
		// newDefPointPosition = new Vector2(ZoneDefenseCalc.getMaxXBotBlockingDefender(), newDefPointPosition.y());
		// }
		
		if (forbiddenCircle.isPointInShape(newDefPointPosition))
		{
			newDefPointPosition = GeoMath.stepAlongLine(ballPos, newDefPointPosition, stopDistance);
		}
		
		if (protectAgainst != null)
		{
			
			newDefPointPosition = penAreaOur.nearestPointOutside(newDefPointPosition, protectAgainst, penAreaMargin);
		} else
		{
			
			newDefPointPosition = penAreaOur.nearestPointOutside(newDefPointPosition, penAreaMargin);
		}
		
		return newDefPointPosition;
	}
}
