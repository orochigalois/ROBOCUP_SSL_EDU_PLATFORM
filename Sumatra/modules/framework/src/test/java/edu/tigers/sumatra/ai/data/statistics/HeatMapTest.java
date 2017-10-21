/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.statistics;

import junit.framework.Assert;

import org.junit.Test;



public class HeatMapTest
{
	
	
	@Test
	public void shouldBeInitializedWithProperPositionData()
	{
		int resolutions = 100;
		PositionData expectedPositionData = new PositionData(resolutions, resolutions);
		
		HeatMap heatMap = new HeatMap(expectedPositionData);
		
		PositionData actualPositionData = heatMap.getPositionData();
		
		Assert.assertEquals(expectedPositionData, actualPositionData);
	}
}
