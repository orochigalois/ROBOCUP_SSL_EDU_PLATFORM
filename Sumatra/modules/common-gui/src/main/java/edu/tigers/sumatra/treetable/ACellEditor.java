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


import java.util.EventObject;

import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;



public class ACellEditor implements CellEditor
{
	protected final EventListenerList	listenerList	= new EventListenerList();
	
	
	@Override
	public Object getCellEditorValue()
	{
		return null;
	}
	
	
	@Override
	public boolean isCellEditable(final EventObject paramEventObject)
	{
		return true;
	}
	
	
	@Override
	public boolean shouldSelectCell(final EventObject paramEventObject)
	{
		return false;
	}
	
	
	@Override
	public boolean stopCellEditing()
	{
		return true;
	}
	
	
	@Override
	public void cancelCellEditing()
	{
		
	}
	
	
	@Override
	public void addCellEditorListener(final CellEditorListener l)
	{
		listenerList.add(CellEditorListener.class, l);
	}
	
	
	@Override
	public void removeCellEditorListener(final CellEditorListener l)
	{
		listenerList.remove(CellEditorListener.class, l);
	}
	
	
	
	protected void fireEditingStopped()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == CellEditorListener.class)
			{
				((CellEditorListener) listeners[i + 1]).editingStopped(new ChangeEvent(this));
			}
		}
	}
	
	
	
	protected void fireEditingCanceled()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == CellEditorListener.class)
			{
				((CellEditorListener) listeners[i + 1]).editingCanceled(new ChangeEvent(this));
			}
		}
	}
}
