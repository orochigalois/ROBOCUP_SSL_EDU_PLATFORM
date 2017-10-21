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

import java.io.UnsupportedEncodingException;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerSystemConsolePrint extends ACommand
{
	
	public static enum ConsolePrintSource
	{
		
		UNKNOWN(0),
		
		MAIN(1),
		
		MEDIA(2),
		
		LEFT(4),
		
		RIGHT(8),
		
		KD(0x10);
		
		private final int	id;
		
		
		private ConsolePrintSource(final int id)
		{
			this.id = id;
		}
		
		
		
		public int getId()
		{
			return id;
		}
		
		
		
		public static ConsolePrintSource getSourceConstant(final int id)
		{
			for (ConsolePrintSource s : values())
			{
				if (s.getId() == id)
				{
					return s;
				}
			}
			
			return UNKNOWN;
		}
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	@SerialData(type = ESerialDataType.UINT8)
	private int		source;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	textData;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerSystemConsolePrint()
	{
		super(ECommand.CMD_SYSTEM_CONSOLE_PRINT);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ConsolePrintSource getSource()
	{
		return ConsolePrintSource.getSourceConstant(source);
	}
	
	
	
	public void setSource(final ConsolePrintSource source)
	{
		this.source = source.getId();
	}
	
	
	
	public String getText()
	{
		String text;
		
		try
		{
			text = new String(textData, 0, textData.length, "US-ASCII");
		} catch (UnsupportedEncodingException err)
		{
			text = "Unsupported Encoding";
		}
		
		return text;
	}
	
	
	
	public void setText(final String text)
	{
		try
		{
			textData = text.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException err)
		{
			textData = new byte[1];
			textData[0] = 0;
		}
	}
}
