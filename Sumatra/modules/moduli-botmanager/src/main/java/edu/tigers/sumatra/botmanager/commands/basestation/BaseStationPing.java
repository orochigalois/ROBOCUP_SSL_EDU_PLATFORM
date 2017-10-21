/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.basestation;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class BaseStationPing extends ACommand
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	@SerialData(type = ESerialDataType.UINT32)
	private long	id			= 0;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	payload	= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BaseStationPing()
	{
		super(ECommand.CMD_BASE_PING);
	}
	
	
	
	public BaseStationPing(final long id, final int payloadLength)
	{
		super(ECommand.CMD_BASE_PING);
		
		this.id = id;
		setPayloadLength(payloadLength);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public long getId()
	{
		return id;
	}
	
	
	
	public void setId(final long id)
	{
		this.id = id;
	}
	
	
	
	public int getPayloadLength()
	{
		if (payload == null)
		{
			return 0;
		}
		
		return payload.length;
	}
	
	
	
	public void setPayloadLength(final int payloadLength)
	{
		payload = new byte[payloadLength];
	}
}
