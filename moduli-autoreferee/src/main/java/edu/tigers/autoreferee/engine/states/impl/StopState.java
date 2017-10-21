/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.states.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.g3force.configurable.Configurable;

import edu.tigers.autoreferee.AutoRefConfig;
import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.AutoRefMath;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.NGeometry;
import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.engine.states.IAutoRefStateContext;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.cam.ACam;
import edu.tigers.sumatra.drawable.DrawableCircle;
import edu.tigers.sumatra.drawable.DrawablePoint;
import edu.tigers.sumatra.drawable.DrawableText;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3f;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.vis.EWpShapesLayer;



public class StopState extends AbstractAutoRefState
{
	private static final Color	PLACEMENT_CIRCLE_COLOR			= Color.BLUE;
	
	@Configurable(comment = "[ms] Time to wait before performing an action after reaching the stop state")
	private static long			STOP_WAIT_TIME_MS					= 2_000;		// ms
																									
	@Configurable(comment = "[ms] The time to wait after all bots have come to a stop and the ball has been placed correctly")
	private static long			READY_WAIT_TIME_MS				= 3_000;
	
	private Long					readyTime;
	private boolean				simulationPlacementAttempted	= false;
	
	
	static
	{
		AbstractAutoRefState.registerClass(StopState.class);
	}
	
	
	
	public StopState()
	{
	}
	
	
	@Override
	protected void prepare(final IAutoRefFrame frame)
	{
		readyTime = null;
		simulationPlacementAttempted = false;
	}
	
	
	@Override
	public void doUpdate(final IAutoRefFrame frame, final IAutoRefStateContext ctx)
	{
		if (ctx.getFollowUpAction() == null)
		{
			setCanProceed(false);
			return;
		}
		
		FollowUpAction action = ctx.getFollowUpAction();
		
		TrackedBall ball = frame.getWorldFrame().getBall();
		List<IDrawableShape> shapes = frame.getShapes().get(EWpShapesLayer.AUTOREFEREE);
		
		IVector2 kickPos = determineKickPos(action);
		visualizeKickPos(shapes, kickPos);
		
		
		if (!timeElapsedSinceEntry(STOP_WAIT_TIME_MS))
		{
			return;
		}
		
		setCanProceed(true);
		
		boolean ballPlaced = checkBallPlaced(ball, kickPos, shapes);
		boolean ballStationary = AutoRefMath.ballIsStationary(ball);
		
		boolean botsCorrectDistance = checkBotStopDistance(frame, shapes);
		boolean readyWaitTimeOver = false;
		
		if (ballPlaced && botsCorrectDistance)
		{
			if (readyTime == null)
			{
				readyTime = frame.getTimestamp();
			}
			long waitTimeNS = frame.getTimestamp() - readyTime;
			readyWaitTimeOver = waitTimeNS > TimeUnit.MILLISECONDS.toNanos(READY_WAIT_TIME_MS);
			drawReadyCircle((int) ((TimeUnit.NANOSECONDS.toMillis(waitTimeNS) * 100L) / READY_WAIT_TIME_MS),
					ball.getPos(), shapes);
		} else
		{
			readyTime = null;
		}
		
		if (ballStationary && !ballPlaced)
		{
			if (!placementWasAttempted(frame) && (AutoRefConfig.getBallPlacementTeams().size() > 0) && ball.isOnCam())
			{
				// Try to place the ball
				sendCommandIfReady(ctx, getPlacementCommand(kickPos, action.getTeamInFavor()));
				return;
			} else if (!simulationPlacementAttempted)
			{
				tryPlaceBallInSimulation(kickPos);
				simulationPlacementAttempted = true;
			}
		}
		
		if (readyWaitTimeOver || ctx.doProceed())
		{
			RefCommand cmd = new RefCommand(action.getCommand());
			if (ctx.doProceed())
			{
				sendCommand(ctx, cmd);
			} else
			{
				sendCommandIfReady(ctx, cmd);
			}
			
		}
	}
	
	
	private void visualizeKickPos(final List<IDrawableShape> shapes, final IVector2 kickPos)
	{
		double radius = AutoRefConfig.getBallPlacementAccuracy();
		
		shapes.add(new DrawableCircle(kickPos, radius, PLACEMENT_CIRCLE_COLOR));
		shapes.add(new DrawableCircle(kickPos, radius * 2, PLACEMENT_CIRCLE_COLOR));
		shapes.add(new DrawablePoint(kickPos, Color.BLACK));
		
		IVector2 textPos = kickPos.addNew(new Vector2(radius * 2, radius * 2));
		DrawableText placementText = new DrawableText(textPos, "New Ball Pos", Color.BLACK);
		placementText.setFontSize(placementText.getFontSize() * 2);
		shapes.add(placementText);
	}
	
	
	
	private RefCommand getPlacementCommand(final IVector2 kickPos, final ETeamColor kickExecutingTeam)
	{
		List<ETeamColor> capableTeams = AutoRefConfig.getBallPlacementTeams();
		if (capableTeams.size() == 0)
		{
			return null;
		}
		
		
		ETeamColor placingTeam = capableTeams.get(0);
		
		ETeamColor preference = AutoRefConfig.getBallPlacementPreference();
		if ((capableTeams.size() > 1))
		{
			
			if (preference.isNonNeutral())
			{
				placingTeam = preference;
			} else
			{
				if (kickExecutingTeam.isNonNeutral())
				{
					placingTeam = kickExecutingTeam;
				}
			}
		}
		
		Command cmd = placingTeam == ETeamColor.BLUE ? Command.BALL_PLACEMENT_BLUE : Command.BALL_PLACEMENT_YELLOW;
		return new RefCommand(cmd, kickPos);
	}
	
	
	private IVector2 determineKickPos(final FollowUpAction action)
	{
		switch (action.getActionType())
		{
			case DIRECT_FREE:
			case INDIRECT_FREE:
			case FORCE_START:
				return action.getNewBallPosition().get();
			case KICK_OFF:
				return NGeometry.getCenter();
			case PENALTY:
				return NGeometry.getPenaltyMark(action.getTeamInFavor().opposite());
			default:
				throw new IllegalArgumentException("Update the StopState to handle the new ActionType: "
						+ action.getActionType());
		}
	}
	
	
	private List<ETeamColor> determineAttemptedPlacements(final IAutoRefFrame frame)
	{
		List<ETeamColor> teams = new ArrayList<>();
		
		// Only search for attempts which were performed directly before this stop state
		List<EGameStateNeutral> stateHist = frame.getStateHistory();
		for (int i = 1; i < stateHist.size(); i++)
		{
			EGameStateNeutral state = stateHist.get(i);
			if (state.isBallPlacement())
			{
				teams.add(state.getTeamColor());
			} else
			{
				break;
			}
		}
		
		return teams;
	}
	
	
	private boolean placementWasAttempted(final IAutoRefFrame frame)
	{
		return determineAttemptedPlacements(frame).size() >= 1;
	}
	
	
	private void tryPlaceBallInSimulation(final IVector2 pos)
	{
		try
		{
			ACam cam = (ACam) SumatraModel.getInstance().getModule(ACam.MODULE_ID);
			cam.replaceBall(new Vector3f(pos, 0), AVector3.ZERO_VECTOR);
		} catch (ModuleNotFoundException e)
		{
		}
	}
}
