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



public abstract class ADataStatistics<ResultType, SampleType>
{
	private final List<SampleType>	samples;
	
	
	
	public ADataStatistics(final List<SampleType> samples)
	{
		this.samples = samples;
	}
	
	
	
	public ResultType getAverage()
	{
		return getAverage(samples);
	}
	
	
	
	public ResultType getStandardDeviation()
	{
		return getStandardDeviation(samples);
	}
	
	
	
	public ResultType getMaximas()
	{
		return getMaximas(samples);
	}
	
	
	
	public ResultType getMinimas()
	{
		return getMinimas(samples);
	}
	
	
	
	public ResultType getRange()
	{
		return getRange(samples);
	}
	
	
	
	protected abstract ResultType getAverage(List<SampleType> samples);
	
	
	
	protected abstract ResultType getStandardDeviation(List<SampleType> samples);
	
	
	
	protected abstract ResultType getMaximas(List<SampleType> samples);
	
	
	
	protected abstract ResultType getMinimas(List<SampleType> samples);
	
	
	
	protected abstract ResultType getRange(List<SampleType> samples);
}
