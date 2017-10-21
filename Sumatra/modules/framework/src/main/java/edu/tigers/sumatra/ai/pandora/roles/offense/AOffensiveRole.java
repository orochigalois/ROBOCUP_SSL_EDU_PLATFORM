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

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.metis.offense.OffensiveConstants;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;



public abstract class AOffensiveRole extends ARole
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	protected static final Logger log = Logger.getLogger(AOffensiveRole.class.getName());
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public AOffensiveRole()
	{
		super(ERole.OFFENSIVE);
	}
	
	
	
	@Override
	protected void beforeUpdate()
	{
		changeStateIfNecessary();
	}
	
	
	// ----------------------------------------------------------------------- //
	// -------------------- functions ---------------------------------------- //
	// ----------------------------------------------------------------------- //
	
	
	public void changeStateIfNecessary()
	{
		if (getAiFrame().getTacticalField() != null)
		{
			if (getAiFrame().getTacticalField().getOffensiveStrategy() != null)
			{
				if (getAiFrame().getTacticalField().getOffensiveStrategy().getCurrentOffensivePlayConfiguration() != null)
				{
					if (getAiFrame().getTacticalField().getOffensiveStrategy()
							.getCurrentOffensivePlayConfiguration().containsKey(getBotID()))
					{
						if (getCurrentState() != getAiFrame().getTacticalField().getOffensiveStrategy()
								.getCurrentOffensivePlayConfiguration().get(getBotID()))
						{
							triggerEvent(getAiFrame().getTacticalField().getOffensiveStrategy()
									.getCurrentOffensivePlayConfiguration().get(getBotID()));
						}
					}
				}
			}
		}
	}
	
	
	protected void printDebugInformation(final String text)
	{
		if (OffensiveConstants.isShowDebugInformations())
		{
			log.debug(text);
		}
	}
}
