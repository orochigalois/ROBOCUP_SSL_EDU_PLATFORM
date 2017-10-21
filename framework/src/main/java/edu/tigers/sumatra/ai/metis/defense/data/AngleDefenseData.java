/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.defense.data;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class AngleDefenseData
{
	private static double		botRadius		= Geometry.getBotRadius();
	private static double		ballRadius		= Geometry.getBallRadius();
	
	private List<FoeBotGroup>	foeBotGroups	= new ArrayList<>();
	
	
	
	public static double getMinDefRadius()
	{
		return Geometry.getPenaltyAreaOur().getLengthOfPenaltyAreaFrontLineHalf()
				+ Geometry.getPenaltyAreaOur().getRadiusOfPenaltyArea() + Geometry.getPenaltyAreaMargin();
	}
	
	
	
	public static double getBotWidthAngle()
	{
		return 2 * Math.asin((botRadius + (ballRadius / 2)) / getMinDefRadius());
	}
	
	
	
	public double getBallAngle(final TrackedBall ballWorldFrame)
	{
		IVector2 goalCenter = Geometry.getGoalOur().getGoalCenter();
		IVector2 corner = new Vector2(-Geometry.getFieldLength() / 2, Geometry.getFieldWidth() / 2);
		
		IVector2 goal2Ball = DefenseAux.getBallPosDefense(ballWorldFrame).subtractNew(goalCenter);
		IVector2 refVector = corner.subtractNew(goalCenter);
		
		return GeoMath.angleBetweenVectorAndVector(refVector, goal2Ball);
	}
	
	
	
	public List<FoeBotGroup> getFoeBotGroups()
	{
		return foeBotGroups;
	}
	
	
	
	public void addFoeBotGroup(final FoeBotGroup newGroup)
	{
		foeBotGroups.add(newGroup);
	}
	
	
	
	public void addFoeBotGroups(final List<FoeBotGroup> newGroups)
	{
		foeBotGroups.addAll(newGroups);
	}
	
	
	
	public int nFoeBotGroups()
	{
		return foeBotGroups.size();
	}
}
