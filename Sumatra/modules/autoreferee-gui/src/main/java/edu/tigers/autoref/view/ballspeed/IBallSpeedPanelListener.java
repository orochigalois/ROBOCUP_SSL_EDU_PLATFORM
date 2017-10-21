/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.ballspeed;


public interface IBallSpeedPanelListener
{
	
	public void pauseButtonPressed();
	
	
	
	public void resumeButtonPressed();
	
	
	
	public void stopChartValueChanged(boolean value);
	
	
	
	public void timeRangeSliderValueChanged(int value);
}
