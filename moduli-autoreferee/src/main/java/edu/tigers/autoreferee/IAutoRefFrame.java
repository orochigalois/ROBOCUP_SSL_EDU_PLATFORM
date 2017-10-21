/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee;

import java.util.List;
import java.util.Optional;

import edu.tigers.autoreferee.engine.calc.BotPosition;
import edu.tigers.autoreferee.engine.calc.PossibleGoalCalc.PossibleGoal;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;



public interface IAutoRefFrame
{
	
	
	public IAutoRefFrame getPreviousFrame();
	
	
	
	public SimpleWorldFrame getWorldFrame();
	
	
	
	public EGameStateNeutral getGameState();
	
	
	
	public BotPosition getBotLastTouchedBall();
	
	
	
	public Optional<BotPosition> getBotTouchedBall();
	
	
	
	public IVector2 getBallLeftFieldPos();
	
	
	
	public RefereeMsg getRefereeMsg();
	
	
	
	public List<EGameStateNeutral> getStateHistory();
	
	
	
	public long getTimestamp();
	
	
	
	public void cleanUp();
	
	
	
	public ShapeMap getShapes();
	
	
	
	public Optional<PossibleGoal> getPossibleGoal();
	
}
