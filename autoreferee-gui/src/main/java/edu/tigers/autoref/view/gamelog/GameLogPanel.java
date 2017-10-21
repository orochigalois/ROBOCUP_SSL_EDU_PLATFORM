/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.gamelog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

import edu.tigers.autoref.model.gamelog.GameLogRowFilter;
import edu.tigers.autoref.model.gamelog.GameLogTableModel;
import edu.tigers.autoreferee.engine.log.GameLog;
import edu.tigers.autoreferee.engine.log.GameLogEntry;
import edu.tigers.autoreferee.engine.log.GameLogEntry.ELogEntryType;



public class GameLogPanel extends JPanel
{
	
	private static final long						serialVersionUID	= 3266769602344203080L;
	
	private JTable										entryTable			= new JTable();
	private GameLogRowFilter						filter				= new GameLogRowFilter();
	private TableRowSorter<GameLogTableModel>	sorter				= new TableRowSorter<>();
	
	
	
	public GameLogPanel()
	{
		entryTable.setFillsViewportHeight(true);
		entryTable.setDefaultRenderer(GameLogEntry.class, new GameLogCellRenderer());
		
		sorter.setRowFilter(filter);
		entryTable.setRowSorter(sorter);
		
		
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(entryTable);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	
	
	public void setTableModel(final GameLogTableModel model)
	{
		sorter.setModel(model);
		entryTable.setModel(model);
		model.addTableModelListener(new ScrollDownModelListener());
		
		disableUserColumnSorting();
	}
	
	
	
	public void setActiveLogTypes(final Set<ELogEntryType> types)
	{
		filter.setIncludedTypes(types);
		sorter.sort();
	}
	
	
	private void disableUserColumnSorting()
	{
		IntStream.range(0, entryTable.getColumnCount()).forEach(i -> sorter.setSortable(i, false));
	}
	
	
	private class ScrollDownModelListener implements TableModelListener
	{
		
		@Override
		public void tableChanged(final TableModelEvent e)
		{
			Rectangle bounds = entryTable.getBounds();
			Rectangle viewBounds = entryTable.getVisibleRect();
			
			boolean isViewPortAtBottom =
					(viewBounds.getY() + viewBounds.getHeight()) >= (bounds.getY() + bounds.getHeight());
			
			if (isViewPortAtBottom)
			{
				EventQueue.invokeLater(() -> {
					entryTable.scrollRectToVisible(entryTable.getCellRect(entryTable.getRowCount() - 1, 0, true));
				});
			}
		}
		
	}
}
