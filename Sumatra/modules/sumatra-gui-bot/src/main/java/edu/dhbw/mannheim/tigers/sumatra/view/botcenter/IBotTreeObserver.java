/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter;


public interface IBotTreeObserver
{
	
	void onItemSelected(BotCenterTreeNode data);
	
	
	
	void onNodeRightClicked(BotCenterTreeNode node);
	
	
	
	void onAddBot();
	
	
	
	void onRemoveBot(BotCenterTreeNode node);
}
