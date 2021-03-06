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

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.Geometry;



public class PositionData
{
	private int					resolutionX;
	private int					resolutionY;
	
	private List<Integer>	detectedPositions	= new ArrayList<Integer>();
	
	
	
	public PositionData(final int resolutionX, final int resolutionY)
	{
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		
		for (int i = 0; i < (resolutionX * resolutionY); i++)
		{
			detectedPositions.add(0);
		}
	}
	
	
	
	public int getResolutionX()
	{
		return resolutionX;
	}
	
	
	
	public int getResolutionY()
	{
		return resolutionY;
	}
	
	
	
	public void signalPositionAt(final IVector2 position)
	{
		int indexToIncrease = getIndexForPosition(position);
		
		detectedPositions.set(indexToIncrease, detectedPositions.get(indexToIncrease) + 1);
	}
	
	
	
	public int getEntryForPosition(final IVector2 position)
	{
		return detectedPositions.get(getIndexForPosition(position));
	}
	
	
	private int getIndexForPosition(final IVector2 position)
	{
		double stepSizeX = Geometry.getFieldWidth() / resolutionX;
		int indexX = (int) ((position.get(0) + (Geometry.getFieldLength() / 2)) / stepSizeX);
		
		double stepSizeY = Geometry.getFieldLength() / resolutionY;
		int indexY = (int) ((position.get(1) + (Geometry.getFieldWidth() / 2)) / stepSizeY);
		
		return (indexX * resolutionY) + indexY;
	}
}
