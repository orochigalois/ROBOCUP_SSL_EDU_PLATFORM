/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.export.INumberListable;



public enum ETeamColor implements INumberListable
{
	
	YELLOW(0),
	
	BLUE(1),
	
	NEUTRAL(2),
	
	UNINITIALIZED(3);
	
	private final int	id;
	
	
	
	public final int getId()
	{
		return id;
	}
	
	
	private ETeamColor(final int id)
	{
		this.id = id;
	}
	
	
	
	public static ETeamColor opposite(final ETeamColor color)
	{
		switch (color)
		{
			case YELLOW:
				return BLUE;
				
			case BLUE:
				return YELLOW;
				
			case NEUTRAL:
				return NEUTRAL;
				
			default:
				return UNINITIALIZED;
		}
	}
	
	
	
	public static ETeamColor[] yellowBlueValues()
	{
		return new ETeamColor[] { YELLOW, BLUE };
	}
	
	
	
	public ETeamColor opposite()
	{
		return opposite(this);
	}
	
	
	
	public Color getColor()
	{
		switch (this)
		{
			case BLUE:
				return Color.BLUE;
			case YELLOW:
				return Color.YELLOW;
			case UNINITIALIZED:
				return Color.black;
			default:
				break;
		}
		throw new IllegalStateException();
	}
	
	
	
	public static boolean isNonNeutral(final ETeamColor color)
	{
		return (color != NEUTRAL) && (color != UNINITIALIZED);
	}
	
	
	
	public boolean isNonNeutral()
	{
		return isNonNeutral(this);
	}
	
	
	@Override
	public List<Number> getNumberList()
	{
		List<Number> numbers = new ArrayList<>();
		numbers.add(this == ETeamColor.BLUE ? 1 : this == ETeamColor.YELLOW ? 0 : -1);
		return numbers;
	}
	
	
	
	public static ETeamColor fromNumberList(final Number value)
	{
		return value.intValue() == 0 ? ETeamColor.YELLOW : value.intValue() == 1 ? ETeamColor.BLUE
				: ETeamColor.UNINITIALIZED;
	}
}
