/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit.ASimStopCriterion;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit.SimStopTimeCrit;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;



public class AUnitTestSimScenario extends ASimulationScenario
{
	private static final Logger	log					= Logger.getLogger(AUnitTestSimScenario.class.getName());
	private final List<String>		errorMessages		= new ArrayList<>();
	
	
	private static long				timeout				= 10 * 60 * 1000;
	
	private static double			preparationTime	= 5;
	
	
	
	public AUnitTestSimScenario()
	{
		setEnableBlue(true);
		setEnableYellow(true);
	}
	
	
	protected void addErrorMessage(final String msg)
	{
		log.warn(msg);
		errorMessages.add(msg);
	}
	
	
	
	public final List<String> getErrorMessages()
	{
		return Collections.unmodifiableList(errorMessages);
	}
	
	
	@Override
	protected void setupStopCriteria(final List<ASimStopCriterion> criteria)
	{
		criteria.add(new SimStopTimeoutCrit(timeout));
	}
	
	
	@Override
	public void onUpdate(final AIInfoFrame aiFrame, final List<RefereeCaseMsg> caseMsgs)
	{
		List<String> errors = getRuleViolationsForAI(aiFrame, caseMsgs);
		for (String msg : errors)
		{
			addErrorMessage(msg);
		}
		if (!errors.isEmpty())
		{
			if (isStopAfterCompletition())
			{
				stopSimulation();
			} else
			{
				pause();
			}
		}
	}
	
	
	private class SimStopTimeoutCrit extends SimStopTimeCrit
	{
		
		public SimStopTimeoutCrit(final long timeout)
		{
			super(timeout);
		}
		
		
		@Override
		protected boolean checkStopSimulation()
		{
			if (super.checkStopSimulation())
			{
				addErrorMessage("Test case timed out after " + SimStopTimeoutCrit.this.getTimeout()
						+ ". Check your stop criteria!");
				return true;
			}
			return false;
		}
	}
	
	
	
	protected final long getScenarioTimeout()
	{
		return timeout;
	}
	
	
	
	protected final double getPreparationTime()
	{
		return preparationTime;
	}
}
