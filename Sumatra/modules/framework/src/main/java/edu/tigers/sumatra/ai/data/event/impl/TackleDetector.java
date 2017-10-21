/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.event.impl;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.ballpossession.EBallPossession;
import edu.tigers.sumatra.ai.data.event.GameEventFrame;
import edu.tigers.sumatra.ai.data.event.IGameEventDetector;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ids.BotID;



public class TackleDetector implements IGameEventDetector
{
	
	@Override
	public GameEventFrame getActiveParticipant(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		if (isTackleActive(newTacticalField))
		{
			BotID tacklingTiger = newTacticalField.getBallPossession().getTigersId();
			BotID tacklingOpponent = newTacticalField.getBallPossession().getOpponentsId();
			
			return new GameEventFrame(tacklingTiger, tacklingOpponent);
		}
		
		return null;
	}
	
	
	private boolean isTackleActive(final TacticalField newTacticalField)
	{
		return newTacticalField.getBallPossession().getEBallPossession() == EBallPossession.BOTH;
	}
	
}
