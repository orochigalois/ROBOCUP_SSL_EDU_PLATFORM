/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.remote.impl;

import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest.CardInfo;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest.CardInfo.CardTeam;
import edu.tigers.sumatra.Referee.SSL_Referee.Point;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector2;



public class RemoteControlProtobufBuilder
{
	private int	nextMsgId	= 0;
	
	
	
	public RemoteControlProtobufBuilder()
	{
	}
	
	
	
	public SSL_RefereeRemoteControlRequest buildRequest(final RefCommand cmd)
	{
		SSL_RefereeRemoteControlRequest.Builder reqBuilder = SSL_RefereeRemoteControlRequest.newBuilder();
		reqBuilder.setMessageId(nextMsgId++);
		
		switch (cmd.getType())
		{
			case CARD:
				handleCardCommand(cmd, reqBuilder);
				break;
			case COMMAND:
				handleRegularCommand(cmd, reqBuilder);
				break;
		}
		return reqBuilder.build();
	}
	
	
	private void handleCardCommand(final RefCommand cmd, final SSL_RefereeRemoteControlRequest.Builder builder)
	{
		CardInfo.Builder cardBuilder = CardInfo.newBuilder();
		cardBuilder.setType(cmd.getCardType());
		cardBuilder.setTeam(cmd.getCardTeam() == ETeamColor.BLUE ? CardTeam.TEAM_BLUE : CardTeam.TEAM_YELLOW);
		builder.setCard(cardBuilder.build());
	}
	
	
	private void handleRegularCommand(final RefCommand cmd, final SSL_RefereeRemoteControlRequest.Builder builder)
	{
		builder.setCommand(cmd.getCommand());
		cmd.getKickPos().ifPresent(point -> setPoint(builder, point));
	}
	
	
	private static void setPoint(final SSL_RefereeRemoteControlRequest.Builder reqBuilder, final IVector2 point)
	{
		Point.Builder pointBuilder = Point.newBuilder();
		pointBuilder.setX((float) point.x());
		pointBuilder.setY((float) point.y());
		reqBuilder.setDesignatedPosition(pointBuilder.build());
	}
}
