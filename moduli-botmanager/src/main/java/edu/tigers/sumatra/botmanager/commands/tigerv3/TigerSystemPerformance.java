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

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class TigerSystemPerformance extends ACommand
{
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	accMax	= 2000;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	accMaxW	= 3000;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	brkMax	= 5000;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	brkMaxW	= 6000;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	velMax	= 1800;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int	velMaxW	= 3000;
	
	
	
	public TigerSystemPerformance()
	{
		super(ECommand.CMD_SYSTEM_PERFORMANCE);
	}
	
	
	
	public double getAccMax()
	{
		return accMax * 0.001;
	}
	
	
	
	public void setAccMax(final double accMax)
	{
		this.accMax = (int) (accMax * 1000.0);
	}
	
	
	
	public double getAccMaxW()
	{
		return accMaxW * 0.01;
	}
	
	
	
	public void setAccMaxW(final double accMaxW)
	{
		this.accMaxW = (int) (accMaxW * 100.0);
	}
	
	
	
	public double getBrkMax()
	{
		return brkMax * 0.001;
	}
	
	
	
	public void setBrkMax(final double brkMax)
	{
		this.brkMax = (int) (brkMax * 1000.0);
	}
	
	
	
	public double getBrkMaxW()
	{
		return brkMaxW * 0.01;
	}
	
	
	
	public void setBrkMaxW(final double brkMaxW)
	{
		this.brkMaxW = (int) (brkMaxW * 100.0);
	}
	
	
	
	public double getVelMax()
	{
		return velMax * 0.001;
	}
	
	
	
	public void setVelMax(final double velMax)
	{
		this.velMax = (int) (velMax * 1000.0);
	}
	
	
	
	public double getVelMaxW()
	{
		return velMaxW * 0.01;
	}
	
	
	
	public void setVelMaxW(final double velMaxW)
	{
		this.velMaxW = (int) (velMaxW * 100.0);
	}
}
