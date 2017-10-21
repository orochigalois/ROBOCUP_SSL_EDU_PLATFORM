/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit;

import org.apache.log4j.Logger;

import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class SimStopNoMoveCrit extends ASimStopCriterion
{
	private static final Logger	log					= Logger.getLogger(SimStopNoMoveCrit.class.getName());
																	
	@Configurable
	private static double			distTolerance		= 1;
																	
	@Configurable
	private static double			timeBeforeCheck	= 1;
																	
																	
	@Override
	protected boolean checkStopSimulation()
	{
		if (getRuntime(getLatestFrame().getWorldFrame().getTimestamp()) < (timeBeforeCheck * 1e9))
		{
			return false;
		}
		for (ITrackedBot bot : getLatestFrame().getWorldFrame().getBots().values())
		{
			ITrackedBot preBot = getLatestFrame().getPrevFrame().getWorldFrame().getBot(bot.getBotId());
			double dist = GeoMath.distancePP(bot.getPos(), preBot.getPos());
			if (dist > distTolerance)
			{
				return false;
			}
		}
		log.debug("Stopped because no bot moved");
		return true;
	}
	
}
