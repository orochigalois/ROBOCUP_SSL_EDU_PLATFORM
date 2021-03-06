/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.config;

import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import com.github.g3force.s2vconverter.String2ValueConverter;

import edu.tigers.sumatra.treetable.ATreeTableModel;
import edu.tigers.sumatra.treetable.ITreeTableModel;



public class ConfigXMLTreeTableModel extends ATreeTableModel
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final String[]					COLUMNS	= new String[] { "Node", "Value", "Comment" };
	private static final Class<?>[]				CLASSES	= new Class<?>[] { ITreeTableModel.class, String.class,
																				String.class };
																				
																				
	private final String2ValueConverter			s2vConv	= String2ValueConverter.getDefault();
																		
	private final HierarchicalConfiguration	config;
															
															
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ConfigXMLTreeTableModel(final HierarchicalConfiguration xml)
	{
		// Hopefully there is no comment as first element... :-P
		super(xml.getRoot());
		config = xml;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public Object getValueAt(final Object obj, final int col)
	{
		if (col == 0)
		{
			// is handled by TreeCellRenderer!!!
			return null;
		}
		
		final Node node = (Node) obj;
		Object result = "";
		switch (col)
		{
			case 1:
				final Object val = node.getValue();
				if (val != null)
				{
					for (ConfigurationNode attr : node.getAttributes("class"))
					{
						Class<?> classType = String2ValueConverter.getClassFromValue(attr.getValue());
						if (classType.isEnum() || (classType == Boolean.TYPE) || (classType == Boolean.class))
						{
							result = s2vConv.parseString(classType, val.toString());
						} else
						{
							result = val.toString();
						}
						break;
					}
				}
				break;
				
			case 2:
				for (ConfigurationNode attr : node.getAttributes("comment"))
				{
					result = attr.getValue().toString();
					break;
				}
				final org.w3c.dom.Node comment = getComment(node);
				if (comment != null)
				{
					result += " " + comment.getTextContent();
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid value for col: " + col);
		}
		return result;
	}
	
	
	@Override
	public void renderTreeCellComponent(final JLabel label, final Object value)
	{
		final Node node = (Node) value;
		label.setText(node.getName());
	}
	
	
	@Override
	public Object getChild(final Object obj, final int index)
	{
		final Node node = (Node) obj;
		final List<?> list = node.getChildren();
		if ((list == null) || (list.size() <= index))
		{
			// Should not happen!
			return null;
		}
		return list.get(index);
	}
	
	
	@Override
	public int getIndexOfChild(final Object parentObj, final Object childObj)
	{
		final Node node = (Node) parentObj;
		final List<?> children = node.getChildren();
		for (int i = 0; i < children.size(); i++)
		{
			if (children.get(i).equals(childObj))
			{
				return i;
			}
		}
		// Not found!
		return -1;
	}
	
	
	@Override
	public int getChildCount(final Object obj)
	{
		final Node node = (Node) obj;
		return node.getChildrenCount();
	}
	
	
	@Override
	public boolean isCellEditable(final Object obj, final int col)
	{
		// 0.0 = "Name"
		if (col == 0)
		{
			// For tree-expansion/collapse
			return super.isCellEditable(obj, col);
		}
		
		if (!isEditable())
		{
			// Editing disabled
			return false;
		}
		
		switch (col)
		{
			
			// "Value"
			case 1:
				return isLeaf(obj);
				
			// "Comment"
			case 2:
				final org.w3c.dom.Node comment = getComment(obj);
				return comment != null;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public void setValueAt(final Object value, final Object obj, final int col)
	{
		final Node node = (Node) obj;
		switch (col)
		{
			case 1:
				node.setValue(value);
				fireTreeNodesChanged(this, getPathTo(node), new int[0], new Object[0]);
				break;
				
			case 2:
				final org.w3c.dom.Node comment = getComment(obj);
				if (comment != null)
				{
					comment.setTextContent(value.toString());
					fireTreeNodesChanged(this, getPathTo(node), new int[0], new Object[0]);
				}
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public String getToolTipText(final MouseEvent event)
	{
		final JTable table = (JTable) event.getSource();
		final int row = table.rowAtPoint(event.getPoint());
		
		// Always show the comment as tooltip
		final Object obj = table.getValueAt(row, 2);
		// This is the value which is actually displayed in the table: a String!
		final String value = (String) obj;
		
		if (!value.isEmpty())
		{
			return value;
		}
		return null;
	}
	
	
	// --------------------------------------------------------------------------
	// --- local helper functions -----------------------------------------------
	// --------------------------------------------------------------------------
	private Object[] getPathTo(final Node node)
	{
		// Gather path elements
		final List<Node> list = new LinkedList<Node>();
		
		Node parent = node.getParent();
		while (parent != null)
		{
			list.add(parent);
			parent = parent.getParent();
		}
		
		// Reverse order
		final Iterator<Node> it = list.iterator();
		final Object[] result = new Object[list.size()];
		for (int i = list.size() - 1; i >= 0; i--)
		{
			result[i] = it.next();
		}
		
		return result;
	}
	
	
	
	private org.w3c.dom.Node getComment(final Object obj)
	{
		final Node xmlNode = (Node) obj;
		final org.w3c.dom.Node node = (org.w3c.dom.Node) xmlNode.getReference();
		if (node == null)
		{
			return null;
		}
		org.w3c.dom.Node prevSibl = node.getPreviousSibling();
		while (prevSibl != null)
		{
			if (prevSibl.getNodeType() == org.w3c.dom.Node.COMMENT_NODE)
			{
				return prevSibl;
			}
			if (prevSibl.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
			{
				return null;
			}
			
			prevSibl = prevSibl.getPreviousSibling();
		}
		
		return null;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public int getColumnCount()
	{
		return COLUMNS.length;
	}
	
	
	@Override
	public String getColumnName(final int col)
	{
		return COLUMNS[col];
	}
	
	
	@Override
	public Class<?> getColumnClass(final int col)
	{
		return CLASSES[col];
	}
	
	
	
	public HierarchicalConfiguration getConfiguration()
	{
		return config;
	}
}
