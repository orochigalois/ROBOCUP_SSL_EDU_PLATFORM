/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events;


public enum EGameEvent
{
	
	
	BALL_LEFT_FIELD(EEventCategory.VIOLATION),
	
	BALL_SPEEDING(EEventCategory.VIOLATION),
	
	DOUBLE_TOUCH(EEventCategory.VIOLATION),
	
	ATTACKER_TO_DEFENCE_AREA(EEventCategory.VIOLATION),
	
	BALL_HOLDING(EEventCategory.VIOLATION),
	
	BOT_COLLISION(EEventCategory.VIOLATION),
	
	INDIRECT_GOAL(EEventCategory.VIOLATION),
	
	ICING(EEventCategory.VIOLATION),
	
	BALL_DRIBBLING(EEventCategory.VIOLATION),
	
	BOT_COUNT(EEventCategory.VIOLATION),
	
	BOT_STOP_SPEED(EEventCategory.VIOLATION),
	
	ATTACKER_IN_DEFENSE_AREA(EEventCategory.VIOLATION),
	
	DEFENDER_TO_KICK_POINT_DISTANCE(EEventCategory.VIOLATION),
	
	KICK_TIMEOUT(EEventCategory.VIOLATION),
	
	MULTIPLE_DEFENDER(EEventCategory.VIOLATION),
	
	MULTIPLE_DEFENDER_PARTIALLY(EEventCategory.VIOLATION),
	
	ATTACKER_TOUCH_KEEPER(EEventCategory.VIOLATION),
	
	GOAL(EEventCategory.GENERAL);
	
	private final EEventCategory	category;
	
	
	private EGameEvent(final EEventCategory category)
	{
		this.category = category;
	}
	
	
	
	public EEventCategory getCategory()
	{
		return category;
	}
}
