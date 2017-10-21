/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.drawable.IDrawableTool;



@Persistent
public class ShapeMap
{
	private final Map<String, ShapeLayer>	categories	= new HashMap<>();
	
	
	public interface IShapeLayer
	{
		
		String getCategory();
		
		
		
		String getLayerName();
		
		
		
		String getId();
		
		
		
		default boolean persist()
		{
			return true;
		}
		
		
		
		default boolean isVisibleByDefault()
		{
			return false;
		}
		
		
		
		default int getOrderId()
		{
			return 50;
		}
		
		
	}
	
	@Persistent
	private static class ShapeLayer implements Comparable<ShapeLayer>
	{
		final IShapeLayer			shapeLayer;
		final List<ShapeList>	shapes	= new ArrayList<>();
		
		
		@SuppressWarnings("unused")
		private ShapeLayer()
		{
			this((IShapeLayer) null);
		}
		
		
		
		public ShapeLayer(final IShapeLayer shapeLayer)
		{
			this.shapeLayer = shapeLayer;
			shapes.add(new ShapeList());
		}
		
		
		
		public ShapeLayer(final ShapeLayer o)
		{
			shapeLayer = o.shapeLayer;
			for (ShapeList l : o.shapes)
			{
				shapes.add(new ShapeList(l));
			}
		}
		
		
		@Override
		public int compareTo(final ShapeLayer o)
		{
			return Integer.compare(shapeLayer.getOrderId(), o.shapeLayer.getOrderId());
		}
		
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = (prime * result) + ((shapeLayer == null) ? 0 : shapeLayer.hashCode());
			return result;
		}
		
		
		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			ShapeLayer other = (ShapeLayer) obj;
			if (shapeLayer == null)
			{
				if (other.shapeLayer != null)
				{
					return false;
				}
			} else if (!shapeLayer.equals(other.shapeLayer))
			{
				return false;
			}
			return true;
		}
		
	}
	
	@Persistent
	private static class ShapeList
	{
		List<IDrawableShape>	shapes	= new ArrayList<>();
		boolean					inverted	= false;
		
		
		
		public ShapeList()
		{
		}
		
		
		
		public ShapeList(final ShapeList o)
		{
			shapes = new ArrayList<>(o.shapes);
			inverted = o.inverted;
		}
	}
	
	
	
	public ShapeMap()
	{
	}
	
	
	
	public ShapeMap(final ShapeMap o)
	{
		for (ShapeLayer sl : o.categories.values())
		{
			categories.put(sl.shapeLayer.getId(), new ShapeLayer(sl));
		}
	}
	
	
	
	public List<IDrawableShape> get(final IShapeLayer shapeLayer)
	{
		ShapeLayer sl = categories.get(shapeLayer.getId());
		if (sl == null)
		{
			sl = new ShapeLayer(shapeLayer);
			categories.put(shapeLayer.getId(), sl);
		}
		
		return sl.shapes.get(0).shapes;
	}
	
	
	
	public void put(final IShapeLayer shapeLayer, final List<IDrawableShape> shapes)
	{
		for (IDrawableShape shape : shapes)
		{
			assert shape != null;
		}
		ShapeLayer sl = new ShapeLayer(shapeLayer);
		ShapeList l = new ShapeList();
		l.shapes = shapes;
		sl.shapes.add(l);
		categories.put(shapeLayer.getId(), sl);
	}
	
	
	
	public void remove(final String layerId)
	{
		categories.remove(layerId);
	}
	
	
	
	public void merge(final ShapeMap map)
	{
		// thread safe iteration
		Collection<ShapeLayer> catsCopy = new ArrayList<>(map.categories.values());
		for (ShapeLayer newsl : catsCopy)
		{
			ShapeLayer sl = categories.get(newsl.shapeLayer.getId());
			if (sl == null)
			{
				sl = new ShapeLayer(newsl.shapeLayer);
				categories.put(newsl.shapeLayer.getId(), sl);
			}
			sl.shapes.addAll(newsl.shapes);
		}
	}
	
	
	
	public void removeNonPersistent()
	{
		categories.entrySet().removeIf(en -> !en.getValue().shapeLayer.persist());
	}
	
	
	
	public List<IShapeLayer> getAllShapeLayers()
	{
		return categories.values().stream().sorted().map(sl -> sl.shapeLayer)
				.collect(Collectors.toList());
	}
	
	
	
	public void setInverted(final boolean inverted)
	{
		categories.values().forEach(sl -> sl.shapes.forEach(l -> l.inverted = inverted));
	}
	
	
	
	public void paint(final Graphics2D g, final IDrawableTool tool)
	{
		categories.values().stream()
				.sorted()
				.forEach(sl -> sl.shapes
						.forEach(s -> s.shapes
								.forEach(ss -> {
									Graphics2D gDerived = (Graphics2D) g.create();
									ss.paintShape(gDerived, tool, s.inverted);
									gDerived.dispose();
								})));
	}
}
