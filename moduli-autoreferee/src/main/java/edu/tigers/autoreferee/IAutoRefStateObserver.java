/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee;

import edu.tigers.autoreferee.AutoRefModule.AutoRefState;



public interface IAutoRefStateObserver
{
	
	void onAutoRefStateChanged(AutoRefState state);
	
	
	
	void onNewAutoRefFrame(IAutoRefFrame frame);
}
