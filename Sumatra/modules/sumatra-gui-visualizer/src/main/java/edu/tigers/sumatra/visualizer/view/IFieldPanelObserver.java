/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer.view;

import java.awt.event.MouseEvent;

import edu.tigers.sumatra.math.IVector2;



public interface IFieldPanelObserver
{
	
	void onFieldClick(IVector2 pos, MouseEvent e);
	
	
	
	default void onMouseMoved(final IVector2 pos, final MouseEvent e)
	{
	}
}
