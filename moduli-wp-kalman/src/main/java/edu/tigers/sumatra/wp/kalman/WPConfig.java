/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman;



public final class WPConfig
{
	
	public static final double	FILTER_CONVERT_NS_TO_INTERNAL_TIME				= 1e-9;
	
	public static final double	FILTER_CONVERT_MM_TO_INTERNAL_UNIT				= 1e0;
	
	
	public static final double	FILTER_CONVERT_M_TO_INTERNAL_UNIT				= 1e3 * FILTER_CONVERT_MM_TO_INTERNAL_UNIT;
	
	public static final double	FILTER_CONVERT_MperS_TO_INTERNAL_V				= (1e-6 * FILTER_CONVERT_MM_TO_INTERNAL_UNIT)
																											/ FILTER_CONVERT_NS_TO_INTERNAL_TIME;
	
	public static final double	FILTER_CONVERT_MperSS_TO_INTERNAL_A				= (1e-15 * FILTER_CONVERT_MM_TO_INTERNAL_UNIT)
																											/ (FILTER_CONVERT_NS_TO_INTERNAL_TIME * FILTER_CONVERT_NS_TO_INTERNAL_TIME);
	
	public static final double	FILTER_CONVERT_RadPerS_TO_RadPerInternal		= 1e-9 / FILTER_CONVERT_NS_TO_INTERNAL_TIME;
	
	public static final double	FILTER_CONVERT_RadPerSS_TO_RadPerInternalSQ	= 1e-18 / (FILTER_CONVERT_NS_TO_INTERNAL_TIME * FILTER_CONVERT_NS_TO_INTERNAL_TIME);
	
	
	
	public static final int		ADD_MIN_FRAMES_BOTS									= 10;
	
	
	public static final double	ADD_MAX_TIME_BOT										= (0.50);
	
	public static final double	ADD_MAX_TIME_BALL										= (0.10);
	
	
	public static final double	REM_MAX_TIME_BOT										= (1.50);
	
	public static final double	REM_MAX_TIME_BALL										= (0.10);
	
	
	public static final double	MIN_CAMFRAME_DELAY_TIME								= 0.0001;
	
	
	public static final int		YELLOW_ID_OFFSET										= 100;
	
	public static final int		BLUE_ID_OFFSET											= 200;
	
	
	
	
	
	public static final int		BALL_MODULE												= 0;
	
	public static final int		BLUE_MODULE												= 0;
	
	public static final int		YELLOW_MODULE											= 0;
	
	
	private WPConfig()
	{
		
	}
}
