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



public class TigerSystemQuery extends ACommand
{
	@SerialData(type = ESerialDataType.UINT16)
	private int	queryType	= 0;
	
	
	public enum EQueryType
	{
		
		CTRL_PID(0),
		
		CTRL_STRUCTURE(1),
		
		CTRL_SENSOR(2),
		
		CTRL_STATE(3),
		
		CTRL_TYPE(4),
		
		KICKER_CONFIG(5);
		
		private final int	id;
		
		
		private EQueryType(final int id)
		{
			this.id = id;
		}
	}
	
	
	
	public TigerSystemQuery()
	{
		super(ECommand.CMD_SYSTEM_QUERY, true);
	}
	
	
	
	public TigerSystemQuery(final EQueryType queryType)
	{
		super(ECommand.CMD_SYSTEM_QUERY, true);
		this.queryType = queryType.id;
	}
	
	
	
	public void setQueryType(final EQueryType queryType)
	{
		this.queryType = queryType.id;
	}
}
