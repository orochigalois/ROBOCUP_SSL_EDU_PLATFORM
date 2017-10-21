/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.triangle;

import java.util.List;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.shapes.I2DShape;



public interface ITriangle extends I2DShape
{
	
	
	List<IVector2> getCorners();
}
