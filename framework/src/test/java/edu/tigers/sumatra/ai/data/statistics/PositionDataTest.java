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

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.Geometry;



public class PositionDataTest
{
	
	
	@Test
	public void testInitializationWithResolution()
	{
		int expectedResolutionX = 100;
		int expectedResolutionY = 100;
		PositionData positionData = new PositionData(expectedResolutionX, expectedResolutionY);
		
		int actualResolutionX = positionData.getResolutionX();
		Assert.assertEquals(expectedResolutionX, actualResolutionX);
		
		int actualResolutionY = positionData.getResolutionY();
		Assert.assertEquals(expectedResolutionY, actualResolutionY);
	}
	
	
	
	@Test
	public void shouldGetEntryFor1DetectionAt99_99ForOurRightCorner()
	{
		int expectedPositions = 1;
		
		int usedResolution = 100;
		
		IVector2 topLeftCorner = Geometry.getCornerRightOur();
		
		PositionData positionData = new PositionData(usedResolution, usedResolution);
		positionData.signalPositionAt(topLeftCorner);
		
		int actualPositions = positionData.getEntryForPosition(topLeftCorner);
		
		Assert.assertEquals(expectedPositions, actualPositions);
	}
}
