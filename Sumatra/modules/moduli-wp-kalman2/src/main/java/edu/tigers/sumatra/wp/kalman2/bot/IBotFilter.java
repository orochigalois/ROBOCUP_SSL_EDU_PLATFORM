/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.kalman2.bot;

import edu.tigers.sumatra.math.IVector3;



public interface IBotFilter
{
	
	
	void update(IVector3 pos, long timestamp);
	
	
	
	void predict(long timestamp);
	
	
	
	void setControl(IVector3 control);
	
	
	
	IVector3 getPos();
	
	
	
	IVector3 getVel();
	
	
	
	IVector3 getAcc();
	
	
	
	IVector3 getCurAcc();
	
	
	
	IVector3 getCurVel();
	
	
	
	IVector3 getCurPos();
	
	
	
	long getCurTimestamp();
	
}