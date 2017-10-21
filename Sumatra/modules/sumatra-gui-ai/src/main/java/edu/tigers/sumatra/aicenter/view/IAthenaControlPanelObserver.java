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

import java.util.Map;

import edu.tigers.sumatra.ai.lachesis.RoleFinderInfo;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;



public interface IAthenaControlPanelObserver
{
	
	void onNewRoleFinderInfos(Map<EPlay, RoleFinderInfo> infos);
	
	
	
	void onNewRoleFinderOverrides(Map<EPlay, Boolean> overrides);
}
