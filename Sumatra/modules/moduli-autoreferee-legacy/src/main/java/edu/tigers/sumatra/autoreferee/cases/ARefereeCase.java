/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.autoreferee.cases;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;



public abstract class ARefereeCase implements IRefereeCase
{
	private enum ERefereeState
	{
		PAUSED,
		RUNNING,
		PENDING
	}
	
	private static final int		MAX_PENDING_TIME	= 3000;
	private static final double	CALL_FREQ			= 60.0;						// Hz
																									
	private ERefereeState			state					= ERefereeState.PAUSED;
	private int							pendingTime			= 0;							// ms
																									
																									
	
	public ARefereeCase()
	{
		
	}
	
	
	@Override
	public List<RefereeCaseMsg> process(final MetisAiFrame frame)
	{
		return runCase(frame);
	}
	
	
	private List<RefereeCaseMsg> runCase(final MetisAiFrame frame)
	{
		EGameStateTeam gameState = frame.getTacticalField().getGameState();
		List<RefereeCaseMsg> caseMsgs = new ArrayList<>();
		switch (state)
		{
			case PAUSED:
				if (isGameRunning(gameState))
				{
					state = ERefereeState.RUNNING;
				}
				return caseMsgs;
			case PENDING:
				if (!isGameRunning(gameState))
				{
					state = ERefereeState.PAUSED;
					break;
				}
				pendingTime += 1000.0 / CALL_FREQ;
				if (pendingTime >= MAX_PENDING_TIME)
				{
					state = ERefereeState.RUNNING;
				}
				return caseMsgs;
			case RUNNING:
				if (!isGameRunning(gameState))
				{
					state = ERefereeState.PAUSED;
					break;
				}
				checkCase(frame, caseMsgs);
				if (!caseMsgs.isEmpty())
				{
					pendingTime = 0;
					state = ERefereeState.PENDING;
				}
				return caseMsgs;
		}
		return caseMsgs;
	}
	
	
	@Override
	public void reset()
	{
		state = ERefereeState.PAUSED;
		pendingTime = 0;
	}
	
	
	private boolean isGameRunning(final EGameStateTeam gameState)
	{
		return gameState != EGameStateTeam.HALTED;
	}
	
	
	
	protected abstract void checkCase(MetisAiFrame frame, List<RefereeCaseMsg> caseMsgs);
}
