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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.referee.TeamConfig;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.Goal;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.PenaltyArea;



public class NGeometry
{
	
	public static class PointDistanceComparator implements Comparator<IVector2>
	{
		
		private final IVector2	pos;
		
		
		
		public PointDistanceComparator(final IVector2 pos)
		{
			this.pos = pos;
		}
		
		
		@Override
		public int compare(final IVector2 p1, final IVector2 p2)
		{
			double distTo1 = GeoMath.distancePP(pos, p1);
			double distTo2 = GeoMath.distancePP(pos, p2);
			
			if (distTo1 < distTo2)
			{
				return -1;
			} else if (distTo1 > distTo2)
			{
				return 1;
			}
			return 0;
		}
		
	}
	
	
	public static class BotDistanceComparator implements Comparator<ITrackedBot>
	{
		
		private PointDistanceComparator	comparator;
		
		
		
		public BotDistanceComparator(final IVector2 pos)
		{
			comparator = new PointDistanceComparator(pos);
		}
		
		
		@Override
		public int compare(final ITrackedBot o1, final ITrackedBot o2)
		{
			return comparator.compare(o1.getPos(), o2.getPos());
		}
		
	}
	
	
	
	public static Line getGoalLine(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getGoalLineOur();
		}
		return Geometry.getGoalLineTheir();
	}
	
	
	
	public static Goal getGoal(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getGoalOur();
		}
		return Geometry.getGoalTheir();
	}
	
	
	
	public static Rectangle getField()
	{
		return Geometry.getField();
	}
	
	
	
	public static double getGoalSize()
	{
		return Geometry.getGoalSize();
	}
	
	
	
	public static Rectangle getFieldSide(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getHalfOur();
		}
		return Geometry.getHalfTheir();
	}
	
	
	
	public static IVector2 getCenter()
	{
		return Geometry.getCenter();
	}
	
	
	
	public static PenaltyArea getPenaltyArea(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getPenaltyAreaOur();
		}
		return Geometry.getPenaltyAreaTheir();
	}
	
	
	
	public static IVector2 getPenaltyMark(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getPenaltyMarkOur();
		}
		return Geometry.getPenaltyMarkTheir();
	}
	
	
	
	public static Rectangle getPenaltyKickArea(final ETeamColor color)
	{
		if (color == TeamConfig.getLeftTeam())
		{
			return Geometry.getPenaltyKickAreaOur();
		}
		return Geometry.getPenaltyKickAreaTheir();
	}
	
	
	
	public static List<PenaltyArea> getPenaltyAreas()
	{
		return Arrays.asList(Geometry.getPenaltyAreaOur(), Geometry.getPenaltyAreaTheir());
	}
	
	
	
	public static ETeamColor getTeamOfClosestGoalLine(final IVector2 pos)
	{
		Line goalLineBlue = getGoalLine(ETeamColor.BLUE);
		Line goalLineYellow = getGoalLine(ETeamColor.YELLOW);
		
		if (GeoMath.distancePL(pos, goalLineBlue) < GeoMath
				.distancePL(pos, goalLineYellow))
		{
			return ETeamColor.BLUE;
		}
		return ETeamColor.YELLOW;
	}
	
	
	
	public static IVector2 getClosestCorner(final IVector2 pos)
	{
		return getField().getCorners().stream().sorted(new PointDistanceComparator(pos)).findFirst().get();
	}
	
	
	
	public static boolean ballInsideGoal(final IVector2 pos)
	{
		return ballInsideGoal(pos, 0);
	}
	
	
	
	public static boolean ballInsideGoal(final IVector2 pos, final double goalDepthMargin)
	{
		Rectangle field = getField();
		double absXPos = Math.abs(pos.x());
		double absYPos = Math.abs(pos.y());
		
		boolean xPosCorrect = (absXPos > (field.getxExtend() / 2))
				&& (absXPos < ((field.getxExtend() / 2) + Geometry.getGoalDepth() + goalDepthMargin));
		boolean yPosCorrect = absYPos < (Geometry.getGoalSize() / 2);
		return xPosCorrect && yPosCorrect;
	}
	
	
	
	public static boolean posInsidePenaltyArea(final IVector2 pos)
	{
		return getPenaltyAreas().stream().anyMatch(penArea -> penArea.isPointInShape(pos));
	}
}
