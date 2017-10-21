/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.cam.data.CamBall;



public class FlyingBallFitter
{
	private final List<List<CamBall>>	currentFly					= new ArrayList<>();
	private boolean							ballCloseToBot				= false;
	
	private FlyingBallFitter				instance						= null;
	
	@SuppressWarnings("unused")
	private int									framesSinceLeavingBot	= 1;
	@SuppressWarnings("unused")
	private int									idxOfEstimatedLastChip	= 0;
	
	
	
	public FlyingBallFitter()
	{
		
	}
	
	
	
	public FlyingBallFitter getInstance()
	{
		if (instance == null)
		{
			instance = new FlyingBallFitter();
		}
		return instance;
	}
	
	
	
	public void onBallCloseToBot(final List<CamBall> currentBalls)
	{
		if (!ballCloseToBot)
		{
			currentFly.clear();
			ballCloseToBot = true;
		}
		// currentFly.add(currentBalls);
	}
	
	
	
	public void onBallAwayFromBot(final List<CamBall> currentBalls)
	{
		if (ballCloseToBot)
		{
			currentFly.clear();
			currentFly.add(currentBalls);
			framesSinceLeavingBot = 1;
			ballCloseToBot = false;
			// TODO 1. find point in time where the ball got accelerated
			// for (int i = currentFly.size() - 2; i >= 0; i--)
			// {
			// for(CamBall ball : currentFly.get(i)) {
			// ball.get
			// }
			// }
			// first simple def
			// idxOfEstimatedLastChip = currentFly.size() - 2;
			
			// TODO 2. fit
			LevenbergMarquardt lm = new LevenbergMarquardt(currentFly);
			lm.optimize();
			
			
			// TODO 3. flag the current hypothesis
		} else if (!ballCloseToBot && (currentFly.size() != 0))
		{
			currentFly.add(currentBalls);
			
			// TODO check with current hypothesis and update
			LevenbergMarquardt lm = new LevenbergMarquardt(currentFly);
			lm.optimize();
		}
		framesSinceLeavingBot++;
	}
}
