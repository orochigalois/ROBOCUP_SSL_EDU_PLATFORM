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



public class TigerSystemConsoleCommand extends ACommand
{
	
	public static enum ConsoleCommandTarget
	{
		
		UNKNOWN(0),
		
		MAIN(1),
		
		MEDIA(2);
		
		private final int	id;
		
		
		private ConsoleCommandTarget(final int id)
		{
			this.id = id;
		}
		
		
		
		public int getId()
		{
			return id;
		}
		
		
		
		public static ConsoleCommandTarget getTargetConstant(final int id)
		{
			for (ConsoleCommandTarget s : values())
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
	private int		target;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]	textData;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerSystemConsoleCommand()
	{
		super(ECommand.CMD_SYSTEM_CONSOLE_COMMAND, true);
	}
	
	
	
	public TigerSystemConsoleCommand(final ConsoleCommandTarget target, final String command)
	{
		this();
		setTarget(target);
		setText(command);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
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
	
	
	
	public ConsoleCommandTarget getTarget()
	{
		return ConsoleCommandTarget.getTargetConstant(target);
	}
	
	
	
	public void setTarget(final ConsoleCommandTarget target)
	{
		this.target = target.getId();
	}
	
}
