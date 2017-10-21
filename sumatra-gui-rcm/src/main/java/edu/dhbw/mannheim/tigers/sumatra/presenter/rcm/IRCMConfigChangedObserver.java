/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.presenter.rcm;

import java.io.File;

import edu.tigers.sumatra.rcm.RcmActionMap.ERcmControllerConfig;
import edu.tigers.sumatra.rcm.RcmActionMapping;



public interface IRCMConfigChangedObserver
{
	
	void onConfigChanged(ERcmControllerConfig configType, double value);
	
	
	
	void onActionMappingCreated(RcmActionMapping mapping);
	
	
	
	void onActionMappingChanged(RcmActionMapping mapping);
	
	
	
	void onActionMappingRemoved(RcmActionMapping mapping);
	
	
	
	void onSaveConfig();
	
	
	
	void onSaveConfigAs(File file);
	
	
	
	void onLoadConfig(File file);
	
	
	
	void onLoadDefaultConfig();
	
	
	
	void onSelectAssignment(RcmActionMapping actionMapping);
	
	
	
	void onSelectionAssistant();
	
	
	
	void onUnassignBot();
}
