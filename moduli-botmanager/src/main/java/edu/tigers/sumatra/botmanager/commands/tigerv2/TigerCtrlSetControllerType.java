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



public class TigerCtrlSetControllerType extends ACommand
{
	
	public static enum EControllerType
	{
		
		NONE(0x00),
		
		FUSION_VEL(0x01),
		
		FUSION(0x02),
		
		MOTOR(0x03),
		
		CALIBRATE(0x04),
		
		TIGGA(0x05);
		
		private int	id;
		
		
		private EControllerType(final int i)
		{
			id = i;
		}
		
		
		
		public int getId()
		{
			return id;
		}
		
		
		
		public static EControllerType getControllerTypeConstant(final int type)
		{
			for (EControllerType t : values())
			{
				if (t.getId() == type)
				{
					return t;
				}
			}
			
			return NONE;
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	@SerialData(type = ESerialDataType.UINT8)
	private int	type	= EControllerType.NONE.getId();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerCtrlSetControllerType()
	{
		super(ECommand.CMD_CTRL_SET_CONTROLLER_TYPE, true);
	}
	
	
	
	public TigerCtrlSetControllerType(final EControllerType t)
	{
		super(ECommand.CMD_CTRL_SET_CONTROLLER_TYPE, true);
		
		type = t.getId();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public EControllerType getControllerType()
	{
		return EControllerType.getControllerTypeConstant(type);
	}
	
	
	
	public void setControllerType(final EControllerType type)
	{
		this.type = type.getId();
	}
}