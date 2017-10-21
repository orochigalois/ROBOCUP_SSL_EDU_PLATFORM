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

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import edu.tigers.sumatra.math.IVector;
import edu.tigers.sumatra.math.VectorN;
import edu.tigers.sumatra.statistics.CollectorVectorStd.Accumulator;



public class CollectorVectorStd implements Collector<IVector, Accumulator, VectorN>
{
	private final IVector	avg;
	
	
	
	public CollectorVectorStd(final IVector avg)
	{
		this.avg = avg;
	}
	
	
	@Override
	public Supplier<Accumulator> supplier()
	{
		return Accumulator::new;
	}
	
	
	@Override
	public BiConsumer<Accumulator, IVector> accumulator()
	{
		return (acc, vec) ->
		{
			IVector tmp = vec.subtractNew(avg);
			acc.vector.add(tmp.multiplyNew(tmp));
			acc.count++;
		};
	}
	
	
	@Override
	public BinaryOperator<Accumulator> combiner()
	{
		return (v1, v2) -> {
			Accumulator acc = new Accumulator();
			acc.vector = v1.vector.addNew(v2.vector);
			acc.count = v1.count + v2.count;
			return acc;
		};
	}
	
	
	@Override
	public Function<Accumulator, VectorN> finisher()
	{
		return acc -> acc.count == 0 ? new VectorN(avg.getNumDimensions()) : acc.vector.multiplyNew(1f / acc.count)
				.applyNew(
						f -> (double) Math.sqrt(f));
	}
	
	
	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics()
	{
		return EnumSet.of(Characteristics.UNORDERED);
	}
	
	protected class Accumulator
	{
		VectorN	vector	= new VectorN(avg.getNumDimensions());
		int		count		= 0;
	}
}
