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

import java.util.List;

import edu.tigers.sumatra.math.IVector;



public class VectorDataStatistics extends ADataStatistics<IVector, IVector>
{
	private final int	dim;
	
	
	
	public VectorDataStatistics(final List<IVector> samples, final int dim)
	{
		super(samples);
		this.dim = dim;
	}
	
	
	@Override
	protected IVector getAverage(final List<IVector> samples)
	{
		return samples.stream().collect(new CollectorVectorAvg(dim));
	}
	
	
	@Override
	protected IVector getStandardDeviation(final List<IVector> samples)
	{
		return samples.stream().collect(new CollectorVectorStd(getAverage(samples)));
	}
	
	
	@Override
	protected IVector getMaximas(final List<IVector> samples)
	{
		return samples.stream().collect(new CollectorVectorMax(dim));
	}
	
	
	@Override
	protected IVector getMinimas(final List<IVector> samples)
	{
		return samples.stream().collect(new CollectorVectorMin(dim));
	}
	
	
	@Override
	protected IVector getRange(final List<IVector> samples)
	{
		return getMaximas(samples).subtractNew(getMinimas(samples)).absNew();
	}
}
