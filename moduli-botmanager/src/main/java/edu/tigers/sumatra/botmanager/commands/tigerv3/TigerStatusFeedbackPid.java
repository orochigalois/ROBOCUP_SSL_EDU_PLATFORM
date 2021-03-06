/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.tigerv3;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;
import edu.tigers.sumatra.export.INumberListable;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class TigerStatusFeedbackPid extends ACommand implements INumberListable
{
	@SerialData(type = ESerialDataType.INT16)
	private final int	t		= 0;
	
	
	@SerialData(type = ESerialDataType.INT16)
	private final int	in[]	= new int[3];
	
	@SerialData(type = ESerialDataType.INT16)
	private final int	out[]	= new int[3];
	
	@SerialData(type = ESerialDataType.INT16)
	private final int	set[]	= new int[3];
	
	
	
	public TigerStatusFeedbackPid()
	{
		super(ECommand.CMD_STATUS_FEEDBACK_PID, false);
	}
	
	
	
	public IVector3 getIn()
	{
		return new Vector3(in[0] / 1000.0, in[1] / 1000.0, in[2] / 1000.0);
	}
	
	
	
	public IVector3 getOut()
	{
		return new Vector3(out[0] / 1000.0, out[1] / 1000.0, out[2] / 1000.0);
	}
	
	
	
	public IVector3 getSet()
	{
		return new Vector3(set[0] / 1000.0, set[1] / 1000.0, set[2] / 1000.0);
	}
	
	
	@Override
	public List<Number> getNumberList()
	{
		List<Number> nbrs = new ArrayList<>(12);
		nbrs.add(t / 1000.0);
		for (int i = 0; i < 3; i++)
		{
			nbrs.add(in[i] / 1000.0);
		}
		for (int i = 0; i < 3; i++)
		{
			nbrs.add(out[i] / 1000.0);
		}
		for (int i = 0; i < 3; i++)
		{
			nbrs.add(set[i] / 1000.0);
		}
		return nbrs;
	}
	
	
	
	public final double getT()
	{
		return t / 1000.0;
	}
}
