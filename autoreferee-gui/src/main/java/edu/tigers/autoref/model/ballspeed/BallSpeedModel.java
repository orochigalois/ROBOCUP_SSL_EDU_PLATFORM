/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.model.ballspeed;

import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class BallSpeedModel
{
	private double					lastBallSpeed		= 0.0d;
	
	private EGameStateNeutral	lastState			= EGameStateNeutral.UNKNOWN;
	private boolean				gameStateChanged	= false;
	
	
	
	public BallSpeedModel()
	{
	}
	
	
	
	public void update(final WorldFrameWrapper wFrameWrapper)
	{
		EGameStateNeutral curState = wFrameWrapper.getGameState();
		
		if (curState != lastState)
		{
			gameStateChanged = true;
		}
		
		lastBallSpeed = wFrameWrapper.getSimpleWorldFrame().getBall().getVel().getLength();
		lastState = curState;
	}
	
	
	
	public void reset()
	{
		gameStateChanged = false;
	}
	
	
	
	public double getLastBallSpeed()
	{
		return lastBallSpeed;
	}
	
	
	
	public EGameStateNeutral getLastState()
	{
		return lastState;
	}
	
	
	
	public boolean hasGameStateChanged()
	{
		return gameStateChanged;
	}
}
