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

import java.util.Comparator;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;



@Persistent(version = 3)
public class FoeBotData
{
	
	public static final Comparator<? super FoeBotData>	DANGER_COMPARATOR	= new DangerComparator();
	
	public static final Comparator<? super FoeBotData>	ANGLE_COMPARATOR	= new AngleComparator();
	
	private final ITrackedBot									trackedFoeBot;
	
	private IVector2												bot2goal;
	private IVector2												bot2goalNearestToGoal;
	private IVector2												bot2goalNearestToBot;
	
	private IVector2												ball2bot;
	private IVector2												ball2botNearestToBall;
	private IVector2												ball2botNearestToBot;
	
	private double													goalAngle;
	
	private List<IntersectionPoint>							bot2goalIntersecsBot2bot;
	private List<IntersectionPoint>							bot2goalIntersecsBall2bot;
	private List<IntersectionPoint>							ball2botIntersecsBot2bot;
	private List<IntersectionPoint>							ball2botIntersecsBot2goal;
	
	private boolean												posessesBall		= false;
	private boolean												bestRedirector		= false;
	
	
	@SuppressWarnings("unused")
	private FoeBotData()
	{
		// berkeley support
		trackedFoeBot = null;
		
		bot2goal = null;
		bot2goalNearestToGoal = null;
		bot2goalNearestToBot = null;
		
		ball2bot = null;
		ball2botNearestToBall = null;
		ball2botNearestToBot = null;
		
		goalAngle = 0.;
		
		bot2goalIntersecsBot2bot = null;
		bot2goalIntersecsBall2bot = null;
		ball2botIntersecsBot2bot = null;
		ball2botIntersecsBot2goal = null;
	}
	
	
	
	public FoeBotData(final ITrackedBot foeBot)
	{
		trackedFoeBot = foeBot;
		
		bot2goal = null;
		bot2goalNearestToGoal = null;
		bot2goalNearestToBot = null;
		
		goalAngle = 0.;
		
		ball2bot = null;
		ball2botNearestToBall = null;
		ball2botNearestToBot = null;
		
		bot2goalIntersecsBot2bot = null;
		bot2goalIntersecsBall2bot = null;
		ball2botIntersecsBot2bot = null;
		ball2botIntersecsBot2goal = null;
	}
	
	
	
	public FoeBotData setBot2goal(final IVector2 bot2goal, final IVector2 bot2goalNearestToBot,
			final IVector2 bot2goalNearestToGoal)
	{
		this.bot2goal = bot2goal;
		this.bot2goalNearestToBot = bot2goalNearestToBot;
		this.bot2goalNearestToGoal = bot2goalNearestToGoal;
		
		return this;
	}
	
	
	
	public FoeBotData setBall2bot(final IVector2 ball2Bot, final IVector2 ball2botNearestToBall,
			final IVector2 ball2botNearestToBot)
	{
		ball2bot = ball2Bot;
		this.ball2botNearestToBall = ball2botNearestToBall;
		this.ball2botNearestToBot = ball2botNearestToBot;
		
		return this;
	}
	
	
	
	public FoeBotData setBot2goalIntersecsBot2bot(final List<IntersectionPoint> bot2goalIntersecsBot2bot)
	{
		this.bot2goalIntersecsBot2bot = bot2goalIntersecsBot2bot;
		
		return this;
	}
	
	
	
	public FoeBotData setBot2goalIntersecsBall2bot(final List<IntersectionPoint> bot2goalIntersecsBall2bot)
	{
		this.bot2goalIntersecsBall2bot = bot2goalIntersecsBall2bot;
		
		return this;
	}
	
	
	
	public FoeBotData setBall2botIntersecsBot2bot(final List<IntersectionPoint> ball2botIntersecsBot2bot)
	{
		this.ball2botIntersecsBot2bot = ball2botIntersecsBot2bot;
		
		return this;
	}
	
	
	
	public FoeBotData setBall2botIntersecsBot2goal(final List<IntersectionPoint> ball2botIntersecsBot2goal)
	{
		this.ball2botIntersecsBot2goal = ball2botIntersecsBot2goal;
		
		return this;
	}
	
	
	
	public FoeBotData setPossessesBall(final boolean possessesBall)
	{
		posessesBall = possessesBall;
		
		return this;
	}
	
	
	
	public ITrackedBot getFoeBot()
	{
		return trackedFoeBot;
	}
	
	
	
	public IVector2 getBot2goal()
	{
		return bot2goal;
	}
	
	
	
	public IVector2 getBot2goalNearestToGoal()
	{
		return bot2goalNearestToGoal;
	}
	
	
	
	public IVector2 getBot2goalNearestToBot()
	{
		return bot2goalNearestToBot;
	}
	
	
	
	public IVector2 getBall2bot()
	{
		return ball2bot;
	}
	
	
	
	public IVector2 getBall2botNearestToBall()
	{
		return ball2botNearestToBall;
	}
	
	
	
	public IVector2 getBall2botNearestToBot()
	{
		return ball2botNearestToBot;
	}
	
	
	
	public List<IntersectionPoint> getBot2goalIntersecsBot2bot()
	{
		return bot2goalIntersecsBot2bot;
	}
	
	
	
	public List<IntersectionPoint> getBot2goalIntersecsBall2bot()
	{
		return bot2goalIntersecsBall2bot;
	}
	
	
	
	public List<IntersectionPoint> getBall2botIntersecsBot2bot()
	{
		return ball2botIntersecsBot2bot;
	}
	
	
	
	public List<IntersectionPoint> getBall2botIntersecsBot2goal()
	{
		return ball2botIntersecsBot2goal;
	}
	
	
	
	public boolean posessesBall()
	{
		return posessesBall;
	}
	
	@SuppressWarnings("unused")
	private static class DistanceComparator implements Comparator<FoeBotData>
	{
		@Override
		public int compare(final FoeBotData bot1, final FoeBotData bot2)
		{
			if (bot1.posessesBall())
			{
				return 1;
			} else if (bot2.posessesBall())
			{
				return -1;
			} else
			{
				double distBot1 = GeoMath.distancePP(bot1.getFoeBot().getPos(), Geometry.getGoalOur().getGoalCenter());
				double distBot2 = GeoMath.distancePP(bot2.getFoeBot().getPos(), Geometry.getGoalOur().getGoalCenter());
				return (int) Math.signum(distBot1 - distBot2);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static class DangerComparator implements Comparator<FoeBotData>
	{
		private static double	halfFieldWidth		= Geometry.getFieldWidth() / 2.0;
		
		
		private static double	fieldWidthFactor	= 1.2;
		
		
		@Override
		public int compare(final FoeBotData bot1, final FoeBotData bot2)
		{
			if (bot1.posessesBall())
			{
				return 1;
			} else if (bot2.posessesBall())
			{
				return -1;
			} else
			{
				double distBot1 = GeoMath.distancePP(bot1.getFoeBot().getPos(), Geometry.getGoalOur().getGoalCenter());
				double distBot2 = GeoMath.distancePP(bot2.getFoeBot().getPos(), Geometry.getGoalOur().getGoalCenter());
				
				distBot1 *= (((1f - fieldWidthFactor) / halfFieldWidth) * Math.abs(bot1.getFoeBot().getPos().y()))
						+ fieldWidthFactor;
				distBot2 *= (((1f - fieldWidthFactor) / halfFieldWidth) * Math.abs(bot2.getFoeBot().getPos().y()))
						+ fieldWidthFactor;
				
				return (int) Math.signum(distBot1 - distBot2);
			}
		}
	}
	
	
	private static class AngleComparator implements Comparator<FoeBotData>
	{
		
		@Override
		public int compare(final FoeBotData bot1, final FoeBotData bot2)
		{
			return (int) Math.signum(bot1.getGoalAngle() - bot2.getGoalAngle());
		}
		
	}
	
	
	
	public double getGoalAngle()
	{
		return goalAngle;
	}
	
	
	
	public void setGoalAngle(final double goalAngle)
	{
		this.goalAngle = goalAngle;
	}
	
	
	
	public boolean isBestRedirector()
	{
		return bestRedirector;
	}
	
	
	
	public void setBestRedirector(final boolean bestRedirector)
	{
		this.bestRedirector = bestRedirector;
	}
	
}
