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

import java.util.Set;



public interface ITree
{
	
	Node getNearest(Node target, boolean allowRoot);
	
	
	
	void add(Node father, Node newNode);
	
	
	
	Node getRoot();
	
	
	
	Set<Node> getAllNodes();
	
	
	
	Set<Node> getAllLeafs();
	
	
}
