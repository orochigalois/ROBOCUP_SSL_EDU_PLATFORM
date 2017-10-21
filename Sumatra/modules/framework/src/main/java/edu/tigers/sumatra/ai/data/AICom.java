/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;



public class AICom
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private IVector2			offensiveRolePassTarget		= null;
	
	private BotID				offensiveRolePassTargetID	= null;
	
	private int					specialMoveCounter			= 0;
	
	private int					unassignedStateCounter		= 0;
	
	// this is used to send responses from SpecialMoveState to OffensiveStrategy
	private boolean			responded						= false;
	
	private List<IVector2>	delayMoves						= new ArrayList<IVector2>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void setOffensiveRolePassTargetID(final BotID passTarget)
	{
		offensiveRolePassTargetID = passTarget;
	}
	
	
	
	public BotID getOffensiveRolePassTargetID()
	{
		return offensiveRolePassTargetID;
	}
	
	
	
	public void setOffensiveRolePassTarget(final IVector2 passTarget)
	{
		offensiveRolePassTarget = passTarget;
	}
	
	
	
	public IVector2 getOffensiveRolePassTarget()
	{
		return offensiveRolePassTarget;
	}
	
	
	
	public int getSpecialMoveCounter()
	{
		return specialMoveCounter;
	}
	
	
	
	public void setSpecialMoveCounter(final int offensiveRoleCounter)
	{
		specialMoveCounter = offensiveRoleCounter;
	}
	
	
	
	public boolean hasResponded()
	{
		return responded;
	}
	
	
	
	public void setResponded(final boolean specialMoveResponse)
	{
		responded = specialMoveResponse;
	}
	
	
	
	public List<IVector2> getDelayMoves()
	{
		return delayMoves;
	}
	
	
	
	public void setDelayMoves(final List<IVector2> delayMoves)
	{
		this.delayMoves = delayMoves;
	}
	
	
	
	public int getUnassignedStateCounter()
	{
		return unassignedStateCounter;
	}
	
	
	
	public void setUnassignedStateCounter(final int unassignedStateCounter)
	{
		this.unassignedStateCounter = unassignedStateCounter;
	}
}
