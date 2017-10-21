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

import java.util.List;



public interface IMainFrame
{
	
	void addObserver(IMainFrameObserver o);
	
	
	
	void removeObserver(IMainFrameObserver o);
	
	
	
	void loadLayout(final String filename);
	
	
	
	void saveLayout(String filename);
	
	
	
	void setMenuLayoutItems(List<String> names);
	
	
	
	void selectLayoutItem(String name);
	
}
