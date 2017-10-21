/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.motorlearner.collector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.export.CSVExporter;
import edu.tigers.sumatra.export.INumberListable;



public abstract class ADataCollector<ResultType extends INumberListable>
{
	private final EDataCollector			type;
	private final Map<Long, ResultType>	samples	= new LinkedHashMap<>();
																
																
	protected ADataCollector(final EDataCollector type)
	{
		this.type = type;
	}
	
	
	
	public void start()
	{
		samples.clear();
	}
	
	
	
	public void stop()
	{
	}
	
	
	
	public synchronized List<ResultType> getSamples()
	{
		return new ArrayList<>(samples.values());
	}
	
	
	protected synchronized void addSample(final ResultType sample)
	{
		samples.put(System.currentTimeMillis(), sample);
	}
	
	
	
	public synchronized void exportSamples(final String filename)
	{
		CSVExporter exporter = new CSVExporter(filename, false);
		for (Map.Entry<Long, ResultType> entry : samples.entrySet())
		{
			Long t = entry.getKey();
			ResultType sample = entry.getValue();
			List<Number> numbers = sample.getNumberList();
			numbers.add(0, t);
			exporter.addValues(numbers);
		}
		exporter.close();
	}
	
	
	
	public final int getNumSamples()
	{
		return samples.size();
	}
	
	
	
	public final EDataCollector getType()
	{
		return type;
	}
}
