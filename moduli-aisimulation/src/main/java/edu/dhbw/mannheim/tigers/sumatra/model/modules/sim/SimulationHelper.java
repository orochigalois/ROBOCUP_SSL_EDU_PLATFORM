/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim;

import org.apache.log4j.Logger;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.SimulationParameters.SimulationObject;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario.ASimulationScenario;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.sumatra.ai.AAgent;
import edu.tigers.sumatra.cam.ACam;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.sim.SumatraBot;
import edu.tigers.sumatra.sim.SumatraCam;



public class SimulationHelper
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SimulationHelper.class.getName());
	
	
	
	public static void setupScenario(final ASimulationScenario scenario)
	{
		SumatraCam cam = getSumatraCam();
		cam.pause();
		resetSimulation();
		
		SimulationParameters p = scenario.setupSimulation();
		
		cam.getBall().setPos(p.getInitBall().getPos());
		cam.getBall().setVel(p.getInitBall().getVel());
		
		for (SumatraBot sBot : cam.getBots())
		{
			SimulationObject oBot = p.getInitBots().get(sBot.getBotId());
			if (oBot != null)
			{
				sBot.setPos(oBot.getPos());
				sBot.setVel(oBot.getVel());
			}
		}
		
		cam.play();
	}
	
	
	
	public static void resetSimulation()
	{
		try
		{
			ACam cam = (ACam) SumatraModel.getInstance().getModule(ACam.MODULE_ID);
			if (cam instanceof SumatraCam)
			{
				SumatraCam sCam = (SumatraCam) cam;
				sCam.reset(0);
			}
		} catch (ModuleNotFoundException e)
		{
			log.error("Could not find cam module");
		}
		
		try
		{
			AAgent ab = (AAgent) SumatraModel.getInstance().getModule(AAgent.MODULE_ID_BLUE);
			ab.reset();
			AAgent ay = (AAgent) SumatraModel.getInstance().getModule(AAgent.MODULE_ID_YELLOW);
			ay.reset();
		} catch (ModuleNotFoundException e)
		{
			log.error("Could not find agent modules");
		}
	}
	
	
	private static SumatraCam getSumatraCam()
	{
		try
		{
			ACam cam = (ACam) SumatraModel.getInstance().getModule(ACam.MODULE_ID);
			if (cam instanceof SumatraCam)
			{
				SumatraCam sCam = (SumatraCam) cam;
				return sCam;
			}
		} catch (ModuleNotFoundException e)
		{
		}
		return null;
	}
}
