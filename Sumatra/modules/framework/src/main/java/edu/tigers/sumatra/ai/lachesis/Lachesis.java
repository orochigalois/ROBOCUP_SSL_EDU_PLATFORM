/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.lachesis;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class Lachesis
{
	// --------------------------------------------------------------------------
	// --- instance variables ---------------------------------------------------
	// --------------------------------------------------------------------------
	
	private final IRoleAssigner roleAssigner;
	
	
	// --------------------------------------------------------------------------
	// --- getInstance/constructor(s) -------------------------------------------
	// --------------------------------------------------------------------------
	
	public Lachesis()
	{
		// roleAssigner = new NewRoleAssigner();
		roleAssigner = new SimplifiedRoleAssigner();
	}
	
	
	
	public final void assignRoles(final AthenaAiFrame frame)
	{
		final BotIDMap<ITrackedBot> assignees = new BotIDMap<>(
				frame.getWorldFrame().tigerBotsAvailable);
		roleAssigner.assignRoles(assignees, frame.getPlayStrategy().getActivePlays(), frame);
	}
}
