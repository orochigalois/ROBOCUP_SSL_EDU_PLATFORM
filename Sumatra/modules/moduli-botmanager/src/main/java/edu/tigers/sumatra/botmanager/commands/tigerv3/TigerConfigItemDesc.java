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

import java.io.UnsupportedEncodingException;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerConfigItemDesc extends ACommand
{
	@SerialData(type = ESerialDataType.UINT16)
	private int					configId;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int					element;
	
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]				name;
	
	
	public static final int	CONFIG_ITEM_FILE_NAME	= 0xFF;
	
	
	
	public TigerConfigItemDesc()
	{
		super(ECommand.CMD_CONFIG_ITEM_DESC, true);
	}
	
	
	
	public TigerConfigItemDesc(final int cfgId, final int element)
	{
		super(ECommand.CMD_CONFIG_ITEM_DESC, true);
		
		configId = cfgId;
		this.element = element;
	}
	
	
	
	public int getConfigId()
	{
		return configId;
	}
	
	
	
	public void setConfigId(final int configId)
	{
		this.configId = configId;
	}
	
	
	
	public int getElement()
	{
		return element;
	}
	
	
	
	public void setElement(final int element)
	{
		this.element = element;
	}
	
	
	
	public String getName()
	{
		String text;
		
		try
		{
			text = new String(name, 0, name.length, "US-ASCII");
		} catch (UnsupportedEncodingException err)
		{
			text = "Unsupported Encoding";
		}
		
		return text;
	}
}
