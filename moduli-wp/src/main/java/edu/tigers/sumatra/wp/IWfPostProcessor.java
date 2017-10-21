/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp;

import edu.tigers.sumatra.wp.data.MotionContext;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;



public interface IWfPostProcessor
{
	
	SimpleWorldFrame process(final SimpleWorldFrame swf);
	
	
	
	void processMotionContext(MotionContext context, long timestamp);
}
