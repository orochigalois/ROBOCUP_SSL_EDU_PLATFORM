/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref;

import java.awt.EventQueue;

import edu.tigers.autoref.gui.AutoRefMainPresenter;



public class AutoReferee
{
	
	
	public static void main(final String[] args)
	{
		EventQueue.invokeLater(() -> new AutoRefMainPresenter());
	}
}
