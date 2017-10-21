/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine;

import java.util.Collection;
import java.util.stream.Stream;

import edu.tigers.autoreferee.AutoRefConfig;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.PenaltyArea;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class AutoRefMath
{
	
	public static double	THROW_IN_DISTANCE						= 100;
	
	public static double	GOAL_KICK_DISTANCE					= 500;
	
	public static double	DEFENSE_AREA_GOALLINE_DISTANCE	= 600;
	
	
	public static double	OFFENSE_FREEKICK_DISTANCE			= 700;
	
	
	
	public static IVector2 getClosestCornerKickPos(final IVector2 pos)
	{
		IVector2 corner = NGeometry.getClosestCorner(pos);
		int ySide = (corner.y() > 0 ? -1 : 1);
		int xSide = (corner.x() > 0 ? -1 : 1);
		return corner.addNew(new Vector2(xSide * THROW_IN_DISTANCE, ySide * THROW_IN_DISTANCE));
	}
	
	
	
	public static IVector2 getClosestGoalKickPos(final IVector2 pos)
	{
		IVector2 corner = NGeometry.getClosestCorner(pos);
		int xSide = (corner.x() > 0 ? -1 : 1);
		int ySide = (corner.y() > 0 ? -1 : 1);
		return corner.addNew(new Vector2(xSide * GOAL_KICK_DISTANCE, ySide * THROW_IN_DISTANCE));
	}
	
	
	
	public static IVector2 getClosestFreekickPos(final IVector2 pos, final ETeamColor kickerColor)
	{
		Rectangle field = NGeometry.getField();
		ETeamColor goalColor = NGeometry.getTeamOfClosestGoalLine(pos);
		IVector2 newKickPos;
		if (goalColor == kickerColor)
		{
			newKickPos = getDefenseKickPos(pos);
		}
		newKickPos = getOffenseKickPos(pos);
		
		
		int xSide = newKickPos.x() > 0 ? 1 : -1;
		int ySide = newKickPos.y() > 0 ? 1 : -1;
		
		if (Math.abs(newKickPos.x()) > ((field.getxExtend() / 2) - THROW_IN_DISTANCE))
		{
			double newXPos = ((field.getxExtend() / 2) - THROW_IN_DISTANCE) * xSide;
			newKickPos = new Vector2(newXPos, newKickPos.y());
		}
		
		if (Math.abs(newKickPos.y()) > ((field.getyExtend() / 2) - THROW_IN_DISTANCE))
		{
			double newYPos = ((field.getyExtend() / 2) - THROW_IN_DISTANCE) * ySide;
			newKickPos = new Vector2(newKickPos.x(), newYPos);
		}
		
		return newKickPos;
	}
	
	
	private static IVector2 getOffenseKickPos(final IVector2 pos)
	{
		PenaltyArea penArea = NGeometry.getPenaltyArea(NGeometry.getTeamOfClosestGoalLine(pos));
		
		if (penArea.isPointInShape(pos, OFFENSE_FREEKICK_DISTANCE))
		{
			return penArea.nearestPointOutside(pos, OFFENSE_FREEKICK_DISTANCE);
		}
		return pos;
	}
	
	
	private static IVector2 getDefenseKickPos(final IVector2 pos)
	{
		int xSide = pos.x() > 0 ? -1 : 1;
		PenaltyArea penArea = NGeometry.getPenaltyArea(NGeometry.getTeamOfClosestGoalLine(pos));
		if (penArea.isPointInShape(pos, OFFENSE_FREEKICK_DISTANCE))
		{
			return getClosestGoalKickPos(pos)
					.addNew(new Vector2((DEFENSE_AREA_GOALLINE_DISTANCE - GOAL_KICK_DISTANCE) * xSide, 0));
		}
		return pos;
	}
	
	
	
	public static boolean botsAreOnCorrectSide(final Collection<ITrackedBot> bots)
	{
		Rectangle field = Geometry.getField();
		Stream<ITrackedBot> onFieldBots = bots.stream().filter(
				bot -> {
					return field.isPointInShape(bot.getPos(), Geometry.getBotRadius());
				});
		
		return onFieldBots.allMatch(bot -> {
			Rectangle side = NGeometry.getFieldSide(bot.getTeamColor());
			return side.isPointInShape(bot.getPos());
		});
	}
	
	
	
	public static boolean botsAreStationary(final Collection<ITrackedBot> bots)
	{
		return bots.stream().allMatch(
				bot -> bot.getVelByTime(0).getLength() < AutoRefConfig.getBotStationarySpeedThreshold());
	}
	
	
	
	public static boolean ballIsPlaced(final TrackedBall ball, final IVector2 destPos)
	{
		return ballIsPlaced(ball, destPos, AutoRefConfig.getBallPlacementAccuracy());
	}
	
	
	
	public static boolean ballIsPlaced(final TrackedBall ball, final IVector2 destPos, final double accuracy)
	{
		double dist = GeoMath.distancePP(ball.getPos(), destPos);
		if ((dist < accuracy) && ballIsStationary(ball))
		{
			return true;
		}
		return false;
	}
	
	
	
	public static boolean ballIsStationary(final TrackedBall ball)
	{
		return ball.getVel().getLength() < AutoRefConfig.getBallStationarySpeedThreshold();
	}
	
	
	
	public static boolean botStopDistanceIsCorrect(final SimpleWorldFrame frame)
	{
		Collection<ITrackedBot> bots = frame.getBots().values();
		IVector2 ballPos = frame.getBall().getPos();
		
		return bots.stream().allMatch(
				bot -> GeoMath.distancePP(bot.getPosByTime(0), ballPos) > Geometry.getBotToBallDistanceStop());
	}
	
	
	
	public static double distanceToNearestPointOutside(final PenaltyArea penArea, final IVector2 pos)
	{
		return distanceToNearestPointOutside(penArea, 0, pos);
	}
	
	
	
	public static double distanceToNearestPointOutside(final PenaltyArea penArea, final double margin, final IVector2 pos)
	{
		IVector2 nearestPointOutsideMargin = penArea.nearestPointOutside(pos, margin);
		return GeoMath.distancePP(pos, nearestPointOutsideMargin);
	}
}
