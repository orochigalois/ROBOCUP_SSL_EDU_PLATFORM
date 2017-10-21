/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.treetable;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;



public class TreeTableModelAdapter extends AbstractTableModel
{
	
	private static final long		serialVersionUID	= -6298333095243382630L;
	
	private final JTree				tree;
	private final ITreeTableModel	treeTableModel;
	
	
	
	public TreeTableModelAdapter(final ITreeTableModel treeTableModel, final JTree tree)
	{
		this.tree = tree;
		this.treeTableModel = treeTableModel;
		
		tree.addTreeExpansionListener(new TreeExpansionListener()
		{
			// Don't use fireTableRowsInserted() here; the selection model
			// would get updated twice.
			@Override
			public void treeExpanded(final TreeExpansionEvent event)
			{
				fireTableDataChanged();
			}
			
			
			@Override
			public void treeCollapsed(final TreeExpansionEvent event)
			{
				fireTableDataChanged();
			}
		});
		
		// Install a TreeModelListener that can update the table when
		// tree changes. We use delayedFireTableDataChanged as we can
		// not be guaranteed the tree will have finished processing
		// the event before us.
		treeTableModel.addTreeModelListener(new TreeModelListener()
		{
			@Override
			public void treeNodesChanged(final TreeModelEvent e)
			{
				// Only one element changed
				if (e.getChildIndices().length == 0)
				{
					final int row = TreeTableModelAdapter.this.tree.getRowForPath(e.getTreePath());
					delayedFireTableRowsUpdated(row, row);
				} else
				{
					delayedFireTableDataChanged();
				}
			}
			
			
			@Override
			public void treeNodesInserted(final TreeModelEvent e)
			{
				delayedFireTableDataChanged();
			}
			
			
			@Override
			public void treeNodesRemoved(final TreeModelEvent e)
			{
				delayedFireTableDataChanged();
			}
			
			
			@Override
			public void treeStructureChanged(final TreeModelEvent e)
			{
				delayedFireTableDataChanged();
			}
		});
	}
	
	
	// Wrappers, implementing TableModel interface.
	
	@Override
	public int getColumnCount()
	{
		return treeTableModel.getColumnCount();
	}
	
	
	@Override
	public String getColumnName(final int column)
	{
		return treeTableModel.getColumnName(column);
	}
	
	
	@Override
	public Class<?> getColumnClass(final int column)
	{
		return treeTableModel.getColumnClass(column);
	}
	
	
	@Override
	public int getRowCount()
	{
		return tree.getRowCount();
	}
	
	
	protected Object nodeForRow(final int row)
	{
		final TreePath treePath = tree.getPathForRow(row);
		return treePath.getLastPathComponent();
	}
	
	
	@Override
	public Object getValueAt(final int row, final int column)
	{
		return treeTableModel.getValueAt(nodeForRow(row), column);
	}
	
	
	@Override
	public boolean isCellEditable(final int row, final int column)
	{
		return treeTableModel.isCellEditable(nodeForRow(row), column);
	}
	
	
	@Override
	public void setValueAt(final Object value, final int row, final int column)
	{
		treeTableModel.setValueAt(value, nodeForRow(row), column);
	}
	
	
	
	protected void delayedFireTableDataChanged()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				fireTableDataChanged();
			}
		});
	}
	
	
	
	protected void delayedFireTableRowsUpdated(final int firstRow, final int lastRow)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				fireTableRowsUpdated(firstRow, lastRow);
			}
		});
	}
}
