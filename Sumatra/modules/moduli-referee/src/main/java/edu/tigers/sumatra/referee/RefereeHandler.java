/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.referee;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.log4j.Logger;

import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.sumatra.Referee.SSL_Referee;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.Referee.SSL_Referee.Point;
import edu.tigers.sumatra.Referee.SSL_Referee.Stage;
import edu.tigers.sumatra.cam.ACam;
import edu.tigers.sumatra.cam.IBallReplacer;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.model.SumatraModel;



public class RefereeHandler extends AReferee
{
	private static final Logger	log		= Logger.getLogger(RefereeHandler.class.getName());
	private final RefereeReceiver	receiver;
	private IBallReplacer			ballReplacer;
	private int							refMsgId	= 0;
														
														
	
	public RefereeHandler(final SubnodeConfiguration subconfig)
	{
		receiver = new RefereeReceiver(this);
		
	}
	
	
	@Override
	public void initModule() throws InitModuleException
	{
		try
		{
			ballReplacer = (ACam) SumatraModel.getInstance().getModule(ACam.MODULE_ID);
		} catch (ModuleNotFoundException err)
		{
			log.error("Could not find cam module", err);
		}
	}
	
	
	@Override
	public void startModule() throws StartModuleException
	{
		receiver.start();
	}
	
	
	@Override
	public void stopModule()
	{
		receiver.cleanup();
	}
	
	
	@Override
	public void deinitModule()
	{
		receiver.cleanup();
	}
	
	
	// --------------------------------------------------------------------------
	// --- on incoming msg ------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	protected void onNewExternalRefereeMsg(final SSL_Referee msg)
	{
		if (isReceiveExternalMsg())
		{
			if (isNewMessage(msg))
			{
				log.trace("Referee msg: " + msg.getCommand());
			}
			onNewRefereeMsg(msg);
		}
	}
	
	
	private void onNewRefereeMsg(final SSL_Referee msg)
	{
		TeamConfig.setKeeperIdYellow(msg.getYellow().getGoalie());
		TeamConfig.setKeeperIdBlue(msg.getBlue().getGoalie());
		notifyNewRefereeMsg(msg);
	}
	
	
	// --------------------------------------------------------------------------
	// --- send own msg ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void sendOwnRefereeMsg(final Command cmd, final int goalsBlue, final int goalsYellow, final int timeLeft,
			final long timestamp, final IVector2 placementPos)
	{
		SSL_Referee refMsg = createSSLRefereeMsg(cmd, goalsBlue, goalsYellow, timeLeft, refMsgId, timestamp,
				placementPos);
		refMsgId++;
		onNewRefereeMsg(refMsg);
	}
	
	
	
	public static SSL_Referee createSSLRefereeMsg(final Command cmd, final int goalsBlue, final int goalsYellow,
			final int timeLeft, final int refId, final long timestamp, final IVector2 placementPos)
	{
		
		
		SSL_Referee.TeamInfo.Builder teamBlueBuilder = SSL_Referee.TeamInfo.newBuilder();
		teamBlueBuilder.setGoalie(TeamConfig.getKeeperIdBlue());
		teamBlueBuilder.setName("Blue");
		teamBlueBuilder.setRedCards(1);
		teamBlueBuilder.setScore(goalsBlue);
		teamBlueBuilder.setTimeouts(4);
		teamBlueBuilder.setTimeoutTime(360);
		teamBlueBuilder.setYellowCards(3);
		
		SSL_Referee.TeamInfo.Builder teamYellowBuilder = SSL_Referee.TeamInfo.newBuilder();
		teamYellowBuilder.setGoalie(TeamConfig.getKeeperIdYellow());
		teamYellowBuilder.setName("Yellow");
		teamYellowBuilder.setRedCards(0);
		teamYellowBuilder.setScore(goalsYellow);
		teamYellowBuilder.setTimeouts(2);
		teamYellowBuilder.setTimeoutTime(65);
		teamYellowBuilder.setYellowCards(1);
		
		SSL_Referee.Builder builder = SSL_Referee.newBuilder();
		builder.setPacketTimestamp(timestamp);
		builder.setBlue(teamBlueBuilder.build());
		builder.setYellow(teamYellowBuilder.build());
		builder.setCommand(cmd);
		builder.setCommandCounter(refId);
		builder.setCommandTimestamp(timestamp);
		builder.setStageTimeLeft(timeLeft);
		builder.setStage(Stage.NORMAL_FIRST_HALF);
		
		if (placementPos != null)
		{
			Point.Builder point = Point.newBuilder();
			point.setX((float) placementPos.x());
			point.setY((float) placementPos.y());
			builder.setDesignatedPosition(point);
		}
		
		return builder.build();
	}
	
	
	
	public static RefereeMsg createRefereeMsg(final long frameTimestamp, final Command cmd, final int goalsBlue,
			final int goalsYellow,
			final int timeLeft, final int refId, final long timestamp, final Vector2 placementPos)
	{
		SSL_Referee sslReferee = createSSLRefereeMsg(cmd, goalsBlue, goalsYellow, timeLeft, refId, timestamp,
				placementPos);
		return new RefereeMsg(frameTimestamp, sslReferee);
	}
	
	
	@Override
	public void replaceBall(final IVector3 pos, final IVector3 vel)
	{
		ballReplacer.replaceBall(pos, vel);
	}
}
