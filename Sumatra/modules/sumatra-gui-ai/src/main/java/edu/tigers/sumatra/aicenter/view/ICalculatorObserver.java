/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter.view;

import edu.tigers.sumatra.ai.metis.ECalculator;



public interface ICalculatorObserver
{
	
	void onCalculatorStateChanged(ECalculator eCalc, boolean active);
}
