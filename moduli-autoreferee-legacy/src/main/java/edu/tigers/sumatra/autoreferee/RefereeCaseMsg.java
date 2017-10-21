/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.autoreferee;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class RefereeCaseMsg
{
	
	public enum EMsgType
	{
		
		OUT_OF_BOUNDS,
		
		PENALTY_PARTIAL,
		
		PENALTY_FULL,
		
		
		BALL_SPEED,
		
		
		BOT_SPEED_STOP,
		
		
		TOO_NEAR_TO_BALL,
		
		
		KICKOFF_PLACEMENT
	}
	
	private final ETeamColor	teamAtFault;
	private final EMsgType		msgType;
	
	
	// optional
	private BotID					botAtFault		= BotID.get();
	private String					additionalInfo	= "";
	
	
	
	public RefereeCaseMsg(final ETeamColor teamAtFault, final EMsgType msgType)
	{
		this.teamAtFault = teamAtFault;
		this.msgType = msgType;
	}
	
	
	
	public ETeamColor getTeamAtFault()
	{
		return teamAtFault;
	}
	
	
	
	public EMsgType getMsgType()
	{
		return msgType;
	}
	
	
	
	public final BotID getBotAtFault()
	{
		return botAtFault;
	}
	
	
	
	public final String getAdditionalInfo()
	{
		return additionalInfo;
	}
	
	
	
	public final void setAdditionalInfo(final String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}
	
	
	
	public final void setBotAtFault(final BotID botAtFault)
	{
		this.botAtFault = botAtFault;
	}
}
