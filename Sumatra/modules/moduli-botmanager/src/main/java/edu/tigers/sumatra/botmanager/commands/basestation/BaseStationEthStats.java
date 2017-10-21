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
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class BaseStationEthStats extends ACommand
{
	@SerialData(type = ESerialDataType.UINT32)
	private long	txFrames;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	txBytes;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	rxFrames;
	
	@SerialData(type = ESerialDataType.UINT32)
	private long	rxBytes;
	
	@SerialData(type = ESerialDataType.UINT16)
	private int		rxFramesDmaOverrun;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int		ntpSync;
	
	
	
	public BaseStationEthStats()
	{
		super(ECommand.CMD_BASE_ETH_STATS);
	}
	
	
	
	public BaseStationEthStats(final BaseStationEthStats a, final BaseStationEthStats b)
	{
		super(ECommand.CMD_BASE_ETH_STATS);
		
		txFrames = a.txFrames - b.txFrames;
		txBytes = a.txBytes - b.txBytes;
		rxFrames = a.rxFrames - b.rxFrames;
		rxBytes = a.rxBytes - b.rxBytes;
		rxFramesDmaOverrun = a.rxFramesDmaOverrun - b.rxFramesDmaOverrun;
		ntpSync = a.ntpSync;
	}
	
	
	
	public long getTxFrames()
	{
		return txFrames;
	}
	
	
	
	public long getTxBytes()
	{
		return txBytes;
	}
	
	
	
	public long getRxFrames()
	{
		return rxFrames;
	}
	
	
	
	public long getRxBytes()
	{
		return rxBytes;
	}
	
	
	
	public int getRxFramesDmaOverrun()
	{
		return rxFramesDmaOverrun;
	}
	
	
	
	public double getRxLoss()
	{
		double goodFrames = rxFrames;
		double badFrames = rxFramesDmaOverrun;
		
		return badFrames / (badFrames + goodFrames + 1);
	}
	
	
	
	public boolean isNtpSync()
	{
		return ntpSync == 1 ? true : false;
	}
}
