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

import java.util.Map;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.SimulationParameters.SimulationObject;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.Vector3;



public class DefaultSimScenario extends ASimulationScenario
{
	
	@Override
	protected void setupBots(final Map<BotID, SimulationObject> bots)
	{
		double y = 1000;
		for (int i = 0; i < 6; i++)
		{
			SimulationObject sob = new SimulationObject();
			sob.setPos(new Vector3(-1000 + (300 * i), y, 0));
			bots.put(BotID.createBotId(i, ETeamColor.BLUE), sob);
			SimulationObject soy = new SimulationObject();
			soy.setPos(new Vector3(-1000 + (300 * i), -y, 0));
			bots.put(BotID.createBotId(i, ETeamColor.YELLOW), soy);
		}
	}
	
}
