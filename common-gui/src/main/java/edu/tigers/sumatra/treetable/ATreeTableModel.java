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


import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;



public abstract class ATreeTableModel implements ITreeTableModel
{
	protected Object					root;
	protected EventListenerList	listenerList	= new EventListenerList();
	
	private boolean					editable			= true;
	
	
	
	public ATreeTableModel(final Object root)
	{
		this.root = root;
	}
	
	
	@Override
	public Object getRoot()
	{
		return root;
	}
	
	
	@Override
	public boolean isLeaf(final Object obj)
	{
		return getChildCount(obj) == 0;
	}
	
	
	@Override
	public void valueForPathChanged(final TreePath paramTreePath, final Object paramObject)
	{
		
	}
	
	
	
	@Override
	public boolean isCellEditable(final Object node, final int column)
	{
		return getColumnClass(column) == ITreeTableModel.class;
	}
	
	
	@Override
	public void setEditable(final boolean editable)
	{
		this.editable = editable;
	}
	
	
	@Override
	public boolean isEditable()
	{
		return editable;
	}
	
	
	@Override
	public void addTreeModelListener(final TreeModelListener l)
	{
		listenerList.add(TreeModelListener.class, l);
	}
	
	
	@Override
	public void removeTreeModelListener(final TreeModelListener l)
	{
		listenerList.remove(TreeModelListener.class, l);
	}
	
	
	
	protected void fireTreeNodesChanged(final Object source, final Object[] path, final int[] childIndices,
			final Object[] children)
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == TreeModelListener.class)
			{
				// Lazily create the event:
				if (e == null)
				{
					e = new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}
	
	
	
	protected void fireTreeNodesInserted(final Object source, final Object[] path, final int[] childIndices,
			final Object[] children)
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == TreeModelListener.class)
			{
				// Lazily create the event:
				if (e == null)
				{
					e = new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
	}
	
	
	
	protected void fireTreeNodesRemoved(final Object source, final Object[] path, final int[] childIndices,
			final Object[] children)
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == TreeModelListener.class)
			{
				// Lazily create the event:
				if (e == null)
				{
					e = new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
			}
		}
	}
	
	
	
	protected void fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices,
			final Object[] children)
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == TreeModelListener.class)
			{
				// Lazily create the event:
				if (e == null)
				{
					e = new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}
}
