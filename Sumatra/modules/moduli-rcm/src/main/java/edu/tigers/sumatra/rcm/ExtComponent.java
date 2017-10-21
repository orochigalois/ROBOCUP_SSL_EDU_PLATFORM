/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.rcm;

import edu.tigers.sumatra.rcm.RcmAction.EActionType;
import net.java.games.input.Component;



public class ExtComponent implements Component
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final Component	baseComponent;
	private final RcmAction	mappedAction;
	private ExtComponent		dependentComp;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public ExtComponent(final Component baseComponent, final RcmAction mappedAction)
	{
		this.baseComponent = baseComponent;
		this.mappedAction = mappedAction;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public boolean isContinuesAction()
	{
		// can potentially be configured in more detail later
		if (mappedAction.getActionType() == EActionType.SIMPLE)
		{
			EControllerAction cAction = (EControllerAction) mappedAction.getActionEnum();
			return cAction.isContinuous();
			
		}
		return false;
	}
	
	
	@Override
	public String toString()
	{
		return baseComponent.getIdentifier().getName() + " - " + mappedAction;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	@Override
	public Identifier getIdentifier()
	{
		return baseComponent.getIdentifier();
	}
	
	
	@Override
	public boolean isRelative()
	{
		return baseComponent.isRelative();
	}
	
	
	@Override
	public boolean isAnalog()
	{
		return baseComponent.isAnalog();
	}
	
	
	@Override
	public float getDeadZone()
	{
		return baseComponent.getDeadZone();
	}
	
	
	@Override
	public float getPollData()
	{
		if (dependentComp != null)
		{
			return baseComponent.getPollData() * dependentComp.getPollData();
		}
		return baseComponent.getPollData();
	}
	
	
	@Override
	public String getName()
	{
		return baseComponent.getName();
	}
	
	
	
	public Component getBaseComponent()
	{
		return baseComponent;
	}
	
	
	
	public RcmAction getMappedAction()
	{
		return mappedAction;
	}
	
	
	
	public final ExtComponent getDependentComp()
	{
		return dependentComp;
	}
	
	
	
	public final void setDependentComp(final ExtComponent dependentComp)
	{
		this.dependentComp = dependentComp;
	}
}
