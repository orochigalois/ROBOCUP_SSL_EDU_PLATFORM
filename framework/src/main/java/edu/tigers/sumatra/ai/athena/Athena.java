/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.athena;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.EAIControlState;
import edu.tigers.sumatra.ai.data.PlayStrategy;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.lachesis.Lachesis;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;



public class Athena
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger			log				= Logger.getLogger(Athena.class.getName());
																		
	
	private final Lachesis					lachesis;
													
	
	private AAthenaAdapter					athenaAdapter;
													
	private EAIControlState					controlState	= EAIControlState.MATCH_MODE;
																		
	private final List<IAIModeChanged>	observers		= new CopyOnWriteArrayList<IAIModeChanged>();
																		
																		
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Athena()
	{
		lachesis = new Lachesis();
		athenaAdapter = new MatchModeAthenaAdapter();
	}
	
	
	
	public void addObserver(final IAIModeChanged observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final IAIModeChanged observer)
	{
		observers.remove(observer);
	}
	
	
	private void notifyAiModeChanged(final EAIControlState mode)
	{
		synchronized (observers)
		{
			for (IAIModeChanged observer : observers)
			{
				observer.onAiModeChanged(mode);
			}
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- process --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public void changeMode(final EAIControlState mode)
	{
		switch (mode)
		{
			case EMERGENCY_MODE:
				athenaAdapter = new EmergencyModeAthenaAdapter();
				break;
			case MATCH_MODE:
			case MIXED_TEAM_MODE:
				athenaAdapter = new MatchModeAthenaAdapter();
				break;
			case TEST_MODE:
				athenaAdapter = new TestModeAthenaAdapter();
				break;
			default:
				throw new IllegalStateException();
		}
		controlState = mode;
		notifyAiModeChanged(mode);
	}
	
	
	
	public final EAIControlState getControlState()
	{
		return controlState;
	}
	
	
	
	public AthenaAiFrame process(final MetisAiFrame metisAiFrame)
	{
		final PlayStrategy.Builder playStrategyBuilder = new PlayStrategy.Builder();
		return process(metisAiFrame, playStrategyBuilder);
	}
	
	
	
	public AthenaAiFrame process(final MetisAiFrame metisAiFrame, final PlayStrategy.Builder playStrategyBuilder)
	{
		AthenaAiFrame athenaAiFrame = null;
		synchronized (athenaAdapter)
		{
			athenaAdapter.process(metisAiFrame, playStrategyBuilder);
			playStrategyBuilder.setAIControlState(controlState);
			athenaAiFrame = new AthenaAiFrame(metisAiFrame, playStrategyBuilder.build());
			
			processRoleAssignment(athenaAiFrame);
			
			updatePlays(athenaAiFrame);
		}
		return athenaAiFrame;
	}
	
	
	
	public void onException(final AthenaAiFrame aiFrame)
	{
		for (ARole role : aiFrame.getPlayStrategy().getActiveRoles().values())
		{
			role.setCompleted();
		}
	}
	
	
	private void processRoleAssignment(final AthenaAiFrame frame)
	{
		lachesis.assignRoles(frame);
	}
	
	
	private void updatePlays(final AthenaAiFrame frame)
	{
		// update all plays with the new frame and remove plays which failed or succeeded
		for (APlay play : new ArrayList<APlay>(frame.getPlayStrategy().getActivePlays()))
		{
			
			try
			{
				for (ARole role : play.getRoles())
				{
					role.updateBefore(frame);
				}
				
				play.updateBeforeRoles(frame);
				
				for (ARole role : play.getRoles())
				{
					role.update(frame);
				}
				
				play.update(frame);
			} catch (Exception err)
			{
				log.error("Exception during play update!", err);
			}
		}
	}
	
	
	
	public final AAthenaAdapter getAthenaAdapter()
	{
		return athenaAdapter;
	}
}
