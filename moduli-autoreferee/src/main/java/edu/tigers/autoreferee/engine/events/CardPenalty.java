/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events;

import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest.CardInfo.CardType;
import edu.tigers.sumatra.ids.ETeamColor;



public class CardPenalty
{
	private final CardType		type;
	private final ETeamColor	cardTeam;
	
	
	
	public CardPenalty(final CardType type, final ETeamColor cardTeam)
	{
		this.type = type;
		this.cardTeam = cardTeam;
	}
	
	
	
	public CardType getType()
	{
		return type;
	}
	
	
	
	public ETeamColor getCardTeam()
	{
		return cardTeam;
	}
	
	
	
	public RefCommand toRefCommand()
	{
		return new RefCommand(type, cardTeam);
	}
}
