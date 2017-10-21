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

import java.util.Optional;

import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public interface IGameEvent
{
	
	public EGameEvent getType();
	
	
	
	public EEventCategory getCategory();
	
	
	
	public long getTimestamp();
	
	
	
	public ETeamColor getResponsibleTeam();
	
	
	
	public Optional<BotID> getResponsibleBot();
	
	
	
	public Optional<CardPenalty> getCardPenalty();
	
	
	
	public String buildLogString();
	
	
	
	public FollowUpAction getFollowUpAction();
}
