/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.ball;

import java.util.List;

import edu.tigers.sumatra.bot.EBotType;



public interface IBallAnalyserPanelObserver
{
	
	void onSave(String filename);
	
	
	
	void onRecord(boolean record, boolean stopAutomatically);
	
	
	
	void onDelete(List<String> selectedFiles);
	
	
	
	void onPlot(List<String> selectedFiles);
	
	
	
	void onCreateBallModel(List<String> selectedFiles);
	
	
	
	void onCreateKickModel(List<String> selectedFiles, EBotType eBotType);
	
	
	
	void onNewSelectedFile(List<String> selectedFiles);
	
	
	
	void onBallCorrector(List<String> selectedFiles);
	
	
	
	void onCopy(List<String> selectedFiles);
	
	
	
	void onKalman(List<String> selectedFiles);
	
}
