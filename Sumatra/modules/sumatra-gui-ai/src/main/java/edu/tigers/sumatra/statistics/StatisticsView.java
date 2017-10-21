/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.statistics;

import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.views.ASumatraView;
import edu.tigers.sumatra.views.ESumatraViewType;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class StatisticsView extends ASumatraView
{
	
	
	private final ETeamColor	teamColor;
	
	
	
	public StatisticsView(final ETeamColor color)
	{
		super(color == ETeamColor.YELLOW ? ESumatraViewType.STATISTICS_YELLOW : ESumatraViewType.STATISTICS_BLUE);
		teamColor = color;
	}
	
	
	@Override
	protected ISumatraViewPresenter createPresenter()
	{
		return new StatisticsPresenter(teamColor);
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
