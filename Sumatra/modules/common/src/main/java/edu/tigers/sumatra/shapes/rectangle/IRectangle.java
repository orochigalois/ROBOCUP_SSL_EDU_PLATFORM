/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.rectangle;

import java.util.List;
import java.util.Random;

import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.MathException;
import edu.tigers.sumatra.shapes.I2DShape;



public interface IRectangle extends I2DShape
{
	
	double yExtend();
	
	
	
	double xExtend();
	
	
	
	IVector2 topLeft();
	
	
	
	IVector2 topRight();
	
	
	
	IVector2 bottomLeft();
	
	
	
	IVector2 bottomRight();
	
	
	
	IVector2 getRandomPointInShape(Random rnd);
	
	
	
	IVector2 getMidPoint();
	
	
	
	List<IVector2> getCorners();
	
	
	
	List<Line> getEdges();
	
	
	
	IVector2 getDistantIntersectionPoint(ILine line) throws MathException;
	
	
	
	IVector2 getNearIntersectionPoint(ILine line) throws MathException;
}
