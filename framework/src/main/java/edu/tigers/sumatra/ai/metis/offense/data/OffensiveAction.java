/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.offense.data;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.metis.support.data.AdvancedPassTarget;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.trajectory.DribblePath;
import edu.tigers.sumatra.wp.data.DynamicPosition;



@Persistent(version = 2)
public class OffensiveAction
{
	
	
	public enum EOffensiveAction
	{
		
		PASS,
		
		KICKOFF,
		
		KICK_INS_BLAUE,
		
		PUSHING_KICK,
		
		CLEARING_KICK,
		
		PULL_BACK,
		
		GOAL_SHOT,
		
		@Deprecated
		MOVING_KICK,
	}
	
	private AdvancedPassTarget		passTarget							= null;
	
	private DynamicPosition			directShotAndClearingTarget	= null;
	
	private EOffensiveAction		type									= null;
	
	private OffensiveMovePosition	movePosition						= null;
	
	private List<DribblePath>		dribblePaths						= null;
	
	
	private boolean					kickInsBlauePossible				= false;
	
	private IVector2					kickInsBlaueTarget				= null;
	
	private boolean					isRoleReadyToKick					= true;
	
	
	
	public OffensiveAction()
	{
		
	}
	
	
	
	public AdvancedPassTarget getPassTarget()
	{
		return passTarget;
	}
	
	
	
	public void setPassTarget(final AdvancedPassTarget passTarget)
	{
		this.passTarget = passTarget;
	}
	
	
	
	public DynamicPosition getDirectShotAndClearingTarget()
	{
		return directShotAndClearingTarget;
	}
	
	
	
	public void setDirectShotAndClearingTarget(final DynamicPosition directShotTarget)
	{
		directShotAndClearingTarget = directShotTarget;
	}
	
	
	
	public EOffensiveAction getType()
	{
		return type;
	}
	
	
	
	public void setType(final EOffensiveAction type)
	{
		this.type = type;
	}
	
	
	
	public OffensiveMovePosition getMovePosition()
	{
		return movePosition;
	}
	
	
	
	public void setMovePosition(final OffensiveMovePosition movePosition)
	{
		this.movePosition = movePosition;
	}
	
	
	
	public List<DribblePath> getDribblePaths()
	{
		return dribblePaths;
	}
	
	
	
	public void setDribblePaths(final List<DribblePath> dribblePaths)
	{
		this.dribblePaths = dribblePaths;
	}
	
	
	
	public boolean isKickInsBlauePossible()
	{
		return kickInsBlauePossible;
	}
	
	
	
	public void setKickInsBlauePossible(final boolean kickInsBlauePossible)
	{
		this.kickInsBlauePossible = kickInsBlauePossible;
	}
	
	
	
	public IVector2 getKickInsBlaueTarget()
	{
		return kickInsBlaueTarget;
	}
	
	
	
	public void setKickInsBlaueTarget(final IVector2 kickInsBlaueTarget)
	{
		this.kickInsBlaueTarget = kickInsBlaueTarget;
	}
	
	
	
	public boolean isRoleReadyToKick()
	{
		return isRoleReadyToKick;
	}
	
	
	
	public void setRoleReadyToKick(final boolean isRoleReadyToKick)
	{
		this.isRoleReadyToKick = isRoleReadyToKick;
	}
	
	
}
