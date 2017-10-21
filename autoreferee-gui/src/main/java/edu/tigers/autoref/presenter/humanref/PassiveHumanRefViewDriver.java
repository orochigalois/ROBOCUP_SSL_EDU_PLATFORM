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

import java.util.LinkedList;

import edu.tigers.autoref.view.humanref.PassiveHumanRefPanel;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.autoreferee.engine.log.GameLogEntry;



public class PassiveHumanRefViewDriver extends BaseHumanRefViewDriver
{
	private final static int				eventLogSize	= 5;
	private final PassiveHumanRefPanel	panel;
	
	private LinkedList<IGameEvent>		events			= new LinkedList<>();
	
	
	
	public PassiveHumanRefViewDriver(final PassiveHumanRefPanel panel)
	{
		super(panel);
		this.panel = panel;
	}
	
	
	@Override
	public void setNewGameLogEntry(final GameLogEntry entry)
	{
		switch (entry.getType())
		{
			case GAME_EVENT:
				IGameEvent event = entry.getGameEvent();
				addToList(event);
				updateEvents();
				break;
			default:
				break;
		
		}
	}
	
	
	private void addToList(final IGameEvent event)
	{
		if (events.size() >= eventLogSize)
		{
			events.pollLast();
		}
		events.offerFirst(event);
	}
	
	
	private void updateEvents()
	{
		panel.setEvents(events);
	}
	
}
