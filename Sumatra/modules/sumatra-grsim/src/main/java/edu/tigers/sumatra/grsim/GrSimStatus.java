/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.grsim;


public class GrSimStatus
{
	private final int			id;
	private final boolean	barrierInterrupted;
	private final boolean	kicked;
									
									
	
	public GrSimStatus()
	{
		id = -1;
		barrierInterrupted = false;
		kicked = false;
	}
	
	
	
	public GrSimStatus(final byte[] data)
	{
		char status = (char) data[0];
		id = status & 7;
		barrierInterrupted = (status & 8) == 8;
		kicked = (status & 16) == 16;
	}
	
	
	
	public final int getId()
	{
		return id;
	}
	
	
	
	public final boolean isBarrierInterrupted()
	{
		return barrierInterrupted;
	}
	
	
	
	public final boolean isKicked()
	{
		return kicked;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("GrSimStatus [id=");
		builder.append(id);
		builder.append(", barrierInterrupted=");
		builder.append(barrierInterrupted);
		builder.append(", kicked=");
		builder.append(kicked);
		builder.append("]");
		return builder.toString();
	}
	
}
