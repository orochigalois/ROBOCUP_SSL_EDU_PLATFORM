/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer.view.field;

import edu.tigers.sumatra.ids.ETeamColor;



public enum EShapeLayerSource
{
	
	CAM,
	
	WP,
	
	AI_YELLOW,
	
	AI_BLUE,
	
	AUTOREFEREE;
	
	
	
	public static EShapeLayerSource forTeamColor(final ETeamColor teamColor)
	{
		switch (teamColor)
		{
			case BLUE:
				return EShapeLayerSource.AI_BLUE;
			case YELLOW:
				return EShapeLayerSource.AI_YELLOW;
			case UNINITIALIZED:
			default:
				throw new IllegalArgumentException();
		}
	}
}
