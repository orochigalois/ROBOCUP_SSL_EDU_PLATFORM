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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.autoreferee.engine.calc.BotPosition;
import edu.tigers.autoreferee.engine.calc.PossibleGoalCalc.PossibleGoal;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



@Persistent
public class AutoRefFrame implements IAutoRefFrame
{
	
	private WorldFrameWrapper			worldFrameWrapper;
	private final ShapeMap				shapes;
	
	private IAutoRefFrame				previousFrame;
	
	private BotPosition					botLastTouchedBall;
	private BotPosition					botTouchedBall;
	
	private IVector2						ballLeftFieldPos;
	
	private List<EGameStateNeutral>	stateHistory;
	
	private PossibleGoal					possibleGoal;
	
	
	
	@SuppressWarnings("unused")
	private AutoRefFrame()
	{
		shapes = new ShapeMap();
	}
	
	
	
	public AutoRefFrame(final IAutoRefFrame previous,
			final WorldFrameWrapper worldFrameWrapper)
	{
		botLastTouchedBall = new BotPosition();
		botTouchedBall = null;
		previousFrame = previous;
		this.worldFrameWrapper = worldFrameWrapper;
		shapes = new ShapeMap();
	}
	
	
	@Override
	public IAutoRefFrame getPreviousFrame()
	{
		return previousFrame;
	}
	
	
	@Override
	public SimpleWorldFrame getWorldFrame()
	{
		return worldFrameWrapper.getSimpleWorldFrame();
	}
	
	
	@Override
	public BotPosition getBotLastTouchedBall()
	{
		return botLastTouchedBall;
	}
	
	
	
	public void setBotLastTouchedBall(final BotPosition botLastTouchedBall)
	{
		this.botLastTouchedBall = botLastTouchedBall;
	}
	
	
	
	@Override
	public Optional<BotPosition> getBotTouchedBall()
	{
		return Optional.ofNullable(botTouchedBall);
	}
	
	
	
	public void setBotTouchedBall(final BotPosition botTouchedBall)
	{
		this.botTouchedBall = botTouchedBall;
	}
	
	
	@Override
	public IVector2 getBallLeftFieldPos()
	{
		return ballLeftFieldPos;
	}
	
	
	
	public void setBallLeftFieldPos(final IVector2 getBallLeftFieldPos)
	{
		ballLeftFieldPos = getBallLeftFieldPos;
	}
	
	
	@Override
	public EGameStateNeutral getGameState()
	{
		return worldFrameWrapper.getGameState();
	}
	
	
	@Override
	public void cleanUp()
	{
		previousFrame = null;
	}
	
	
	@Override
	public List<EGameStateNeutral> getStateHistory()
	{
		return stateHistory;
	}
	
	
	
	public void setStateHistory(final List<EGameStateNeutral> stateHistory)
	{
		this.stateHistory = stateHistory;
	}
	
	
	@Override
	public long getTimestamp()
	{
		return worldFrameWrapper.getSimpleWorldFrame().getTimestamp();
	}
	
	
	@Override
	public RefereeMsg getRefereeMsg()
	{
		return worldFrameWrapper.getRefereeMsg();
	}
	
	
	@Override
	public ShapeMap getShapes()
	{
		return shapes;
	}
	
	
	
	@Override
	public Optional<PossibleGoal> getPossibleGoal()
	{
		return Optional.ofNullable(possibleGoal);
	}
	
	
	
	public void setPossibleGoal(final PossibleGoal possibleGoal)
	{
		this.possibleGoal = possibleGoal;
	}
}
