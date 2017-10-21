/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.offense;

import edu.tigers.sumatra.ai.data.OffensiveStrategy.EOffensiveStrategy;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleDelayState;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleInterceptionState;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleKickState;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleRedirectCatchSpecialMovementState;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleStopState;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleSupportiveAttackerState;



public class OffensiveRole extends AOffensiveRole
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public OffensiveRole()
	{
		super();
		OffensiveRoleKickState kicker = new OffensiveRoleKickState(this);
		OffensiveRoleStopState stop = new OffensiveRoleStopState(this);
		OffensiveRoleDelayState delay = new OffensiveRoleDelayState(this);
		OffensiveRoleRedirectCatchSpecialMovementState redirector = new OffensiveRoleRedirectCatchSpecialMovementState(
				this);
		OffensiveRoleInterceptionState intercept = new OffensiveRoleInterceptionState(this);
		OffensiveRoleSupportiveAttackerState supporter = new OffensiveRoleSupportiveAttackerState(this);
		setInitialState(stop);
		
		addTransition(EOffensiveStrategy.KICK, kicker);
		addTransition(EOffensiveStrategy.DELAY, delay);
		addTransition(EOffensiveStrategy.STOP, stop);
		addTransition(EOffensiveStrategy.REDIRECT_CATCH_SPECIAL_MOVE, redirector);
		addTransition(EOffensiveStrategy.INTERCEPT, intercept);
		addTransition(EOffensiveStrategy.SUPPORTIVE_ATTACKER, supporter);
	}
	
	
	@Override
	public void beforeFirstUpdate()
	{
		EOffensiveStrategy initialState = getAiFrame().getTacticalField().getOffensiveStrategy()
				.getCurrentOffensivePlayConfiguration().get(getBotID());
		
		if (initialState == null)
		{
			int idx = getAiFrame().getPrevFrame().getAICom().getUnassignedStateCounter();
			
			if (idx >= getAiFrame().getTacticalField().getOffensiveStrategy().getUnassignedStrategies().size())
			{
				initialState = EOffensiveStrategy.REDIRECT_CATCH_SPECIAL_MOVE;
			} else
			{
				initialState = getAiFrame().getTacticalField().getOffensiveStrategy().getUnassignedStrategies()
						.get(idx);
				getAiFrame().getPrevFrame().getAICom().setUnassignedStateCounter(idx + 1);
			}
		}
		
		switch (initialState)
		{
			case REDIRECT_CATCH_SPECIAL_MOVE:
				triggerEvent(EOffensiveStrategy.REDIRECT_CATCH_SPECIAL_MOVE);
				break;
			case DELAY:
				triggerEvent(EOffensiveStrategy.DELAY);
				break;
			case GET:
				triggerEvent(EOffensiveStrategy.GET);
				log.warn("Added Get Offenisve, this should not happen anymore!");
				break;
			case INTERCEPT:
				triggerEvent(EOffensiveStrategy.INTERCEPT);
				break;
			case KICK:
				triggerEvent(EOffensiveStrategy.KICK);
				break;
			case STOP:
				triggerEvent(EOffensiveStrategy.STOP);
				break;
			case SUPPORTIVE_ATTACKER:
				triggerEvent(EOffensiveStrategy.SUPPORTIVE_ATTACKER);
				break;
			default:
				break;
		}
	}
	
	
	// ----------------------------------------------------------------------- //
	// -------------------- functions ---------------------------------------- //
	// ----------------------------------------------------------------------- //
	
	// // @Override
	// // public void setNewSkill(final ISkill newSkill)
	// // {
	// // super.setNewSkill(newSkill);
	// //
	// // }
	//
	//
	// 
	// @Override
	// public void setCompleted(final int timeMs)
	// {
	// super.setCompleted(timeMs);
	// }
	//
	//
	// 
	// @Override
	// public ERole getType()
	// {
	// return super.getType();
	// }
	//
	//
	// 
	// @Override
	// public BotID getBotID()
	// {
	// return super.getBotID();
	// }
	//
	//
	// 
	// @Override
	// public IVector2 getPos()
	// {
	// return super.getPos();
	// }
	//
	//
	// 
	// @Override
	// public ITrackedBot getBot()
	// {
	// return super.getBot();
	// }
	//
	//
	// 
	// @Override
	// public boolean hasBeenAssigned()
	// {
	// return super.hasBeenAssigned();
	// }
	//
	//
	// 
	// @Override
	// public boolean isCompleted()
	// {
	// return super.isCompleted();
	// }
	//
	//
	// 
	// @Override
	// public void setCompleted()
	// {
	// super.setCompleted();
	// }
	//
	//
	// @Override
	// public String toString()
	// {
	// return super.toString();
	// }
	//
	//
	// 
	// @Override
	// public AthenaAiFrame getAiFrame()
	// {
	// return super.getAiFrame();
	// }
	//
	//
	// 
	// @Override
	// public WorldFrame getWFrame()
	// {
	// return super.getWFrame();
	// }
	//
	//
	// 
	// @Override
	// public ISkill getCurrentSkill()
	// {
	// return super.getCurrentSkill();
	// }
	
	
}
