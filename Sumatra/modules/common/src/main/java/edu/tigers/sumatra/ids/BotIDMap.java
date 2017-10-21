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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sleepycat.persist.model.Persistent;



@Persistent
public class BotIDMap<T> implements IBotIDMap<T>
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long		serialVersionUID	= -5736073179625081902L;
																	
	private final Map<BotID, T>	map;
											
											
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BotIDMap()
	{
		map = new HashMap<BotID, T>();
	}
	
	
	
	public BotIDMap(final int initialCapacity)
	{
		map = new HashMap<BotID, T>(initialCapacity);
	}
	
	
	
	public BotIDMap(final int initialCapacity, final float loadFactor)
	{
		map = new HashMap<BotID, T>(initialCapacity, loadFactor);
	}
	
	
	
	public BotIDMap(final IBotIDMap<T> iMap)
	{
		map = new HashMap<BotID, T>(iMap.size());
		for (final Entry<BotID, T> entry : iMap.entrySet())
		{
			map.put(entry.getKey(), entry.getValue());
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public T get(final BotID id)
	{
		final T result = map.get(id);
		if (result == null)
		{
			throw new NoObjectWithThisIDException("This BotIDMap does not contain a bot with id '" + id + "'!!!");
		}
		return result;
	}
	
	
	@Override
	public T getWithNull(final BotID id)
	{
		return map.get(id);
	}
	
	
	@Override
	public Set<Entry<BotID, T>> entrySet()
	{
		return map.entrySet();
	}
	
	
	@Override
	public T put(final BotID key, final T value)
	{
		return map.put(key, value);
	}
	
	
	@Override
	public Collection<T> values()
	{
		return map.values();
	}
	
	
	@Override
	public void putAll(final IBotIDMap<? extends T> put)
	{
		for (final Entry<BotID, ? extends T> entry : put.entrySet())
		{
			map.put(entry.getKey(), entry.getValue());
		}
	}
	
	
	@Override
	public T remove(final BotID key)
	{
		return map.remove(key);
	}
	
	
	@Override
	public int size()
	{
		return map.size();
	}
	
	
	@Override
	public boolean containsValue(final Object value)
	{
		return map.containsValue(value);
	}
	
	
	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	
	@Override
	public Set<BotID> keySet()
	{
		return map.keySet();
	}
	
	
	@Override
	public void clear()
	{
		map.clear();
	}
	
	
	@Override
	public boolean containsKey(final BotID key)
	{
		return map.containsKey(key);
	}
	
	
	@Override
	public Iterator<Entry<BotID, T>> iterator()
	{
		return map.entrySet().iterator();
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public Map<BotID, T> getContentMap()
	{
		return map;
	}
}
