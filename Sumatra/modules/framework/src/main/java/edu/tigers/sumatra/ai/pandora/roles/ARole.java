/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.EGameStateTeam;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.NoObjectWithThisIDException;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.statemachine.DoneState;
import edu.tigers.sumatra.statemachine.EventStatePair;
import edu.tigers.sumatra.statemachine.IRoleState;
import edu.tigers.sumatra.statemachine.IStateMachine;
import edu.tigers.sumatra.statemachine.StateMachine;
import edu.tigers.sumatra.thread.NamedThreadFactory;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.ISkill;
import edu.x.framework.skillsystem.skills.IdleSkill;



public abstract class ARole
{
	
	private static final Logger								log				= Logger.getLogger(ARole.class.getName());
																							
	private static final IRoleState							DONE_STATE		= new DoneState();
																							
	private final ERole											type;
																		
	
	private BotID													botID				= BotID.get();
																							
	private boolean												firstUpdate		= true;
																							
	
	private boolean												isCompleted		= false;
																							
	private transient AthenaAiFrame							aiFrame			= null;
																							
	private transient ISkill									currentSkill	= new IdleSkill();
	private transient boolean									newSkill			= true;
	private final transient IStateMachine<IRoleState>	stateMachine;
																		
	private IVector2												lastPos;
																		
																		
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public ARole(final ERole type)
	{
		this.type = type;
		stateMachine = new StateMachine<IRoleState>();
	}
	
	
	// --------------------------------------------------------------------------
	// --- state machine --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final void addTransition(final Enum<?> stateId, final Enum<?> event, final IRoleState state)
	{
		stateMachine.addTransition(new EventStatePair(event, stateId), state);
	}
	
	
	
	public final void addTransition(final Enum<?> event, final IRoleState state)
	{
		stateMachine.addTransition(new EventStatePair(event), state);
	}
	
	
	
	public final void addTransition(final IRoleState fromState, final Enum<?> event, final IRoleState toState)
	{
		stateMachine.addTransition(new EventStatePair(event, fromState), toState);
	}
	
	
	
	public final void addEndTransition(final Enum<?> stateId, final Enum<?> event)
	{
		addTransition(stateId, event, DONE_STATE);
	}
	
	
	
	public final void addEndTransition(final Enum<?> event)
	{
		addTransition(event, DONE_STATE);
	}
	
	
	
	public final void addEndTransition(final IRoleState state, final Enum<?> event)
	{
		addTransition(state, event, DONE_STATE);
	}
	
	
	
	public final void setInitialState(final IRoleState initialState)
	{
		stateMachine.setInitialState(initialState);
	}
	
	
	
	public final void triggerEvent(final Enum<? extends Enum<?>> event)
	{
		if (event != null)
		{
			stateMachine.triggerEvent(event);
		}
	}
	
	
	
	public final boolean doSelfCheck()
	{
		return stateMachine.valid();
	}
	
	
	
	public final Enum<?> getCurrentState()
	{
		if (stateMachine.getCurrentStateId() == null)
		{
			return DoneState.EStateId.DONE;
		}
		return stateMachine.getCurrentStateId().getIdentifier();
	}
	
	
	// --------------------------------------------------------------------------
	// --- interface ------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public boolean isKeeper()
	{
		return false;
	}
	
	
	
	public final void assignBotID(final BotID newBotId, final MetisAiFrame frame)
	{
		if (newBotId.isUninitializedID())
		{
			throw new IllegalArgumentException("Someone tries to initialize role '" + this
					+ "' with an UNINITIALIZED BotID!!!");
		}
		
		BotID oldBotId = botID;
		botID = newBotId;
		if (!oldBotId.isUninitializedID())
		{
			// that does not work well atm...
			// stateMachine.restart();
		}
	}
	
	
	
	public final void updateBefore(final AthenaAiFrame curFrame)
	{
		aiFrame = curFrame;
		lastPos = getPos();
	}
	
	
	
	public final void update(final AthenaAiFrame curFrame)
	{
		updateBefore(curFrame);
		
		if (curFrame.getWorldFrame().tigerBotsAvailable.getWithNull(botID) == null)
		{
			// bot is not there atm. update would result in exceptions...
			// in match mode, this should not happen, because the playfinder will
			// find new plays immediately after one bot was lost
			return;
		}
		
		try
		{
			if (firstUpdate)
			{
				if (hasBeenAssigned())
				{
					beforeFirstUpdate();
					firstUpdate = false;
				} else
				{
					log.error(this + ": Update was called although role has not been assigned!");
					return;
				}
			}
			
			if (isCompleted())
			{
				return;
			}
			
			beforeUpdate();
			
			if (stateMachine.getCurrentStateId() == null)
			{
				setCompleted();
			} else
			{
				stateMachine.update();
			}
			
			reduceSpeedOnStop();
			
			afterUpdate();
		} catch (Exception err)
		{
			log.error("Exception on role update (" + getType() + ")", err);
		}
	}
	
	
	private void reduceSpeedOnStop()
	{
		currentSkill.getMoveCon().setRefereeStop(aiFrame.getTacticalField().getGameState() == EGameStateTeam.STOPPED);
	}
	
	
	
	protected void afterUpdate()
	{
	}
	
	
	
	protected void beforeUpdate()
	{
	}
	
	
	
	protected void beforeFirstUpdate()
	{
	}
	
	
	protected void onCompleted()
	{
	}
	
	
	
	public void onGameStateChanged(final EGameStateTeam gameState)
	{
	}
	
	
	
	public final void setCompleted(final int timeMs)
	{
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(
				"SetRoleCompleted"));
		executor.schedule(new Runnable()
		{
			@Override
			public void run()
			{
				setCompleted();
			}
		}, timeMs, TimeUnit.MILLISECONDS);
	}
	
	
	
	public final ERole getType()
	{
		return type;
	}
	
	
	
	public final BotID getBotID()
	{
		return botID;
	}
	
	
	
	public final IVector2 getPos()
	{
		if (botID.isUninitializedID())
		{
			throw new IllegalStateException("Role '" + this + "' has not been initialized yet!!!");
		}
		try
		{
			return getAiFrame().getWorldFrame().getTiger(botID).getPos();
		} catch (NoObjectWithThisIDException err)
		{
			return lastPos;
		}
	}
	
	
	
	public final ITrackedBot getBot()
	{
		return getWFrame().tigerBotsVisible.get(getBotID());
	}
	
	
	
	public final boolean hasBeenAssigned()
	{
		return !botID.isUninitializedID();
	}
	
	
	
	public final boolean isCompleted()
	{
		return isCompleted;
	}
	
	
	
	public final void setCompleted()
	{
		if (!isCompleted)
		{
			log.trace(this + ": Completed");
			isCompleted = true;
			stateMachine.stop();
			if ((currentSkill.getType() != ESkill.IDLE))
			{
				setNewSkill(new IdleSkill());
			}
			onCompleted();
		}
	}
	
	
	@Override
	public final String toString()
	{
		return type.toString();
	}
	
	
	
	public final AthenaAiFrame getAiFrame()
	{
		return aiFrame;
	}
	
	
	
	public final WorldFrame getWFrame()
	{
		return aiFrame.getWorldFrame();
	}
	
	
	
	public final ISkill getCurrentSkill()
	{
		return currentSkill;
	}
	
	
	
	public final void setNewSkill(final ISkill newSkill)
	{
		currentSkill = newSkill;
		this.newSkill = true;
	}
	
	
	
	public final ISkill getNewSkill()
	{
		if (newSkill)
		{
			newSkill = false;
			return currentSkill;
		}
		return null;
	}
}
