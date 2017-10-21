/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.trajectory.TrajectoryWithTime;



public interface ITrajPathDriver extends IPathDriver
{
	
	
	void setPath(final TrajectoryWithTime<IVector2> path, IVector2 finalDestination, double targetAngle);
	
	
	
	TrajectoryWithTime<IVector2> getPath();
	
}
