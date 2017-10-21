/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.frames;

import edu.tigers.sumatra.ai.data.AICom;
import edu.tigers.sumatra.ai.data.MultiTeamMessage;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.referee.RefereeMsgTeamSpec;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class BaseAiFrame
{
	private final WorldFrameWrapper	worldFrameWrapper;
	private final boolean				newRefereeMsg;
	private final ETeamColor			teamColor;
	private final AICom					aiCom;
	
	private final WorldFrame			worldFrame;
	private final RefereeMsgTeamSpec	refereeMsg;
	
	private MultiTeamMessage			multiTeamMessage	= null;
	
	
	private AIInfoFrame					prevFrame;
	
	
	
	public BaseAiFrame(final WorldFrameWrapper worldFrameWrapper, final boolean newRefereeMsg,
			final AIInfoFrame prevFrame, final ETeamColor teamColor)
	{
		this.worldFrameWrapper = worldFrameWrapper;
		this.prevFrame = prevFrame;
		this.teamColor = teamColor;
		this.newRefereeMsg = newRefereeMsg;
		aiCom = new AICom();
		
		worldFrame = worldFrameWrapper.getWorldFrame(teamColor);
		refereeMsg = new RefereeMsgTeamSpec(worldFrameWrapper.getRefereeMsg(), teamColor);
	}
	
	
	
	public BaseAiFrame(final BaseAiFrame original)
	{
		worldFrameWrapper = original.worldFrameWrapper;
		prevFrame = original.prevFrame;
		teamColor = original.teamColor;
		aiCom = original.aiCom;
		newRefereeMsg = original.newRefereeMsg;
		
		worldFrame = worldFrameWrapper.getWorldFrame(teamColor);
		refereeMsg = original.refereeMsg;
		
	}
	
	
	
	public void cleanUp()
	{
		prevFrame = null;
	}
	
	
	
	public final BotID getKeeperId()
	{
		return BotID.createBotId(refereeMsg.getTeamInfoTigers().getGoalie(), teamColor);
	}
	
	
	
	public final BotID getKeeperFoeId()
	{
		return BotID.createBotId(refereeMsg.getTeamInfoThem().getGoalie(), teamColor.opposite());
	}
	
	
	
	public WorldFrame getWorldFrame()
	{
		return worldFrame;
	}
	
	
	
	public RefereeMsgTeamSpec getRefereeMsg()
	{
		return refereeMsg;
	}
	
	
	
	public AIInfoFrame getPrevFrame()
	{
		return prevFrame;
	}
	
	
	
	public final ETeamColor getTeamColor()
	{
		return teamColor;
	}
	
	
	
	public AICom getAICom()
	{
		return aiCom;
	}
	
	
	
	public final SimpleWorldFrame getSimpleWorldFrame()
	{
		return worldFrameWrapper.getSimpleWorldFrame();
	}
	
	
	
	public MultiTeamMessage getMultiTeamMessage()
	{
		return multiTeamMessage;
	}
	
	
	
	public void setMultiTeamMessage(final MultiTeamMessage message)
	{
		multiTeamMessage = message;
	}
	
	
	
	public final WorldFrameWrapper getWorldFrameWrapper()
	{
		return worldFrameWrapper;
	}
	
	
	
	public final boolean isNewRefereeMsg()
	{
		return newRefereeMsg;
	}
}
