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
import edu.tigers.sumatra.botmanager.commands.CommandFactory;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;
import edu.tigers.sumatra.ids.AObjectID;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class BaseStationACommand extends ACommand
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	// Cached variables
	private ACommand	child	= null;
	private BotID		id		= null;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int			idData;
	@SerialData(type = ESerialDataType.TAIL)
	private byte[]		childData;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BaseStationACommand()
	{
		super(ECommand.CMD_BASE_ACOMMAND);
	}
	
	
	
	public BaseStationACommand(final BotID id, final ACommand command)
	{
		super(ECommand.CMD_BASE_ACOMMAND);
		
		setId(id);
		setChild(command);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ACommand getChild()
	{
		if (child == null)
		{
			child = CommandFactory.getInstance().decode(childData, false);
		}
		
		return child;
	}
	
	
	
	public void setChild(final ACommand child)
	{
		childData = CommandFactory.getInstance().encode(child, false);
		this.child = child;
	}
	
	
	
	public BotID getId()
	{
		if (id == null)
		{
			id = getBotIdFromBaseStationId(idData);
		}
		
		return id;
	}
	
	
	
	public void setId(final BotID id)
	{
		idData = getBaseStationIdFromBotId(id);
		this.id = id;
	}
	
	
	
	public static BotID getBotIdFromBaseStationId(final int id)
	{
		if (id == AObjectID.UNINITIALIZED_ID)
		{
			return BotID.get();
		}
		
		if (id > AObjectID.BOT_ID_MAX)
		{
			return BotID.createBotId(id - (AObjectID.BOT_ID_MAX + 1), ETeamColor.BLUE);
		}
		
		return BotID.createBotId(id, ETeamColor.YELLOW);
	}
	
	
	
	public static int getBaseStationIdFromBotId(final BotID id)
	{
		if (id.getNumber() == AObjectID.UNINITIALIZED_ID)
		{
			return 255;
		}
		
		if (id.getTeamColor() == ETeamColor.BLUE)
		{
			return id.getNumber() + AObjectID.BOT_ID_MAX + 1;
		}
		
		return id.getNumber();
	}
}
