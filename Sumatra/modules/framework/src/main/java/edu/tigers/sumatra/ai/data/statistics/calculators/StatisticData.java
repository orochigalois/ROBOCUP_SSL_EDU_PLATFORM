/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.statistics.calculators;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.data.Percentage;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.statistics.MarkovChain;



@Persistent
public class StatisticData
{
	private Map<BotID, ? extends Object>	botspecificStatistics;
	private Object									generalStatistic;
	
	
	@SuppressWarnings("unused")
	private StatisticData()
	{
		
	}
	
	
	
	public StatisticData(final Map<BotID, ? extends Object> botspecificStatistics, final Object generalStatistic)
	{
		this.botspecificStatistics = botspecificStatistics;
		this.generalStatistic = generalStatistic;
	}
	
	
	
	public <T> StatisticData(final Map<BotID, MarkovChain<T>> mappedChain, final T stateOne, final T stateTwo)
	{
		Integer sumTransitions = 0;
		Map<BotID, Integer> tempList = new HashMap<>();
		
		for (BotID bot : mappedChain.keySet())
		{
			Integer botTransitions = mappedChain.get(bot).getAbsoluteCountTransitions(stateOne, stateTwo);
			tempList.put(bot, botTransitions);
			sumTransitions += botTransitions;
		}
		
		botspecificStatistics = tempList;
		generalStatistic = sumTransitions;
	}
	
	
	
	public Map<BotID, String> getTextualRepresentationOfBotStatistics()
	{
		Map<BotID, String> textualRepresentation = new HashMap<BotID, String>();
		
		for (BotID bot : botspecificStatistics.keySet())
		{
			textualRepresentation.put(bot, getTextualRepresentation(botspecificStatistics.get(bot)));
		}
		
		return textualRepresentation;
	}
	
	
	
	public String getTextualRepresenationOfGeneralStatistic()
	{
		return getTextualRepresentation(generalStatistic);
	}
	
	
	private String getTextualRepresentation(final Object o)
	{
		if (o == null)
		{
			return null;
		}
		
		if (o instanceof Percentage)
		{
			DecimalFormat df = new DecimalFormat("###.#%");
			
			return df.format(((Percentage) o).getPercent());
		}
		return o.toString();
	}
}
