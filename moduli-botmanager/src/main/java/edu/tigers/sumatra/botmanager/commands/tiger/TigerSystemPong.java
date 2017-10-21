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

import java.util.Arrays;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerSystemPong extends ACommand
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	// ping identification (for roundtrip measurements)
	@SerialData(type = ESerialDataType.INT32)
	private int		id;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	payload;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerSystemPong()
	{
		super(ECommand.CMD_SYSTEM_PONG);
		
		id = 0;
	}
	
	
	
	public TigerSystemPong(final int id)
	{
		super(ECommand.CMD_SYSTEM_PONG);
		
		this.id = id;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public boolean payloadValid()
	{
		if (payload == null)
		{
			return true;
		}
		byte[] sPayload = new byte[payload.length];
		for (int i = 0; i < payload.length; i++)
		{
			byte2ByteArray(sPayload, i, i == 0 ? 1 : i);
		}
		for (int i = 0; i < payload.length; i++)
		{
			if (payload[i] != sPayload[i])
			{
				return false;
			}
		}
		return true;
	}
	
	
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
	
	
	
	public final byte[] getPayload()
	{
		return Arrays.copyOf(payload, payload.length);
	}
}
