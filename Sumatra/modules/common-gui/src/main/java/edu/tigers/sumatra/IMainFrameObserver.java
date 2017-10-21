/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra;

import javax.swing.UIManager.LookAndFeelInfo;



public interface IMainFrameObserver
{
	
	
	void onSaveLayout();
	
	
	
	void onDeleteLayout();
	
	
	
	void onExit();
	
	
	
	default void onAbout()
	{
	}
	
	
	
	void onLoadLayout(String filename);
	
	
	
	void onRefreshLayoutItems();
	
	
	
	default void onSelectLookAndFeel(final LookAndFeelInfo info)
	{
	}
}
