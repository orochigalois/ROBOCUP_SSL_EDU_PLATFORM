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

import java.util.List;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;



public interface ILearningCase
{
	
	public boolean isFinished(AthenaAiFrame frame);
	
	
	
	public void update(List<ARole> roles, AthenaAiFrame aiFrame);
	
	
	
	public boolean isReady(AthenaAiFrame frame, List<ARole> roles);
	
	
	
	public boolean isActive(AthenaAiFrame frame);
	
	
	
	public String getReadyCriteria();
	
}
