/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands.tiger;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerSystemPing extends ACommand
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	@SerialData(type = ESerialDataType.INT32)
	private int		id;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	payload;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public TigerSystemPing()
	{
		super(ECommand.CMD_SYSTEM_PING);
		
		id = 0;
	}
	
	
	
	public TigerSystemPing(final int id)
	{
		super(ECommand.CMD_SYSTEM_PING);
		
		this.id = id;
	}
	
	
	
	public TigerSystemPing(final int id, final int payloadSize)
	{
		super(ECommand.CMD_SYSTEM_PING);
		
		this.id = id;
		
		payload = new byte[payloadSize];
		for (int i = 0; i < payloadSize; i++)
		{
			byte2ByteArray(payload, i, i == 0 ? 1 : i);
			// byte2ByteArray(payload, i, (int) ((Math.random() * 254.0) + 1.0));
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public int getId()
	{
		return id;
	}
	
	
	
	public void setId(final int id)
	{
		this.id = id;
	}
}
