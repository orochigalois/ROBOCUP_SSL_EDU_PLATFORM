/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.test;

import edu.tigers.sumatra.ai.data.OffensiveStrategy.EOffensiveStrategy;
import edu.tigers.sumatra.ai.pandora.roles.offense.OffensiveRole;
import edu.tigers.sumatra.ai.pandora.roles.offense.states.OffensiveRoleRedirectCatchSpecialMovementState;



public class RedirectOffensiveTestRole extends OffensiveRole
{
	
	private boolean aiEnabled = true;
	
	
	@Override
	protected void beforeUpdate()
	{
		changeStateIfNecessary();
	}
	
	
	
	public RedirectOffensiveTestRole()
	{
		setInitialState(new OffensiveRoleRedirectCatchSpecialMovementState(this));
	}
	
	
	@Override
	public void changeStateIfNecessary()
	{
		if (aiEnabled)
		{
			super.changeStateIfNecessary();
		} else if (getCurrentState() != EOffensiveStrategy.REDIRECT_CATCH_SPECIAL_MOVE)
		{
			triggerEvent(EOffensiveStrategy.REDIRECT_CATCH_SPECIAL_MOVE);
		}
	}
	
	
	@Override
	public void beforeFirstUpdate()
	{
	}
	
	
	
	public void setAiEnabled(final boolean enabled)
	{
		aiEnabled = enabled;
	}
	
	
}
