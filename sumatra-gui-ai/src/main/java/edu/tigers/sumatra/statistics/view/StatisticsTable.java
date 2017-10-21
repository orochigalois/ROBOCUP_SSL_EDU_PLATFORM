/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.statistics.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.tigers.sumatra.ai.data.statistics.calculators.StatisticData;
import edu.tigers.sumatra.ids.BotID;



public class StatisticsTable extends JTable
{
	
	private static final long				serialVersionUID		= 1L;
	
	private DefaultTableModel				tableModel;
	
	
	private Map<String, StatisticData>	rowEntries;
	
	private Map<BotID, Integer>			columnOfBot				= new HashMap<>();
	
	private final int							countGenericColums	= 2;
	private final int							placeGeneralColumn	= 1;
	
	private Map<BotID, Integer>			hardwareIDs				= new HashMap<>();
	
	private boolean							isHardwareIDShown		= false;
	
	
	
	public StatisticsTable()
	{
		super(0, 2);
		tableModel = (DefaultTableModel) getModel();
		
		tableModel.addRow(new String[] { "Statistic", "General" });
	}
	
	
	
	public void updateTableEntries(final Map<String, StatisticData> updatedEntries,
			final Set<BotID> availableBots)
	{
		rowEntries = updatedEntries;
		
		createBotHeaders(availableBots);
		
		// This would mean that there is no update or that the statistics is halted
		if (updatedEntries.size() == 0)
		{
			return;
		}
		
		updateTable();
	}
	
	
	private void createBotHeaders(final Set<BotID> availableBots)
	{
		tableModel.setColumnCount(columnOfBot.size() + countGenericColums);
		
		for (BotID tempBotID : availableBots)
		{
			if (!columnOfBot.containsKey(tempBotID))
			{
				columnOfBot.put(tempBotID, countGenericColums + columnOfBot.size());
				
				tableModel.setColumnCount(columnOfBot.size() + countGenericColums);
			}
			
			Integer valueToDisplay = getBotHeaderEntry(tempBotID);
			
			tableModel.setValueAt(valueToDisplay, 0, columnOfBot.get(tempBotID));
		}
	}
	
	
	private int getBotHeaderEntry(final BotID botToGetHeader)
	{
		if (isHardwareIDShown)
		{
			return hardwareIDs.get(botToGetHeader);
		}
		return botToGetHeader.getNumberWithColorOffset();
	}
	
	
	private void updateTable()
	{
		if (rowEntries != null)
		{
			tableModel.setRowCount(rowEntries.size() + 1);
			
			
			// This two rows are going to fill the table with the header and then with the values
			int row = 1;
			
			for (String rowDescriptor : rowEntries.keySet())
			{
				tableModel.setValueAt(rowDescriptor, row, 0);
				
				String generalStatistic = rowEntries.get(rowDescriptor).getTextualRepresenationOfGeneralStatistic();
				
				tableModel.setValueAt(generalStatistic, row, placeGeneralColumn);
				
				Map<BotID, String> specificStatistics = rowEntries.get(rowDescriptor)
						.getTextualRepresentationOfBotStatistics();
				
				for (BotID availableBot : specificStatistics.keySet())
				{
					Integer columnToSet;
					
					columnToSet = columnOfBot.get(availableBot);
					
					if ((columnToSet != null) && (specificStatistics != null))
					{
						tableModel.setValueAt(specificStatistics.get(availableBot), row, columnToSet);
					}
				}
				
				row++;
			}
		}
	}
	
	
	
	public void updateHardwareIDs(final Map<BotID, Integer> hardwareIDs)
	{
		for (BotID tempBotID : hardwareIDs.keySet())
		{
			this.hardwareIDs.put(tempBotID, hardwareIDs.get(tempBotID));
		}
	}
	
	
	
	public void setHardwareIDShown(final boolean isHardwareIDShown)
	{
		this.isHardwareIDShown = isHardwareIDShown;
	}
	
	
}
