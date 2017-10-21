/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.Color;
import java.awt.Graphics2D;



public interface IColorPicker
{
	
	Color applyColor(Graphics2D g, double relValue);
	
	
	
	Color getColor(double relValue);
}
