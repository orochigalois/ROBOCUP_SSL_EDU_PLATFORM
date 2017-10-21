/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.autoreferee.cases;

import java.util.List;

import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.autoreferee.AutoReferee;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;



public interface IRefereeCase
{
	
	List<RefereeCaseMsg> process(MetisAiFrame frame);
	
	
	
	void reset();
}
