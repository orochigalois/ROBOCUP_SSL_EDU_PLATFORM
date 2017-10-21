/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.wp.data.MotionContext;
import edu.tigers.sumatra.wp.data.MotionContext.BotInfo;
import edu.tigers.sumatra.wp.kalman.filter.ExtKalmanFilter;
import edu.tigers.sumatra.wp.kalman.filter.IFilter;
import edu.tigers.sumatra.wp.kalman.motionModels.BallMotionModel;



public class PredictionContext
{
	
	
	
	
	private final Map<Integer, IFilter>				yellowBots;
	
	private final Map<Integer, IFilter>				blueBots;
	
	private IFilter										ball;
	
	
	private final Map<Integer, UnregisteredBot>	newYellowBots;
	
	private final Map<Integer, UnregisteredBot>	newBlueBots;
	
	@Configurable(comment = "time which one lookahead prediction step should look ahead (in internalTime)")
	private static double								stepSize					= 0.01;
	@Configurable(comment = "number of lookahead steps which should be performed")
	private static int									maxStepCount			= 20;
	
	@Configurable(comment = "lookahead to add to current timestamp (prediction part)", spezis = { "",
			"SUMATRA" }, defValueSpezis = { "0.06", "0.016" })
	private static double								predictionLookahead	= 0.0;
	
	
	private MotionContext								motionContext;
	
	
	static
	{
		ConfigRegistration.registerClass("wp", PredictionContext.class);
	}
	
	
	
	public PredictionContext()
	{
		ConfigRegistration.applySpezi("wp", SumatraModel.getInstance().getGlobalConfiguration().getString("environment"));
		
		// maps an id (integer) to a ObjectData, maximum 10 elements, 100% load
		yellowBots = new ConcurrentHashMap<Integer, IFilter>(10, 1);
		blueBots = new ConcurrentHashMap<Integer, IFilter>(10, 1);
		
		newYellowBots = new HashMap<Integer, UnregisteredBot>(10, 1);
		newBlueBots = new HashMap<Integer, UnregisteredBot>(10, 1);
		
		getYellowBots().clear();
		getBlueBots().clear();
		
		getNewYellowBots().clear();
		getNewBlueBots().clear();
		
		ball = new ExtKalmanFilter();
		ball.init(new BallMotionModel(), this, 0, new WPCamBall(new CamBall()));
		updateMotionContext(0);
	}
	
	
	
	public void updateMotionContext(final long timestamp)
	{
		motionContext = new MotionContext();
		
		for (IFilter f : getBlueBots().values())
		{
			ABotMotionResult mr = (ABotMotionResult) f.getPrediction(timestamp);
			IVector3 pos = new Vector3(mr.x, mr.y, mr.orientation);
			BotID botId = BotID.createBotId(f.getId(), ETeamColor.BLUE);
			motionContext.getBots().put(botId, new BotInfo(botId, pos));
		}
		
		for (IFilter f : getYellowBots().values())
		{
			ABotMotionResult mr = (ABotMotionResult) f.getPrediction(timestamp);
			IVector3 pos = new Vector3(mr.x, mr.y, mr.orientation);
			BotID botId = BotID.createBotId(f.getId(), ETeamColor.YELLOW);
			motionContext.getBots().put(botId, new BotInfo(botId, pos));
		}
	}
	
	
	
	public int getStepCount()
	{
		return maxStepCount;
	}
	
	
	
	public double getStepSize()
	{
		return stepSize;
	}
	
	
	
	public Map<Integer, IFilter> getYellowBots()
	{
		return yellowBots;
	}
	
	
	
	public Map<Integer, IFilter> getBlueBots()
	{
		return blueBots;
	}
	
	
	
	public IFilter getBall()
	{
		return ball;
	}
	
	
	
	public void setBall(final IFilter ball)
	{
		this.ball = ball;
	}
	
	
	
	public Map<Integer, UnregisteredBot> getNewYellowBots()
	{
		return newYellowBots;
	}
	
	
	
	public Map<Integer, UnregisteredBot> getNewBlueBots()
	{
		return newBlueBots;
	}
	
	
	
	public double getFilterTimeOffset()
	{
		return 0;
	}
	
	
	
	public MotionContext getMotionContext()
	{
		return motionContext;
	}
	
	
	
	public double getPredictionLookahead()
	{
		return predictionLookahead;
	}
}
