/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.control;

import Jama.Matrix;



public interface IPolicyController
{
	
	Matrix getControl(final Matrix state);
	
	
	
	// default IVector3 getControl(final IVector3 destination, final TrackedBot bot)
	// {
	// IVector2 error = destination.getXYVector().subtractNew(bot.getPos()).multiply(0.001f);
	// double errorW = AngleMath.getShortestRotation(bot.getAngle(), destination.z());
	// double[] stateArr = new double[] { error.x(), error.y(), errorW, bot.getVel().x(), bot.getVel().y(),
	// bot.getaVel() };
	// Matrix state = new Matrix(stateArr, 1);
	// Matrix u = getControl(state);
	// return new Vector3(u.get(0, 0), u.get(0, 1), u.get(0, 2));
	// }
	
	
	
	int getStateDimension();
	
	
	
	double getDt();
}
