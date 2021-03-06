/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.vector;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector2f;



@BenchmarkMethodChart(filePrefix = "benchmark-mutability")
@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 100, callgc = false)
@BenchmarkHistoryChart(labelWith = LabelType.CUSTOM_KEY, maxRuns = 20)
@Ignore
public class VectorMutabilityPerfTest extends AbstractBenchmark
{
	private static final int	NUM_RUNS	= 1_000_000;
	private static final long	SEED		= 42;
													
													
	private IVector2 getVector2(final Random rnd)
	{
		return new Vector2f(rnd.nextDouble(), rnd.nextDouble());
	}
	
	
	static
	{
		System.setProperty("jub.customkey", String.valueOf(NUM_RUNS));
	}
	
	
	
	@Test
	public void testMutableAddition()
	{
		Random rnd = new Random(SEED);
		Vector2 v = new Vector2();
		for (int i = 0; i < NUM_RUNS; i++)
		{
			v.add(getVector2(rnd));
		}
		if (v.isZeroVector())
		{
			System.out.println("hoppala");
		}
	}
	
	
	
	@Test
	public void testImmutableAddition()
	{
		Random rnd = new Random(SEED);
		IVector2 v = new Vector2f();
		for (int i = 0; i < NUM_RUNS; i++)
		{
			v = v.addNew(getVector2(rnd));
		}
		if (v.isZeroVector())
		{
			System.out.println("hoppala");
		}
	}
}
