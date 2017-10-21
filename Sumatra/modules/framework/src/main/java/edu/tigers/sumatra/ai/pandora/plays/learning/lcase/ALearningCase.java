/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.learning.lcase;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ERole;



public abstract class ALearningCase implements ILearningCase
{
	protected List<ERole>	activeRoleTypes	= new ArrayList<ERole>();
	protected boolean			active				= false;
	
	
	@Override
	public boolean isActive(final AthenaAiFrame frame)
	{
		return active;
	}
	
	
	
	public List<ERole> getActiveRoleTypes()
	{
		return activeRoleTypes;
	}
	
	
	
	public void setActiveRoleTypes(final List<ERole> activeRoleTypes)
	{
		this.activeRoleTypes = activeRoleTypes;
	}
	
	
	
	public void setActive(final boolean active)
	{
		this.active = active;
	}
}
