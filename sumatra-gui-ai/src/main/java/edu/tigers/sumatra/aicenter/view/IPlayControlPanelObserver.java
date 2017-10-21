/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter.view;

import edu.tigers.sumatra.ai.pandora.plays.APlay;



public interface IPlayControlPanelObserver
{
	
	void addPlay(APlay play);
	
	
	
	void removePlay(APlay play);
	
	
	
	void addRoles2Play(APlay play, int numRoles);
	
	
	
	void removeRolesFromPlay(APlay play, int numRoles);
}
