/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.errt.tree.kd;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.sisyphus.errt.tree.ITree;
import edu.tigers.sumatra.ai.sisyphus.errt.tree.Node;
import edu.tigers.sumatra.math.IVector2;



@Persistent(version = 1)
public class KDTree implements ITree
{
	
	
	private KDNode							root;
	
	private final Map<Node, KDNode>	mapOfAllNodes;
	private final Set<Node>				setOfLeafs;
												
												
	
	public KDTree()
	{
		mapOfAllNodes = new HashMap<Node, KDNode>();
		setOfLeafs = new HashSet<Node>();
	}
	
	
	
	public KDTree(final IVector2 root, final Node goal)
	{
		this();
		this.root = new KDNode(new Node(root));
		this.root.setDepth(0);
		mapOfAllNodes.put(this.root.getVal(), this.root);
	}
	
	
	
	@Override
	public Node getNearest(final Node target, final boolean allowRoot)
	{
		return root.getNearest(target).getVal();
	}
	
	
	@Override
	public void add(final Node father, final Node newNode)
	{
		
		KDNode kdFather = mapOfAllNodes.get(father);
		KDNode kdChild = new KDNode(newNode);
		
		// add it to the list of all nodes
		if (!mapOfAllNodes.containsKey(newNode))
		{
			mapOfAllNodes.put(newNode, kdChild);
		}
		
		kdFather.addChild(kdChild);
	}
	
	
	@Override
	public Node getRoot()
	{
		return root.getVal();
	}
	
	
	
	public KDNode getKDRoot()
	{
		return root;
	}
	
	
	
	public void addAll(final Set<Node> nodes)
	{
		root = buildTree(new ArrayList<Node>(nodes), 0);
	}
	
	
	private KDNode buildTree(final List<Node> nodes, final int depth)
	{
		if ((depth % 2) == 0)
		{
			// Sort against X-Axis
			Collections.sort(
					nodes,
					(Comparator<Node>) (final Node n1, final Node n2) -> Double.valueOf(n1.x()).compareTo(
							Double.valueOf(n2.x())));
		} else
		{
			// Sort against Y-Axis
			Collections.sort(
					nodes,
					(Comparator<Node>) (final Node n1, final Node n2) -> Double.valueOf(n1.y()).compareTo(
							Double.valueOf(n2.y())));
		}
		KDNode kdFather;
		if (nodes.size() > 2)
		{
			int median = (int) Math.floor((nodes.size() - 1) / (double) 2);
			Node father = nodes.get(median);
			kdFather = new KDNode(father);
			// sublist(from inclusive index to exclusive index)
			List<Node> leftNodes = nodes.subList(0, median);
			kdFather.addLeft(buildTree(leftNodes, depth + 1));
			List<Node> rightNodes = nodes.subList(median + 1, nodes.size());
			kdFather.addRight(buildTree(rightNodes, depth + 1));
		} else if (nodes.size() == 2)
		{
			kdFather = new KDNode(nodes.get(0));
			Node rightLeaf = nodes.get(1);
			kdFather.addRight(new KDNode(rightLeaf));
			setOfLeafs.add(rightLeaf);
		} else if (nodes.size() == 1)
		{
			Node leaf = nodes.get(0);
			kdFather = new KDNode(leaf);
			setOfLeafs.add(leaf);
		} else
		{
			throw new IllegalArgumentException("nodes should not be empty");
		}
		kdFather.setDepth(depth);
		mapOfAllNodes.put(kdFather.getVal(), kdFather);
		return kdFather;
	}
	
	
	@Override
	public Set<Node> getAllNodes()
	{
		return mapOfAllNodes.keySet();
	}
	
	
	@Override
	public Set<Node> getAllLeafs()
	{
		return setOfLeafs;
	}
}
