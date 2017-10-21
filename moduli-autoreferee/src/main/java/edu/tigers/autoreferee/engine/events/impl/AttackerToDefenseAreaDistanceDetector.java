/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.tigers.autoreferee.AutoRefUtil.ColorFilter;
import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.AutoRefMath;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.FollowUpAction.EActionType;
import edu.tigers.autoreferee.engine.NGeometry;
import edu.tigers.autoreferee.engine.events.DistanceViolation;
import edu.tigers.autoreferee.engine.events.EGameEvent;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.PenaltyArea;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class AttackerToDefenseAreaDistanceDetector extends APreparingGameEventDetector
{
	private static final int							priority								= 1;
	
	
	private static final double						MIN_ATTACKER_DEFENSE_DISTANCE	= 200;
	private static final Set<EGameStateNeutral>	ALLOWED_PREVIOUS_STATES;
	
	private boolean										active								= false;
	
	static
	{
		Set<EGameStateNeutral> states = EnumSet.of(
				EGameStateNeutral.INDIRECT_KICK_BLUE, EGameStateNeutral.INDIRECT_KICK_YELLOW,
				EGameStateNeutral.DIRECT_KICK_BLUE, EGameStateNeutral.DIRECT_KICK_YELLOW,
				EGameStateNeutral.KICKOFF_BLUE, EGameStateNeutral.KICKOFF_YELLOW);
		ALLOWED_PREVIOUS_STATES = Collections.unmodifiableSet(states);
	}
	
	
	
	public AttackerToDefenseAreaDistanceDetector()
	{
		super(EGameStateNeutral.RUNNING);
	}
	
	
	@Override
	public int getPriority()
	{
		return priority;
	}
	
	
	@Override
	protected void prepare(final IAutoRefFrame frame)
	{
		List<EGameStateNeutral> stateHistory = frame.getStateHistory();
		
		if ((stateHistory.size() > 1) && ALLOWED_PREVIOUS_STATES.contains(stateHistory.get(1)))
		{
			active = true;
		} else
		{
			active = false;
		}
	}
	
	
	@Override
	public Optional<IGameEvent> doUpdate(final IAutoRefFrame frame, final List<IGameEvent> violations)
	{
		if (active)
		{
			active = false;
			
			EGameStateNeutral lastGameState = frame.getStateHistory().get(1);
			ETeamColor attackerColor = lastGameState.getTeamColor();
			
			return new Evaluator(frame, attackerColor).evaluate();
		}
		
		return Optional.empty();
	}
	
	
	@Override
	public void doReset()
	{
	}
	
	
	private static class Evaluator
	{
		private final double							requiredMargin	= MIN_ATTACKER_DEFENSE_DISTANCE + Geometry.getBotRadius();
		
		private final IAutoRefFrame				frame;
		private final ETeamColor					attackerColor;
		private final PenaltyArea					defenderPenArea;
		
		private final TrackedBall					ball;
		private final Collection<ITrackedBot>	bots;
		
		
		public Evaluator(final IAutoRefFrame frame, final ETeamColor attackerColor)
		{
			this.frame = frame;
			this.attackerColor = attackerColor;
			defenderPenArea = NGeometry.getPenaltyArea(attackerColor.opposite());
			ball = frame.getWorldFrame().getBall();
			bots = frame.getWorldFrame().getBots().values();
		}
		
		
		public Optional<IGameEvent> evaluate()
		{
			Optional<ITrackedBot> optOffender = bots.stream()
					.filter(ColorFilter.get(attackerColor))
					.filter(bot -> defenderPenArea.isPointInShape(bot.getPos(), requiredMargin))
					.findFirst();
			
			return optOffender.map(offender -> buildViolation(offender));
		}
		
		
		private DistanceViolation buildViolation(final ITrackedBot offender)
		{
			double distance = AutoRefMath
					.distanceToNearestPointOutside(defenderPenArea, requiredMargin, offender.getPos());
			
			IVector2 kickPos = AutoRefMath.getClosestFreekickPos(ball.getPos(), offender.getTeamColor().opposite());
			FollowUpAction followUp = new FollowUpAction(EActionType.INDIRECT_FREE, attackerColor.opposite(), kickPos);
			return new DistanceViolation(EGameEvent.ATTACKER_TO_DEFENCE_AREA, frame.getTimestamp(),
					offender.getBotId(), followUp, distance);
		}
	}
	
}
