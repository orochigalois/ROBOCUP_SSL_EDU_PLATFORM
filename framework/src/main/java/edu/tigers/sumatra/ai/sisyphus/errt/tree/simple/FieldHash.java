/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.errt.tree.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.map.MultiValueMap;




public class FieldHash<T>
{
	private final List<MultiValueMap<Integer, T>>	positionHashs	= new ArrayList<MultiValueMap<Integer, T>>();
	
	private final int											binSize;
	
	
	
	public FieldHash(final int binSize)
	{
		this.binSize = binSize;
		for (int i = 0; i < 4; i++)
		{
			MultiValueMap<Integer, T> map = new MultiValueMap<>();
			positionHashs.add(map);
		}
	}
	
	
	
	public void add(final double x, final double y, final T object)
	{
		// feeding the four grids with positions of the bots
		for (int i = 0; i < 4; i++)
		{
			positionHashs.get(i).put(hash(x, y, i), object);
		}
	}
	
	
	
	// public Collection<T> findExtensively(final double x, final double y)
	// {
	// Collection<T> returnCollection = find(x, y);
	// int dist = 0;
	// while ((returnCollection == null) && ((dist * binSize) < Geometry.getFieldLength()))
	// {
	// dist++;
	// for (int i = -dist; i < dist; i++)
	// {
	// for (int j = -dist; j < dist; j++)
	// {
	// Collection<T> elems = find(x + (dist * binSize), y + (dist * binSize));
	// if (returnCollection != null)
	// {
	// returnCollection.addAll(elems);
	// } else
	// {
	// returnCollection = elems;
	// }
	// }
	// }
	// }
	// if (returnCollection == null)
	// {
	// returnCollection = Collections.emptyList();
	// }
	// return returnCollection;
	// }
	
	
	
	public Set<T> find(final double x, final double y)
	{
		Set<T> returnCollection = new HashSet<T>();
		// feeding the four grids with positions of the bots
		for (int i = 0; i < 4; i++)
		{
			Collection<T> elems = positionHashs.get(i).getCollection(hash(x, y, i));
			
			if (elems != null)
			{
				Set<T> elemsSet = new HashSet<T>(elems);
				returnCollection.addAll(elemsSet);
				// if (returnCollection != null)
				// {
				// returnCollection.addAll(elems);
				// } else
				// {
				// returnCollection = elems;
				// }
			}
		}
		return returnCollection;
	}
	
	
	private int hash(final double x, final double y, final int i)
	{
		// offset for the four different grids
		int offset_x = (binSize / 2) * (i % 2);
		int offset_y = 0;
		if (i > 2)
		{
			offset_y = (binSize / 2);
		}
		
		// calculate the bin matching to the x and y combination given grid number i
		int a = (int) Math.ceil((x + offset_x) / binSize);
		int b = (int) Math.ceil((y + offset_y) / binSize);
		
		// convert to only positive values
		a = convertToOnlyPositives(a);
		b = convertToOnlyPositives(b);
		
		// Hashing function (Cantors pairing function)
		return (int) ((1f / 2.0) * (((a + b) * (a + b + 1)))) + b;
	}
	
	
	private int convertToOnlyPositives(final int possibleNegative)
	{
		if (possibleNegative < 0)
		{
			return (-possibleNegative * 2) + 1;
		}
		return (possibleNegative * 2);
	}
}
