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

import java.util.Map;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.BotIDMapConst;
import edu.tigers.sumatra.ids.IBotIDMap;



@Persistent(version = 2)
public class WorldFramePrediction
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private final IBotIDMap<FieldPredictionInformation>	bots;
																			
																			
	private final FieldPredictionInformation					ball;
																			
																			
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@SuppressWarnings("unused")
	private WorldFramePrediction()
	{
		ball = null;
		bots = null;
	}
	
	
	
	public WorldFramePrediction(final IBotIDMap<FieldPredictionInformation> bots, final FieldPredictionInformation ball)
	{
		this.bots = BotIDMapConst.unmodifiableBotIDMap(bots);
		this.ball = ball;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public WorldFramePrediction mirrorNew()
	{
		IBotIDMap<FieldPredictionInformation> newBots = new BotIDMap<FieldPredictionInformation>();
		for (Map.Entry<BotID, FieldPredictionInformation> entry : bots.entrySet())
		{
			newBots.put(entry.getKey(), entry.getValue().mirror());
		}
		
		return new WorldFramePrediction(newBots, ball.mirror());
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public FieldPredictionInformation getBall()
	{
		return ball;
	}
	
	
	
	public FieldPredictionInformation getBot(final BotID bot)
	{
		return bots.getWithNull(bot);
	}
	
	
	
	public IBotIDMap<FieldPredictionInformation> getBots()
	{
		return bots;
	}
}
