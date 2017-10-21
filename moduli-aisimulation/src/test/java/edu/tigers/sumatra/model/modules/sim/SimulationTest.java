/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.model.modules.sim;

import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.Simulation;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario.AUnitTestSimScenario;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario.RefereeDirectKickSimScenario;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario.RefereeKickoffSimScenario;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario.RefereeStopSimScenario;
import edu.tigers.sumatra.model.SumatraModel;



public class SimulationTest
{
	private static final int NUM_RUNS = 1;
	
	
	static
	{
		SumatraModel.changeLogLevel(Level.INFO);
	}
	
	
	private void evaluateScenario(final AUnitTestSimScenario scenario)
	{
		Simulation.runSimulationBlocking(scenario);
		if (!scenario.getErrorMessages().isEmpty())
		{
			StringBuilder sb = new StringBuilder();
			for (String err : scenario.getErrorMessages())
			{
				sb.append(System.lineSeparator());
				sb.append(err);
			}
			
			Assert.fail("Simulation reported errors: " + sb.toString());
		}
	}
	
	
	
	@Test
	@Ignore
	public void testRefereeStop()
	{
		for (int i = 0; i < NUM_RUNS; i++)
		{
			AUnitTestSimScenario scenario = new RefereeStopSimScenario();
			evaluateScenario(scenario);
		}
	}
	
	
	
	@Test
	@Ignore
	public void testRefereeDirectKick()
	{
		for (int i = 0; i < NUM_RUNS; i++)
		{
			AUnitTestSimScenario scenario = new RefereeDirectKickSimScenario();
			evaluateScenario(scenario);
		}
	}
	
	
	
	@Test
	@Ignore
	public void testRefereeKickoff()
	{
		for (int i = 0; i < NUM_RUNS; i++)
		{
			AUnitTestSimScenario scenario = new RefereeKickoffSimScenario();
			evaluateScenario(scenario);
		}
	}
}
