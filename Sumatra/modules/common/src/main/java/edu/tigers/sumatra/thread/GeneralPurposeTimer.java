/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.thread;

import java.util.Timer;



public final class GeneralPurposeTimer extends Timer
{
	private static final GeneralPurposeTimer	instance	= new GeneralPurposeTimer();
	
	
	private GeneralPurposeTimer()
	{
		super("GeneralPurposeTimer");
	}
	
	
	
	public static GeneralPurposeTimer getInstance()
	{
		return instance;
	}
}
