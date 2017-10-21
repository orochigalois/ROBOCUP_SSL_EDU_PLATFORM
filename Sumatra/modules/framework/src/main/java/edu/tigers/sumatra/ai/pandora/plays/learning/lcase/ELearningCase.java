/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.learning.lcase;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;



public enum ELearningCase implements IInstanceableEnum
{
	
	
	BALL_ANALYZER(new InstanceableClass(BallAnalyzerLearningCase.class)),
	
	
	ROBOT_MOVEMENT(new InstanceableClass(RobotMovementLearningCase.class));
	
	
	private final InstanceableClass	clazz;
	
	
	@Override
	public InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
	
	
	
	private ELearningCase(final InstanceableClass clazz)
	{
		this.clazz = clazz;
	}
}
