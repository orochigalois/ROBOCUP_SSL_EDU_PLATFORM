/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.defense.algorithms.interfaces;

import java.util.List;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;



public interface IFoeBotCalc
{
	
	public List<FoeBotData> getFoeBotData(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame);
}
