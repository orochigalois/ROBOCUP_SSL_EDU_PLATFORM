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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.tigers.sumatra.persistance.RecordBerkeleyPersistence;
import edu.tigers.sumatra.persistance.RecordFrame;



public class BerkeleyPersistenceTest
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public BerkeleyPersistenceTest()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Test
	public void testSaveLoadBerkeleyRecordFrame()
	{
		RecordBerkeleyPersistence pers = new RecordBerkeleyPersistence(PersistenceTestHelper.DB_NAME, false);
		PersistenceTestHelper helper = new PersistenceTestHelper();
		List<RecordFrame> origFrames = helper.createOneRecordFrame();
		helper.close();
		pers.saveRecordFrames(origFrames);
		pers.close();
		
		pers = new RecordBerkeleyPersistence(PersistenceTestHelper.DB_NAME, false);
		pers.getRecordFrame(pers.getFirstKey());
		pers.close();
	}
	
	
	
	@Test
	@Ignore
	public void testLoadAllBerkeleyDbs()
	{
		String home = System.getProperty("user.home");
		String base = home + "/.sumatra-berkeley/";
		new File(base).mkdir();
		DateFormat dfmt = new SimpleDateFormat("yy-MM-dd");
		String dbName = "AI_" + dfmt.format(new Date());
		
		// create new db for today into home folder, if not already existing
		if (!new File(base + dbName).exists())
		{
			System.out.println("Create new db: " + dbName);
			RecordBerkeleyPersistence pers = new RecordBerkeleyPersistence(base + "/" + dbName, false);
			PersistenceTestHelper helper = new PersistenceTestHelper();
			List<RecordFrame> origFrames = helper.createOneRecordFrame();
			helper.close();
			pers.saveRecordFrames(origFrames);
			pers.close();
		}
	}
	
	
	
	@After
	public void after()
	{
		PersistenceTestHelper.cleanup();
	}
	
	
	
	@Before
	public void before()
	{
		PersistenceTestHelper.cleanup();
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
