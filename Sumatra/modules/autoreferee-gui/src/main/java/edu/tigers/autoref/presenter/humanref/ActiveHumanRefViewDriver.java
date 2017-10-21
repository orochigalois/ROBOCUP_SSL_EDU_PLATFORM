/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.presenter.humanref;

import edu.tigers.autoref.view.humanref.ActiveHumanRefPanel;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.autoreferee.engine.log.GameLogEntry;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class ActiveHumanRefViewDriver extends BaseHumanRefViewDriver
{
	private final ActiveHumanRefPanel	panel;
	
	
	
	public ActiveHumanRefViewDriver(final ActiveHumanRefPanel panel)
	{
		super(panel);
		this.panel = panel;
	}
	
	
	@Override
	public void setNewGameLogEntry(final GameLogEntry entry)
	{
		switch (entry.getType())
		{
			case FOLLOW_UP:
				FollowUpAction action = entry.getFollowUpAction();
				setFollowUp(action);
				break;
			case GAME_EVENT:
				if (entry.isAcceptedByEngine())
				{
					IGameEvent event = entry.getGameEvent();
					setEvent(event);
					setFollowUp(event.getFollowUpAction());
				}
				break;
			case GAME_STATE:
				EGameStateNeutral state = entry.getGamestate();
				if (state == EGameStateNeutral.RUNNING)
				{
					clearEvent();
					setFollowUp(null);
				}
			default:
				break;
		
		}
	}
	
	
	private void setFollowUp(final FollowUpAction action)
	{
		if (action == null)
		{
			panel.clearFollowUp();
		} else
		{
			panel.setNextAction(action);
		}
	}
	
	
	private void setEvent(final IGameEvent event)
	{
		panel.setEvent(event);
	}
	
	
	private void clearEvent()
	{
		panel.clearEvent();
	}
	
}
