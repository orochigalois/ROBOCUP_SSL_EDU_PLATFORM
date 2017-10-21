/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.errt.tree;

import java.util.LinkedList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent(version = 1)
public class Node extends Vector2
{
	// ------------------------------------------------------------------------
	// --- variable(s) --------------------------------------------------------
	// ------------------------------------------------------------------------
	// root node
	private Node					parent		= null;
	
	// all children
	private final List<Node>	children		= new LinkedList<Node>();
	
	// the children which points to the children which leads to the goal
	// parent + successor are a double linked list
	// @deprecated("Double linked list not really necessary anymore")
	@Deprecated
	private Node					successor	= null;
	
	
	// ------------------------------------------------------------------------
	// --- constructor(s) -----------------------------------------------------
	// ------------------------------------------------------------------------
	
	@SuppressWarnings("unused")
	private Node()
	{
	}
	
	
	
	public Node(final double x, final double y)
	{
		super(x, y);
	}
	
	
	
	public Node(final IVector2 pos)
	{
		super(pos);
	}
	
	
	// ------------------------------------------------------------------------
	// --- method(s) ----------------------------------------------------------
	// ------------------------------------------------------------------------
	
	public void addChild(final Node newNode)
	{
		children.add(newNode);
		newNode.parent = this;
	}
	
	
	
	@Deprecated
	public void setSuccessor(final Node suc)
	{
		successor = suc;
	}
	
	
	
	@Deprecated
	public Node getSuccessor()
	{
		return successor;
	}
	
	
	
	public List<Node> getChildren()
	{
		return children;
	}
	
	
	
	public List<Node> getChildrenRecursive()
	{
		final List<Node> listOfAll = new LinkedList<Node>();
		listOfAll.addAll(children);
		for (final Node child : children)
		{
			listOfAll.addAll(child.getChildrenRecursive());
		}
		return listOfAll;
	}
	
	
	
	public Node copy()
	{
		return new Node(new Vector2(x(), y()));
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Node getParent()
	{
		return parent;
	}
	
	
	
	public void setParent(final Node parent)
	{
		this.parent = parent;
	}
	
	
}