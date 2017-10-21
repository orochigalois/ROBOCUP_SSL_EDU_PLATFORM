/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.calc;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.autoreferee.AutoRefConfig;
import edu.tigers.autoreferee.AutoRefFrame;
import edu.tigers.autoreferee.engine.NGeometry;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class PossibleGoalCalc implements IRefereeCalc
{
	
	public static final class PossibleGoal
	{
		private final long			timestamp;
		private final ETeamColor	goalColor;
		
		
		
		public PossibleGoal(final long timestamp, final ETeamColor goalColor)
		{
			this.timestamp = timestamp;
			this.goalColor = goalColor;
		}
		
		
		
		public long getTimestamp()
		{
			return timestamp;
		}
		
		
		
		public ETeamColor getGoalColor()
		{
			return goalColor;
		}
	}
	
	@Configurable(comment = "[degree] The angle by which the ball heading needs to change while inside the goal to count as goal")
	private static double	GOAL_BALL_CHANGE_ANGLE_DEGREE	= 45;
	
	private PossibleGoal		detectedGoal						= null;
	
	private long				tsOnGoalEntry						= 0;
	private IVector2			ballHeadingOnGoalEntry			= null;
	
	static
	{
		ConfigRegistration.registerClass("autoreferee", PossibleGoalCalc.class);
	}
	
	
	@Override
	public void process(final AutoRefFrame frame)
	{
		TrackedBall ball = frame.getWorldFrame().getBall();
		IVector2 ballPos = ball.getPos();
		
		if (ball.isOnCam() && NGeometry.ballInsideGoal(ballPos))
		{
			
			if (ballHeadingOnGoalEntry == null)
			{
				ballHeadingOnGoalEntry = ball.getVel();
				tsOnGoalEntry = frame.getTimestamp();
			}
			
			
			double angle = GeoMath.angleBetweenVectorAndVector(ballHeadingOnGoalEntry, ball.getVel());
			boolean ballHeadingChanged = angle > ((GOAL_BALL_CHANGE_ANGLE_DEGREE / 180) * Math.PI);
			
			boolean ballStationary = ballIsStationary(ball);
			
			if ((ballHeadingChanged || ballStationary) && (detectedGoal == null))
			{
				detectedGoal = new PossibleGoal(tsOnGoalEntry, getGoalColor(ballPos));
			}
			
		} else
		{
			detectedGoal = null;
			ballHeadingOnGoalEntry = null;
		}
		
		frame.setPossibleGoal(detectedGoal);
	}
	
	
	private ETeamColor getGoalColor(final IVector2 ballPos)
	{
		Line blueGoalLine = NGeometry.getGoalLine(ETeamColor.BLUE);
		Line yellowGoalLine = NGeometry.getGoalLine(ETeamColor.YELLOW);
		
		if (GeoMath.distancePL(ballPos, yellowGoalLine) < GeoMath.distancePL(ballPos, blueGoalLine))
		{
			return ETeamColor.YELLOW;
		}
		return ETeamColor.BLUE;
	}
	
	
	private static boolean ballIsStationary(final TrackedBall ball)
	{
		return ball.getVel().getLength() < AutoRefConfig.getBallStationarySpeedThreshold();
	}
	
}
