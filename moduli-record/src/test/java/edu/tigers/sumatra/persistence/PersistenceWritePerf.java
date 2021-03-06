/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistence;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.persistance.RecordBerkeleyPersistence;
import edu.tigers.sumatra.persistance.RecordFrame;



@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5, callgc = true)
@BenchmarkHistoryChart(labelWith = LabelType.CUSTOM_KEY, maxRuns = 20)
public class PersistenceWritePerf
{
	static
	{
		System.setProperty("jub.customkey", String.valueOf(PersistenceTestHelper.MANY_FRAMES));
	}
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final List<RecordFrame>	manyRecordFrames;
	private final List<RecordFrame>	oneRecordFrame;
	
	
	
	@Rule
	public TestRule						benchmarkRun	= new BenchmarkRule();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public PersistenceWritePerf()
	{
		SumatraModel.noLogging();
		
		PersistenceTestHelper helper = new PersistenceTestHelper();
		manyRecordFrames = helper.createManyRecordFrames();
		oneRecordFrame = helper.createOneRecordFrame();
		helper.close();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Before
	public void before()
	{
		PersistenceTestHelper.cleanup();
	}
	
	
	
	@AfterClass
	public static void afterClass()
	{
		PersistenceTestHelper.cleanup();
	}
	
	
	
	@Test
	@BenchmarkOptions(benchmarkRounds = 50, warmupRounds = 100)
	public void testBerkeley()
	{
		RecordBerkeleyPersistence pers = new RecordBerkeleyPersistence(
				RecordBerkeleyPersistence.getDefaultBasePath() + "/" + PersistenceTestHelper.DB_NAME, false);
		pers.saveRecordFrames(oneRecordFrame);
		pers.close();
	}
	
	
	
	@Test
	@BenchmarkOptions(benchmarkRounds = 50, warmupRounds = 50)
	public void testManyBerkeley()
	{
		RecordBerkeleyPersistence pers = new RecordBerkeleyPersistence(
				RecordBerkeleyPersistence.getDefaultBasePath() + "/" + PersistenceTestHelper.DB_NAME, false);
		pers.saveRecordFrames(manyRecordFrames);
		pers.close();
	}
	
	
	
	// @Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
	public void testEndlessBerkeley()
	{
		PersistenceTestHelper.printMemoryUsage();
		RecordBerkeleyPersistence pers = new RecordBerkeleyPersistence(
				RecordBerkeleyPersistence.getDefaultBasePath() + "/" + PersistenceTestHelper.DB_NAME, false);
		PersistenceTestHelper helper = new PersistenceTestHelper();
		int sum = 0;
		try
		{
			while (true)
			{
				List<RecordFrame> frames = helper.createManyRecordFrames();
				pers.saveRecordFrames(frames);
				sum += frames.size();
				PersistenceTestHelper.printMemoryUsage();
				System.out.println("Sum: " + sum);
			}
		} finally
		{
			pers.close();
			helper.close();
		}
	}
}
