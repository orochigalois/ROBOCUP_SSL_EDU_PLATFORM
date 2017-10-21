/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.tigerv2;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerBootloaderData extends ACommand
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	@SerialData(type = ESerialDataType.UINT8)
	private int		procId	= 0;
	@SerialData(type = ESerialDataType.UINT32)
	private long	offset	= 0;
	
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	payload;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerBootloaderData()
	{
		super(ECommand.CMD_BOOTLOADER_DATA);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public long getOffset()
	{
		return offset;
	}
	
	
	
	public void setOffset(final long offset)
	{
		this.offset = offset;
	}
	
	
	
	public byte[] getPayload()
	{
		return payload;
	}
	
	
	
	public void setPayload(final byte[] payloadIn)
	{
		payload = new byte[payloadIn.length];
		
		System.arraycopy(payloadIn, 0, payload, 0, payloadIn.length);
	}
	
	
	
	public int getProcId()
	{
		return procId;
	}
	
	
	
	public void setProcId(final int procId)
	{
		this.procId = procId;
	}
}