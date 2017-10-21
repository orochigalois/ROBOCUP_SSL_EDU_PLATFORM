/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.tigers.sumatra.ai.data.BotDistance;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.ACalculator;
import edu.tigers.sumatra.ids.BotIDMapConst;
import edu.tigers.sumatra.ids.ETeam;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class BotToBallDistanceCalc extends ACalculator
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private final ETeam team;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BotToBallDistanceCalc(final ETeam team)
	{
		ETeam.assertOneTeam(team);
		this.team = team;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@Override
	public void doCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		WorldFrame wFrame = baseAiFrame.getWorldFrame();
		IBotIDMap<? extends ITrackedBot> bots = getBots(wFrame);
		
		
		final List<BotDistance> distances = new ArrayList<BotDistance>(bots.size());
		for (ITrackedBot bot : bots.values())
		{
			final double distanceToBall = GeoMath.distancePP(bot.getPos(), wFrame.getBall().getPos());
			distances.add(new BotDistance(bot, distanceToBall));
		}
		
		Collections.sort(distances, BotDistance.ASCENDING);
		
		if (team == ETeam.TIGERS)
		{
			newTacticalField.setTigersToBallDist(distances);
		} else
		{
			newTacticalField.setEnemiesToBallDist(distances);
		}
		
	}
	
	
	private BotIDMapConst<? extends ITrackedBot> getBots(final WorldFrame wf)
	{
		if (team == ETeam.TIGERS)
		{
			return wf.getTigerBotsVisible();
		}
		return wf.getFoeBots();
	}
	
	
	@Override
	public void fallbackCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		final List<BotDistance> distances = new ArrayList<BotDistance>(0);
		
		if (team == ETeam.TIGERS)
		{
			newTacticalField.setTigersToBallDist(distances);
		} else
		{
			newTacticalField.setEnemiesToBallDist(distances);
		}
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
