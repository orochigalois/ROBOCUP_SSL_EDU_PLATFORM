/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.log;

import edu.tigers.sumatra.Referee.SSL_Referee.Stage;
import edu.tigers.sumatra.referee.RefereeMsg;



public class GameTime
{
	private final static GameTime	empty	= new GameTime(Stage.NORMAL_FIRST_HALF_PRE, 0);
	
	private final Stage				stage;
	private final long				micros;
	
	
	private GameTime(final Stage stage, final long micros)
	{
		this.stage = stage;
		this.micros = micros;
	}
	
	
	
	public static GameTime empty()
	{
		return empty;
	}
	
	
	
	public static GameTime of(final Stage stage, final long micros)
	{
		return new GameTime(stage, micros);
	}
	
	
	
	public static GameTime of(final RefereeMsg refMsg)
	{
		return new GameTime(refMsg.getStage(), refMsg.getStageTimeLeft());
	}
	
	
	
	public GameTime subtract(final long micros)
	{
		return new GameTime(stage, this.micros - micros);
	}
	
	
	
	public Stage getStage()
	{
		return stage;
	}
	
	
	
	public long getMicrosLeft()
	{
		return micros;
	}
}
