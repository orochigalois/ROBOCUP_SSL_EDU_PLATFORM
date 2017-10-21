/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit;

import edu.tigers.sumatra.ai.data.EGameStateTeam;



public class SimStopGameStateReached extends ASimStopCriterion
{
	private final EGameStateTeam	gameState;
	
	
	
	public SimStopGameStateReached(final EGameStateTeam gameState)
	{
		this.gameState = gameState;
	}
	
	
	@Override
	protected boolean checkStopSimulation()
	{
		return getLatestFrame().getTacticalField().getGameState() == gameState;
	}
}
