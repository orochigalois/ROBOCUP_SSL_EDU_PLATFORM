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
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sleepycat.persist.model.Persistent;



@Persistent
public final class BotIDMapConst<T> implements IBotIDMap<T>
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long	serialVersionUID	= -6737987790216062346L;
																
	
	private Map<BotID, T>		map;
										
										
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	private BotIDMapConst()
	{
	}
	
	
	private BotIDMapConst(final Map<BotID, T> base)
	{
		this.map = base;
	}
	
	
	
	private BotIDMapConst(final IBotIDMap<T> base)
	{
		this(base.getContentMap());
	}
	
	
	
	public static <T> BotIDMapConst<T> unmodifiableBotIDMap(final IBotIDMap<T> base)
	{
		return new BotIDMapConst<T>(base);
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
			throw new NoObjectWithThisIDException("This BotIDMapConst does not contain a bot with id '" + id + "'!!!");
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
		return Collections.unmodifiableSet(map.entrySet());
	}
	
	
	@Override
	public T put(final BotID key, final T value)
	{
		throw new UnsupportedOperationException("Cannot put data in a unmodifiable map");
	}
	
	
	@Override
	public void putAll(final IBotIDMap<? extends T> put)
	{
		throw new UnsupportedOperationException("Cannot put data in a unmodifiable map");
	}
	
	
	@Override
	public Collection<T> values()
	{
		return Collections.unmodifiableCollection(map.values());
	}
	
	
	@Override
	public T remove(final BotID key)
	{
		throw new UnsupportedOperationException("Cannot put data in a unmodifiable map");
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
		return Collections.unmodifiableSet(map.keySet());
	}
	
	
	@Override
	public void clear()
	{
		throw new UnsupportedOperationException("Cannot put data in a unmodifiable map");
	}
	
	
	@Override
	public boolean containsKey(final BotID key)
	{
		return map.containsKey(key);
	}
	
	
	@Override
	public Iterator<Entry<BotID, T>> iterator()
	{
		return new ConstBotIDMapIterator<T>(entrySet());
	}
	
	
	private static final class ConstBotIDMapIterator<T> implements Iterator<Entry<BotID, T>>
	{
		private final Iterator<Entry<BotID, T>> entryIterator;
		
		
		private ConstBotIDMapIterator(final Collection<Entry<BotID, T>> entries)
		{
			this.entryIterator = entries.iterator();
		}
		
		
		@Override
		public boolean hasNext()
		{
			return entryIterator.hasNext();
		}
		
		
		@Override
		public Entry<BotID, T> next()
		{
			return entryIterator.next();
		}
		
		
		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("BotIDMapConst doesn't support mutating operations!");
		}
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
