/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.replay;


public interface IReplayControlPanelObserver
{
	
	void onSetSpeed(double speed);
	
	
	
	void onPlayPause(boolean playing);
	
	
	
	void onChangeAbsoluteTime(long time);
	
	
	
	void onChangeRelativeTime(long relTime);
	
	
	
	void onNextFrame();
	
	
	
	void onPreviousFrame();
	
	
	
	void onSetSkipStop(final boolean enable);
	
	
	
	void onSearchKickoff(boolean enable);
	
	
	
	void onRunCurrentAi(boolean selected);
	
	
	
	void onSnapshot();
	
}
