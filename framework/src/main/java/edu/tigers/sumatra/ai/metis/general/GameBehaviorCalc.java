/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.general;

import edu.tigers.sumatra.ai.data.EGameBehavior;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;



public class GameBehaviorCalc extends ACalculator
{
	
	
	public GameBehaviorCalc()
	{
	}
	
	
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		if (baseAiFrame.getRefereeMsg() == null)
		{
			fallbackCalc(newTacticalField, baseAiFrame);
			return;
		}
		
		int goalDifference = baseAiFrame.getRefereeMsg().getTeamInfoTigers().getScore()
				- baseAiFrame.getRefereeMsg().getTeamInfoThem().getScore();
		EGameBehavior behavior = newTacticalField.getGameBehavior();
		
		if (goalDifference <= -5)
		{
			behavior = EGameBehavior.DEFENSIVE;
		} else
		{
			behavior = EGameBehavior.OFFENSIVE;
		}
		newTacticalField.setGameBehavior(behavior);
		
	}
	
	
	@Override
	public void fallbackCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		newTacticalField.setGameBehavior(EGameBehavior.OFFENSIVE);
	}
}
