/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.referee.TeamConfig;



@Persistent
public class WorldFrameWrapper
{
	private final SimpleWorldFrame								simpleWorldFrame;
	private final RefereeMsg										refereeMsg;
	private final ShapeMap											shapeMap;
																			
	private transient final Map<ETeamColor, WorldFrame>	worldFrames	= new HashMap<ETeamColor, WorldFrame>(2);
	private EGameStateNeutral										gameState	= EGameStateNeutral.UNKNOWN;
																							
																							
	@SuppressWarnings("unused")
	private WorldFrameWrapper()
	{
		simpleWorldFrame = null;
		refereeMsg = new RefereeMsg();
		shapeMap = new ShapeMap();
	}
	
	
	
	public WorldFrameWrapper(final SimpleWorldFrame swf, final RefereeMsg refereeMsg, final ShapeMap shapeMap)
	{
		assert refereeMsg != null;
		assert swf != null;
		simpleWorldFrame = swf;
		this.refereeMsg = refereeMsg;
		this.shapeMap = shapeMap;
	}
	
	
	
	public WorldFrameWrapper(final WorldFrameWrapper wfw)
	{
		simpleWorldFrame = wfw.simpleWorldFrame;
		refereeMsg = wfw.refereeMsg;
		shapeMap = new ShapeMap(wfw.shapeMap);
		worldFrames.putAll(wfw.worldFrames);
		gameState = wfw.gameState;
	}
	
	
	
	private WorldFrame createWorldFrame(final SimpleWorldFrame swf, final RefereeMsg refereeMsg,
			final ETeamColor teamColor)
	{
		final WorldFrame wf;
		// if (refereeMsg.getLeftTeam() == teamColor)
		if (TeamConfig.getLeftTeam() == teamColor)
		{
			wf = new WorldFrame(swf, teamColor, false);
		} else
		{
			// right team will be mirrored
			wf = new WorldFrame(swf.mirrorNew(), teamColor, true);
		}
		return wf;
	}
	
	
	
	public SimpleWorldFrame getSimpleWorldFrame()
	{
		return simpleWorldFrame;
	}
	
	
	
	public synchronized WorldFrame getWorldFrame(final ETeamColor teamColor)
	{
		WorldFrame wf = worldFrames.get(teamColor);
		if (wf == null)
		{
			wf = createWorldFrame(simpleWorldFrame, refereeMsg, teamColor);
			worldFrames.put(teamColor, wf);
		}
		return wf;
	}
	
	
	
	public final RefereeMsg getRefereeMsg()
	{
		return refereeMsg;
	}
	
	
	
	public final EGameStateNeutral getGameState()
	{
		return gameState;
	}
	
	
	
	public final ShapeMap getShapeMap()
	{
		return shapeMap;
	}
	
	
	
	public final void setGameState(final EGameStateNeutral gameState)
	{
		this.gameState = gameState;
	}
}
