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
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigFileStructure.EElementType;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerConfigRead extends ACommand
{
	@SerialData(type = ESerialDataType.UINT16)
	private int				configId;
	
	@SerialData(type = ESerialDataType.TAIL)
	private final byte[]	data	= null;
	
	
	
	public TigerConfigRead()
	{
		super(ECommand.CMD_CONFIG_READ, true);
	}
	
	
	
	public TigerConfigRead(final int cfgId)
	{
		super(ECommand.CMD_CONFIG_READ, true);
		
		configId = cfgId;
	}
	
	
	
	public int getConfigId()
	{
		return configId;
	}
	
	
	
	public void setConfigId(final int configId)
	{
		this.configId = configId;
	}
	
	
	
	public List<String> getData(final TigerConfigFileStructure structure)
	{
		List<String> values = new ArrayList<String>();
		List<EElementType> elements = structure.getElements();
		
		if (data == null)
		{
			return values;
		}
		
		int offset = 0;
		
		for (EElementType element : elements)
		{
			if ((offset + element.getSize()) > data.length)
			{
				return values;
			}
			
			String val = null;
			switch (element)
			{
				case UINT8:
					val = new String("" + ACommand.byteArray2UByte(data, offset));
					break;
				case INT8:
					val = new String("" + (int) data[offset]);
					break;
				case UINT16:
					val = new String("" + ACommand.byteArray2UShort(data, offset));
					break;
				case INT16:
					val = new String("" + ACommand.byteArray2Short(data, offset));
					break;
				case UINT32:
					val = new String("" + ACommand.byteArray2UInt(data, offset));
					break;
				case INT32:
					val = new String("" + ACommand.byteArray2Int(data, offset));
					break;
				case FLOAT32:
					val = new String("" + ACommand.byteArray2Float(data, offset));
					break;
				case UNKNOWN:
					break;
				default:
					break;
			}
			
			if (val != null)
			{
				values.add(val);
			}
			
			offset += element.getSize();
		}
		
		return values;
	}
}
