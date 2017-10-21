/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.fieldPrediction;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.data.TrackedBot;



public class FieldPredictorTest
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private WorldFramePrediction			wfp;
	
	private FieldPredictionInformation	fpi;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@BeforeClass
	public static void init()
	{
		// SumatraSetupHelper.setupSumatra();
	}
	
	
	
	@Before
	public void generateFieldPredictionInformation()
	{
		// prepare
		Vector3 n = new Vector3(0, 0, 0);
		TrackedBall ball = new TrackedBall(n, n, n);
		
		IBotIDMap<ITrackedBot> bots = new BotIDMap<>();
		BotID tiger1BotID = BotID.createBotId(0, ETeamColor.YELLOW);
		TrackedBot tiger1 = new TrackedBot(0, tiger1BotID);
		tiger1.setPos(new Vector2(200, 200));
		tiger1.setVel(new Vector2(0, 0.01));
		bots.put(tiger1BotID, tiger1);
		
		BotID tiger2BotID = BotID.createBotId(1, ETeamColor.YELLOW);
		TrackedBot tiger2 = new TrackedBot(0, tiger2BotID);
		tiger2.setPos(new Vector2(0, 400));
		tiger2.setVel(new Vector2(0.01, 0));
		bots.put(tiger2BotID, tiger2);
		
		FieldPredictor fp = new FieldPredictor(bots.values(), ball);
		
		// do
		wfp = fp.create();
		
		fpi = wfp.getBot(tiger1BotID);
	}
	
	
	
	@Test
	public void testEstimationWithoutCrash()
	{
		if (!(new Vector2(200, 210)).equals(fpi.getPosAt(1), 0.01))
		{
			fail(fpi.getPosAt(1) + " should equal 200,210");
		}
	}
	
	
	
	@Test
	public void testCrash()
	{
		// beware of the crash after 20 seconds
		if (!(new Vector2(200, 400)).equals(fpi.getPosAt(25), 0.01))
		{
			fail(fpi.getPosAt(25) + " should equal 200,400");
		}
	}
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
