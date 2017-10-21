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

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import edu.tigers.autoreferee.engine.log.GameLog;
import edu.tigers.autoreferee.engine.log.GameLog.IGameLogObserver;
import edu.tigers.autoreferee.engine.log.GameLogEntry;
import edu.tigers.autoreferee.engine.log.IGameLog;



public class GameLogTableModel extends AbstractTableModel implements IGameLogObserver
{
	
	
	private static final long				serialVersionUID	= -8160241136867692587L;
	private static final List<String>	columns;
	
	private final IGameLog					gameLog;
	
	
	static
	{
		columns = Collections.unmodifiableList(Arrays.asList("Time [ms]", "GameTime [ms]", "Type", "Event"));
	}
	
	
	
	public GameLogTableModel(final IGameLog log)
	{
		gameLog = log;
		gameLog.addObserver(this);
	}
	
	
	@Override
	public int getRowCount()
	{
		return gameLog.getEntries().size();
	}
	
	
	@Override
	public int getColumnCount()
	{
		return columns.size();
	}
	
	
	@Override
	public String getColumnName(final int column)
	{
		return columns.get(column);
	}
	
	
	@Override
	public Class<?> getColumnClass(final int columnIndex)
	{
		return GameLogEntry.class;
	}
	
	
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		
		return getEntry(rowIndex);
	}
	
	
	@Override
	public void onNewEntry(final int id, final GameLogEntry entry)
	{
		EventQueue.invokeLater(() -> fireTableRowsInserted(id, id));
	}
	
	
	private GameLogEntry getEntry(final int rowIndex)
	{
		return gameLog.getEntries().get(rowIndex);
	}
	
}
