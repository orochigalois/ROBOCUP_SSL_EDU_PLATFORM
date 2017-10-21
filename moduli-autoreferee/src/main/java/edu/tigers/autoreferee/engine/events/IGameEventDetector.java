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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public interface IGameEventDetector
{
	
	public enum EGameEventDetectorType
	{
		
		ATTACKER_TO_DEFENSE_DISTANCE,
		
		ATTACKER_TOUCHED_KEEPER,
		
		BALL_LEFT_FIELD_ICING,
		
		BALL_SPEEDING,
		
		BOT_COLLISION,
		
		BOT_IN_DEFENSE_AREA,
		
		BOT_NUMBER,
		
		BOT_STOP_SPEED,
		
		DOUBLE_TOUCH,
		
		DRIBBLING,
		
		DEFENDER_TO_KICK_POINT_DISTANCE,
		
		GOAL,
		
		KICK_TIMEOUT
	}
	
	
	public static class GameEventDetectorComparator implements
			Comparator<IGameEventDetector>
	{
		
		
		public static GameEventDetectorComparator	INSTANCE	= new GameEventDetectorComparator();
		
		
		@Override
		public int compare(final IGameEventDetector o1, final IGameEventDetector o2)
		{
			int prio1 = o1.getPriority();
			int prio2 = o2.getPriority();
			if (prio1 > prio2)
			{
				return -1;
			} else if (prio1 < prio2)
			{
				return 1;
			} else
			{
				return 0;
			}
		}
	}
	
	
	
	public boolean isActiveIn(EGameStateNeutral state);
	
	
	
	public int getPriority();
	
	
	
	Optional<IGameEvent> update(IAutoRefFrame frame, List<IGameEvent> events);
	
	
	
	public void reset();
	
	
}
