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

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerBootloaderRequestData extends ACommand
{
	@SerialData(type = ESerialDataType.UINT8)
	private int		procId;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	offset;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	size;
	
	
	
	protected TigerBootloaderRequestData()
	{
		super(ECommand.CMD_BOOTLOADER_REQUEST_DATA);
	}
	
	
	
	public int getProcId()
	{
		return procId;
	}
	
	
	
	public void setProcId(final int procId)
	{
		this.procId = procId;
	}
	
	
	
	public long getOffset()
	{
		return offset;
	}
	
	
	
	public void setOffset(final long offset)
	{
		this.offset = offset;
	}
	
	
	
	public long getSize()
	{
		return size;
	}
	
	
	
	public void setSize(final long size)
	{
		this.size = size;
	}
	
}
