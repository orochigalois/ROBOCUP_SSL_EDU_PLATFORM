/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.ellipse;

import java.util.List;

import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.I2DShape;



public interface IEllipse extends I2DShape
{
	
	IVector2 getApex(EApexType apexType);
	
	
	
	IVector2 getCenter();
	
	
	
	double getRadiusX();
	
	
	
	double getRadiusY();
	
	
	
	double getTurnAngle();
	
	
	
	IVector2 getFocusPositive();
	
	
	
	IVector2 getFocusNegative();
	
	
	
	IVector2 getFocusFromCenter();
	
	
	
	IVector2 stepOnCurve(IVector2 start, double step);
	
	
	
	List<IVector2> getIntersectingPoints(IVector2 p1, IVector2 p2);
	
	
	
	double getCircumference();
	
	
	
	double getDiameterMax();
}
