/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.statistics;

import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class MarkovChain<MappedClass>
{
	private Map<MappedClass, MarkovChainEntry<MappedClass>>	containedStates	= new HashMap<>();
	
	
	
	public void increaseCountTransitions(final MappedClass startState, final MappedClass goalState)
	{
		MarkovChainEntry<MappedClass> entryToIncrease = containedStates.get(startState);
		
		if (entryToIncrease != null)
		{
			Integer countTransitions = entryToIncrease.absoluteTransitions.get(goalState);
			
			if (countTransitions != null)
			{
				entryToIncrease.absoluteTransitions.put(goalState, countTransitions + 1);
			} else
			{
				entryToIncrease.absoluteTransitions.put(goalState, 1);
			}
			
			entryToIncrease.transitions++;
		} else
		{
			MarkovChainEntry<MappedClass> newEntry = new MarkovChainEntry<MappedClass>();
			
			newEntry.absoluteTransitions.put(goalState, 1);
			
			containedStates.put(startState, newEntry);
		}
	}
	
	
	
	public int getAbsoluteCountTransitions(final MappedClass startState, final MappedClass goalState)
	{
		MarkovChainEntry<MappedClass> startEntry = containedStates.get(startState);
		
		if (startEntry != null)
		{
			Integer transitions = startEntry.absoluteTransitions.get(goalState);
			
			if (transitions != null)
			{
				return transitions;
			}
		}
		
		return 0;
	}
	
	
	
	public float getRelativeCountTransitions(final MappedClass startState, final MappedClass goalState)
	{
		int absoluteTransitions = getAbsoluteCountTransitions(startState, goalState);
		
		MarkovChainEntry<MappedClass> startEntry = containedStates.get(startState);
		
		if (startEntry != null)
		{
			return (float) absoluteTransitions / startEntry.transitions;
		}
		
		return 0;
	}
}
