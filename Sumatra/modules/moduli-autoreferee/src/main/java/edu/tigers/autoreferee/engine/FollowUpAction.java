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

import java.util.Optional;

import edu.tigers.autoreferee.engine.states.impl.StopState;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;



public class FollowUpAction
{
	
	
	public enum EActionType
	{
		
		INDIRECT_FREE,
		
		DIRECT_FREE,
		
		KICK_OFF,
		
		FORCE_START,
		
		PENALTY
	}
	
	private final ETeamColor	teamInFavor;
	private final EActionType	actionType;
	private final IVector2		newBallPos;
	
	
	
	public FollowUpAction(final EActionType actionType, final ETeamColor teamInFavor,
			final IVector2 newBallPos)
	{
		if (teamInFavor != null)
		{
			this.teamInFavor = teamInFavor;
		} else
		{
			this.teamInFavor = ETeamColor.NEUTRAL;
		}
		this.actionType = actionType;
		this.newBallPos = newBallPos;
	}
	
	
	
	public ETeamColor getTeamInFavor()
	{
		return teamInFavor;
	}
	
	
	
	public EActionType getActionType()
	{
		return actionType;
	}
	
	
	
	public Optional<IVector2> getNewBallPosition()
	{
		return Optional.ofNullable(newBallPos);
	}
	
	
	
	public Command getCommand()
	{
		switch (actionType)
		{
			case DIRECT_FREE:
				return teamInFavor == ETeamColor.BLUE ? Command.DIRECT_FREE_BLUE : Command.DIRECT_FREE_YELLOW;
			case FORCE_START:
				return Command.FORCE_START;
			case INDIRECT_FREE:
				return teamInFavor == ETeamColor.BLUE ? Command.INDIRECT_FREE_BLUE : Command.INDIRECT_FREE_YELLOW;
			case KICK_OFF:
				return teamInFavor == ETeamColor.BLUE ? Command.PREPARE_KICKOFF_BLUE : Command.PREPARE_KICKOFF_YELLOW;
			default:
				throw new IllegalArgumentException("Please add the following action type to the switch case: " + actionType);
		}
	}
	
	
	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}
		
		if (other instanceof FollowUpAction)
		{
			FollowUpAction otherAction = (FollowUpAction) other;
			return (otherAction.actionType == actionType)
					&& (otherAction.teamInFavor == teamInFavor)
					&& otherAction.newBallPos.equals(newBallPos);
		}
		return false;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + actionType.hashCode();
		result = (prime * result) + ((teamInFavor == null) ? 0 : teamInFavor.hashCode());
		result = (prime * result) + ((newBallPos == null) ? 0 : newBallPos.hashCode());
		return result;
	}
}
