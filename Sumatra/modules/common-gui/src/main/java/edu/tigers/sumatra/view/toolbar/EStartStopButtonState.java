/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.view.toolbar;

import javax.swing.ImageIcon;

import edu.tigers.sumatra.util.ImageScaler;



public enum EStartStopButtonState
{
	
	START("/start.png"),
	
	STOP("/stop.png"),
	
	LOADING("/LoadingTrans.gif");
	
	private final ImageIcon	icon;
	
	
	private EStartStopButtonState(final String path)
	{
		icon = ImageScaler.scaleDefaultButtonImageIcon(path);
	}
	
	
	
	public final ImageIcon getIcon()
	{
		return icon;
	}
}
