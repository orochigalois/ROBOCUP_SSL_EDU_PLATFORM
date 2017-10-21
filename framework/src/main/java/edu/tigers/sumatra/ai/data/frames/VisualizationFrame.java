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

import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.data.BotAiInformation;
import edu.tigers.sumatra.ai.data.MatchStatistics;
import edu.tigers.sumatra.ai.data.OffensiveStrategy;
import edu.tigers.sumatra.ai.data.event.GameEvents;
import edu.tigers.sumatra.ai.lachesis.RoleFinderInfo;
import edu.tigers.sumatra.ai.metis.offense.data.OffensiveAction;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



@Persistent(version = 1)
public class VisualizationFrame
{
	private final ETeamColor							teamColor;
	
	private final boolean								inverted;
	
	private final ShapeMap								shapes;
	
	private transient WorldFrameWrapper				worldFrameWrapper	= null;
	
	private final MatchStatistics						matchStatistics;
	
	private final GameEvents							gameEvents;
	
	private final OffensiveStrategy					offensiveStrategy;
	private final Map<BotID, OffensiveAction>		offensiveActions	= new HashMap<>();
	private final Map<BotID, BotAiInformation>	aiInfos				= new HashMap<>();
	private final Map<EPlay, RoleFinderInfo>		roleFinderInfos	= new HashMap<>();
	
	
	@SuppressWarnings("unused")
	private VisualizationFrame()
	{
		teamColor = null;
		inverted = false;
		matchStatistics = null;
		gameEvents = null;
		offensiveStrategy = null;
		shapes = new ShapeMap();
	}
	
	
	
	public VisualizationFrame(final AIInfoFrame aiFrame)
	{
		shapes = new ShapeMap(aiFrame.getTacticalField().getDrawableShapes());
		worldFrameWrapper = aiFrame.getWorldFrameWrapper();
		teamColor = aiFrame.getTeamColor();
		inverted = aiFrame.getWorldFrame().isInverted();
		matchStatistics = aiFrame.getTacticalField().getStatistics();
		gameEvents = aiFrame.getTacticalField().getGameEvents();
		offensiveStrategy = aiFrame.getTacticalField().getOffensiveStrategy();
		offensiveActions.putAll(aiFrame.getTacticalField().getOffensiveActions());
		aiInfos.putAll(aiFrame.getTacticalField().getBotAiInformation());
		roleFinderInfos.putAll(aiFrame.getTacticalField().getRoleFinderInfos());
	}
	
	
	
	public VisualizationFrame(final VisualizationFrame aiFrame)
	{
		shapes = new ShapeMap(aiFrame.getShapes());
		worldFrameWrapper = new WorldFrameWrapper(aiFrame.worldFrameWrapper);
		teamColor = aiFrame.getTeamColor();
		inverted = aiFrame.getWorldFrame().isInverted();
		matchStatistics = aiFrame.getMatchStatistics();
		gameEvents = aiFrame.getGameEvents();
		offensiveStrategy = aiFrame.getOffensiveStrategy();
		offensiveActions.putAll(aiFrame.getOffensiveActions());
		aiInfos.putAll(aiFrame.getAiInfos());
		roleFinderInfos.putAll(aiFrame.getRoleFinderInfos());
	}
	
	
	
	public final long getTimestamp()
	{
		return worldFrameWrapper.getSimpleWorldFrame().getTimestamp();
	}
	
	
	
	public final ShapeMap getShapes()
	{
		return shapes;
	}
	
	
	
	public final RefereeMsg getLatestRefereeMsg()
	{
		return worldFrameWrapper.getRefereeMsg();
	}
	
	
	
	public final SimpleWorldFrame getSimpleWorldFrame()
	{
		return worldFrameWrapper.getSimpleWorldFrame();
	}
	
	
	
	public final ETeamColor getTeamColor()
	{
		return teamColor;
	}
	
	
	
	public final boolean isInverted()
	{
		return inverted;
	}
	
	
	
	public final MatchStatistics getMatchStatistics()
	{
		return matchStatistics;
	}
	
	
	
	public final GameEvents getGameEvents()
	{
		return gameEvents;
	}
	
	
	
	public final OffensiveStrategy getOffensiveStrategy()
	{
		return offensiveStrategy;
	}
	
	
	
	public final Map<BotID, OffensiveAction> getOffensiveActions()
	{
		return offensiveActions;
	}
	
	
	
	public final WorldFrame getWorldFrame()
	{
		return worldFrameWrapper.getWorldFrame(teamColor);
	}
	
	
	
	public final Map<BotID, BotAiInformation> getAiInfos()
	{
		return aiInfos;
	}
	
	
	
	public final Map<EPlay, RoleFinderInfo> getRoleFinderInfos()
	{
		return roleFinderInfos;
	}
	
	
	
	public final WorldFrameWrapper getWorldFrameWrapper()
	{
		return worldFrameWrapper;
	}
	
	
	
	public final void setWorldFrameWrapper(final WorldFrameWrapper worldFrameWrapper)
	{
		this.worldFrameWrapper = worldFrameWrapper;
	}
}
