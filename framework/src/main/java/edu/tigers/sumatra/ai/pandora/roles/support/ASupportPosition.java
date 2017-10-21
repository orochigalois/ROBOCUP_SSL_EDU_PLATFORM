/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.support;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;



public abstract class ASupportPosition
{
	protected Map<BotID, IVector2>	supportPositions;
	
	
	protected ASupportPosition()
	{
		supportPositions = new HashMap<BotID, IVector2>();
	}
	
	
	
	public IVector2 getNewPosition(final BotID bot, final TacticalField newTacticalField, final BaseAiFrame aiFrame)
	{
		IVector2 newPosition;
		
		if (isSupporter(bot, aiFrame))
		{
			newPosition = estimateNewPosition(bot, newTacticalField, aiFrame);
		} else
		{
			newPosition = aiFrame.getWorldFrame().getTiger(bot).getPos();
		}
		
		if (newPosition == null)
		{
			newPosition = aiFrame.getWorldFrame().getTiger(bot).getPos();
		}
		
		supportPositions.put(bot, newPosition);
		
		return newPosition;
	}
	
	
	
	protected abstract IVector2 estimateNewPosition(final BotID bot, final TacticalField newTacticalField,
			final BaseAiFrame aiFrame);
	
	
	protected boolean isSupporter(final BotID bot, final BaseAiFrame aiFrame)
	{
		return aiFrame.getPrevFrame().getPlayStrategy().getActiveRoles(ERole.SUPPORT).stream()
				.anyMatch(role -> bot.equals(role.getBotID()));
	}
	
	
	// protected boolean isBalanced(final IVector2 position, final BaseAiFrame aiFrame)
	// {
	// int yBalance = 0;
	//
	// for (ARole role : aiFrame.getPrevFrame().getPlayStrategy().getActiveRoles(ERole.SUPPORT))
	// {
	// if (((SupportRole) role).getDestination() != null)
	// {
	// yBalance += ((SupportRole) role).getDestination().y() < 0 ? -1 : 1;
	// }
	// }
	//
	// boolean positiveNeeded = yBalance <= 0;
	//
	// return (yBalance == 0) || (positiveNeeded && (position.y() >= 0)) || (!positiveNeeded && (position.y() < 0));
	// }
}
