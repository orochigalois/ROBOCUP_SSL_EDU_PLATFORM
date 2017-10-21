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



public class TigerBootloaderCrc extends ACommand
{
	@SerialData(type = ESerialDataType.UINT8)
	private int		procId;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	startAddr;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	endAddr;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	crc;
	
	
	
	public TigerBootloaderCrc()
	{
		super(ECommand.CMD_BOOTLOADER_CRC);
	}
	
	
	
	public int getProcId()
	{
		return procId;
	}
	
	
	
	public void setProcId(final int procId)
	{
		this.procId = procId;
	}
	
	
	
	public long getStartAddr()
	{
		return startAddr;
	}
	
	
	
	public void setStartAddr(final long startAddr)
	{
		this.startAddr = startAddr;
	}
	
	
	
	public long getEndAddr()
	{
		return endAddr;
	}
	
	
	
	public void setEndAddr(final long endAddr)
	{
		this.endAddr = endAddr;
	}
	
	
	
	public long getCrc()
	{
		return crc;
	}
	
	
	
	public void setCrc(final long crc)
	{
		this.crc = crc;
	}
	
}
