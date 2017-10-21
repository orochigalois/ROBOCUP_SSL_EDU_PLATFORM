/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp;

import java.util.Map;

import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;



public interface IBallWatcherObserver
{
	
	void beforeExport(Map<String, Object> jsonMapping);
	
	
	
	void onAddCustomData(ExportDataContainer container, ExtendedCamDetectionFrame frame);
	
	
	
	void postProcessing(final String fileName);
}
