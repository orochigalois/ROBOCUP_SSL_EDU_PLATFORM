/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.calc;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import edu.tigers.autoreferee.AutoRefFrame;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class GameStateHistoryCalc implements IRefereeCalc
{
	private static int							historySize	= 5;
	
	// Using the implementation directly is normally considered bad practice, but a LinkedList implements both the List
	// as well as the Queue interface, which makes it very convenient for internal use
	private LinkedList<EGameStateNeutral>	stateHistory;
	
	
	
	public GameStateHistoryCalc()
	{
		stateHistory = new LinkedList<>(Arrays.asList(EGameStateNeutral.UNKNOWN));
	}
	
	
	@Override
	public void process(final AutoRefFrame frame)
	{
		if (stateHistory.peekFirst() != frame.getGameState())
		{
			add(frame.getGameState());
		}
		
		frame.setStateHistory(Collections.unmodifiableList(stateHistory));
	}
	
	
	private void add(final EGameStateNeutral state)
	{
		if (stateHistory.size() >= historySize)
		{
			stateHistory.pollLast();
		}
		stateHistory.offerFirst(state);
	}
	
}
