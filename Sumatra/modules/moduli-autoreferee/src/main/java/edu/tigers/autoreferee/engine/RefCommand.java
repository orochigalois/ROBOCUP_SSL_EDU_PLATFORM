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

import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest.CardInfo.CardType;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;



public class RefCommand
{
	
	public enum CommandType
	{
		
		COMMAND,
		
		CARD
	}
	
	
	private final CommandType	type;
	
	private final Command		command;
	private final IVector2		kickPos;
	
	private final CardType		cardType;
	private final ETeamColor	cardTeam;
	
	
	
	public RefCommand(final Command command)
	{
		this(command, null);
	}
	
	
	
	public RefCommand(final Command command, final IVector2 kickPos)
	{
		this(CommandType.COMMAND, command, kickPos, null, null);
	}
	
	
	
	public RefCommand(final CardType cardType, final ETeamColor cardTeam)
	{
		this(CommandType.CARD, null, null, cardType, cardTeam);
	}
	
	
	private RefCommand(final CommandType type, final Command command, final IVector2 kickPos,
			final CardType cardType, final ETeamColor cardTeam)
	{
		this.type = type;
		
		this.command = command;
		this.kickPos = kickPos;
		
		this.cardType = cardType;
		this.cardTeam = cardTeam;
	}
	
	
	
	public CommandType getType()
	{
		return type;
	}
	
	
	
	public Command getCommand()
	{
		return command;
	}
	
	
	
	public Optional<IVector2> getKickPos()
	{
		return Optional.ofNullable(kickPos);
	}
	
	
	
	public CardType getCardType()
	{
		return cardType;
	}
	
	
	
	public ETeamColor getCardTeam()
	{
		return cardTeam;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (this == obj)
		{
			return true;
		}
		if (obj instanceof RefCommand)
		{
			RefCommand other = (RefCommand) obj;
			return equalsCommand(other);
		}
		return false;
	}
	
	
	private boolean equalsCommand(final RefCommand other)
	{
		if (type != other.type)
		{
			return false;
		}
		
		switch (type)
		{
			case CARD:
				return (cardType == other.cardType) && (cardTeam == other.cardTeam);
			case COMMAND:
				if (command != other.command)
				{
					return false;
				}
				if (kickPos == null)
				{
					if (other.kickPos == null)
					{
						return true;
					}
					return false;
				}
				return kickPos.equals(other.kickPos);
		}
		return false;
	}
	
	
	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		
		result = (prime * result) + type.hashCode();
		switch (type)
		{
			case CARD:
				result = (prime * result) + cardType.hashCode();
				result = (prime * result) + cardTeam.hashCode();
				break;
			case COMMAND:
				result = (prime * result) + command.hashCode();
				if (kickPos != null)
				{
					result = (prime * result) + kickPos.hashCode();
				}
				break;
		}
		return result;
	}
}
