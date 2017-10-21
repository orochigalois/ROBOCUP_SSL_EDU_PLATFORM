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

import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.tree.TreeModel;



public interface ITreeTableModel extends TreeModel
{
	
	int getColumnCount();
	
	
	
	String getColumnName(int column);
	
	
	
	Class<?> getColumnClass(int column);
	
	
	
	Object getValueAt(Object node, int column);
	
	
	
	boolean isCellEditable(Object node, int column);
	
	
	
	void setEditable(boolean editable);
	
	
	
	boolean isEditable();
	
	
	
	void setValueAt(Object aValue, Object node, int column);
	
	
	
	void renderTreeCellComponent(JLabel label, Object node);
	
	
	
	String getToolTipText(MouseEvent event);
}
