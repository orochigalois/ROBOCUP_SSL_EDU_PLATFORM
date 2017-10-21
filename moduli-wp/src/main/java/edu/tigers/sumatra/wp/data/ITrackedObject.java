/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import edu.tigers.sumatra.ids.AObjectID;
import edu.tigers.sumatra.math.IVector2;



public interface ITrackedObject
{
	
	
	IVector2 getPos();
	
	
	
	IVector2 getVel();
	
	
	
	IVector2 getAcc();
	
	
	
	AObjectID getBotId();
	
	
	
	long getTimestamp();
	
}