/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



public interface IBotIDMap<T> extends Serializable, Iterable<Entry<BotID, T>>
{
	
	T get(BotID id);
	
	
	
	T getWithNull(BotID id);
	
	
	
	Set<Entry<BotID, T>> entrySet();
	
	
	
	T put(BotID key, T value);
	
	
	
	Collection<T> values();
	
	
	
	void putAll(IBotIDMap<? extends T> put);
	
	
	
	T remove(BotID key);
	
	
	
	int size();
	
	
	
	boolean containsValue(Object value);
	
	
	
	boolean isEmpty();
	
	
	
	Set<BotID> keySet();
	
	
	
	void clear();
	
	
	
	boolean containsKey(BotID key);
	
	
	
	Map<BotID, T> getContentMap();
}
