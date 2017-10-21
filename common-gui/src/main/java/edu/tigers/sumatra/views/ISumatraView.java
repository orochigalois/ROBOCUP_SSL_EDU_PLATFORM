/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.views;

import java.util.Collections;
import java.util.List;

import javax.swing.JMenu;



public interface ISumatraView
{
	
	default List<JMenu> getCustomMenus()
	{
		return Collections.emptyList();
	}
	
	
	
	default void onShown()
	{
		
	}
	
	
	
	default void onHidden()
	{
		
	}
	
	
	
	default void onFocused()
	{
		
	}
	
	
	
	default void onFocusLost()
	{
		
	}
}
