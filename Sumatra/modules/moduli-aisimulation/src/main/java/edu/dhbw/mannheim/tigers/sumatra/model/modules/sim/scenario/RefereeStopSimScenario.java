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

import java.util.List;
import java.util.Map;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.SimulationParameters.SimulationObject;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit.ASimStopCriterion;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit.SimStopNoMoveCrit;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.Vector3;



public class RefereeStopSimScenario extends AUnitTestSimScenario
{
	@Override
	protected void setupStopCriteria(final List<ASimStopCriterion> criteria)
	{
		super.setupStopCriteria(criteria);
		criteria.add(new SimStopNoMoveCrit());
	}
	
	
	@Override
	protected void setupBots(final Map<BotID, SimulationObject> bots)
	{
		setupBotsFormation1(bots);
		// put one bot near ball to force it to drive away
		bots.put(BotID.createBotId(5, ETeamColor.BLUE), new SimulationObject(new Vector3(150, 0, 0)));
	}
	
	
	@Override
	protected SimulationObject setupBall()
	{
		return new SimulationObject(new Vector3(0, 0, 0));
	}
	
	
	@Override
	public void onUpdate(final AIInfoFrame aiFrame, final List<RefereeCaseMsg> caseMsgs)
	{
		// give the bots some time
		if (getSimTimeSinceStart(aiFrame.getWorldFrame().getTimestamp()) < getPreparationTime())
		{
			return;
		}
		
		super.onUpdate(aiFrame, caseMsgs);
	}
	
	
	@Override
	protected Command getInitialRefereeCmd()
	{
		return Command.STOP;
	}
}
