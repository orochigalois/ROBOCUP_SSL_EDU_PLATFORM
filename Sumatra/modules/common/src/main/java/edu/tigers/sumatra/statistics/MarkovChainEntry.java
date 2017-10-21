/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.statistics;

import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class MarkovChainEntry<MappedClass>
{
	
	public Map<MappedClass, Integer>	absoluteTransitions	= new HashMap<>();
	
	
	public int								transitions				= 1;
}
