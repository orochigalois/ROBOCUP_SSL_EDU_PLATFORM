/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.model.gamelog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.RowFilter;

import edu.tigers.autoreferee.engine.log.GameLogEntry;
import edu.tigers.autoreferee.engine.log.GameLogEntry.ELogEntryType;



public class GameLogRowFilter extends RowFilter<GameLogTableModel, Integer>
{
	private Set<ELogEntryType>	includedTypes;
	
	
	
	public GameLogRowFilter()
	{
		includedTypes = new HashSet<>(Arrays.asList(ELogEntryType.values()));
	}
	
	
	
	public GameLogRowFilter(final Set<ELogEntryType> includedTypes)
	{
		this.includedTypes = includedTypes;
	}
	
	
	
	public void setIncludedTypes(final Set<ELogEntryType> types)
	{
		includedTypes = types;
	}
	
	
	@Override
	public boolean include(final Entry<? extends GameLogTableModel, ? extends Integer> entry)
	{
		GameLogEntry logEntry = (GameLogEntry) entry.getValue(0);
		return includedTypes.contains(logEntry.getType());
	}
	
}
