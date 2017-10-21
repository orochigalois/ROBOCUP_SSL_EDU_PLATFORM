/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.views.ASumatraView;
import edu.tigers.sumatra.views.ESumatraViewType;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class AICenterView extends ASumatraView
{
	
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final ETeamColor	teamColor;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public AICenterView(final ETeamColor color)
	{
		super(color == ETeamColor.YELLOW ? ESumatraViewType.AI_CENTER_YELLOW : ESumatraViewType.AI_CENTER_BLUE);
		teamColor = color;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public ISumatraViewPresenter createPresenter()
	{
		return new AICenterPresenter(teamColor);
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
