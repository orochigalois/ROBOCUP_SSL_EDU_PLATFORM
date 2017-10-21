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

import java.awt.Graphics2D;

import edu.tigers.sumatra.math.IVector2;



public interface IDrawableTool
{
	
	
	IVector2 transformToGlobalCoordinates(IVector2 guiPosition);
	
	
	
	IVector2 transformToGlobalCoordinates(IVector2 globalPosition, boolean invert);
	
	
	
	IVector2 transformToGuiCoordinates(IVector2 globalPosition, boolean invert);
	
	
	
	IVector2 transformToGuiCoordinates(IVector2 globalPosition);
	
	
	
	int scaleXLength(double length);
	
	
	
	int scaleYLength(double length);
	
	
	
	int getFieldTotalWidth();
	
	
	
	int getFieldTotalHeight();
	
	
	
	void turnField(EFieldTurn fieldTurn, double angle, Graphics2D g2);
	
	
	
	EFieldTurn getFieldTurn();
	
	
	
	int getFieldHeight();
	
	
	
	int getFieldWidth();
	
	
	
	double getScaleFactor();
	
	
	
	double getFieldOriginY();
	
	
	
	double getFieldOriginX();
	
	
	
	int getWidth();
	
	
	
	int getHeight();
	
	
	
	int getFieldMargin();
}
