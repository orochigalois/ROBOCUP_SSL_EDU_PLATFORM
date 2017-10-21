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



public class TigerConfigFileStructure extends ACommand
{
	
	public static enum EElementType
	{
		
		UINT8(0, 1, 0, 255),
		
		INT8(1, 1, -128, 127),
		
		UINT16(2, 2, 0, 65535),
		
		INT16(3, 2, -32768, 32767),
		
		UINT32(4, 4, 0, 4294967295L),
		
		INT32(5, 4, -2147483648L, 2147483647L),
		
		FLOAT32(6, 4, 0, 0),
		
		UNKNOWN(7, 0, 0, 0);
		
		private final int		typeId;
		private final int		size;
		private final long	min;
		private final long	max;
		
		
		private EElementType(final int typeId, final int size, final long min, final long max)
		{
			this.typeId = typeId;
			this.size = size;
			this.min = min;
			this.max = max;
		}
		
		
		
		public int getTypeId()
		{
			return typeId;
		}
		
		
		
		public int getSize()
		{
			return size;
		}
		
		
		
		public long getMin()
		{
			return min;
		}
		
		
		
		public long getMax()
		{
			return max;
		}
		
		
		
		public static EElementType getElementType(final int id)
		{
			for (EElementType s : values())
			{
				if (s.getTypeId() == id)
				{
					return s;
				}
			}
			
			return UNKNOWN;
		}
	}
	
	@SerialData(type = ESerialDataType.UINT16)
	private int		configId;
	
	@SerialData(type = ESerialDataType.UINT16)
	private int		version;
	
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	structure;
	
	
	
	public TigerConfigFileStructure()
	{
		super(ECommand.CMD_CONFIG_FILE_STRUCTURE, true);
	}
	
	
	
	public int getConfigId()
	{
		return configId;
	}
	
	
	
	public int getVersion()
	{
		return version;
	}
	
	
	
	public List<EElementType> getElements()
	{
		List<EElementType> elements = new ArrayList<EElementType>();
		
		for (byte item : structure)
		{
			EElementType eType = EElementType.getElementType(item);
			elements.add(eType);
		}
		
		return elements;
	}
}
