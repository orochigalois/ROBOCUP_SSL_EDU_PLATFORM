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
import edu.tigers.sumatra.ids.BotID;



public class BaseStationWifiStats extends ACommand
{
	
	public static final int	NUM_BOTS	= 24;
	
	
	public static class BotStats
	{
		
		public static class NRF24IOStats
		{
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txPackets;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txBytes;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxPackets;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxBytes;
			
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxPacketsLost;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txPacketsMaxRT;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txPacketsAcked;
			
			
			
			public NRF24IOStats subtractNew(final NRF24IOStats rhs)
			{
				NRF24IOStats ret = new NRF24IOStats();
				
				ret.txPackets = txPackets - rhs.txPackets;
				ret.txBytes = txBytes - rhs.txBytes;
				ret.rxPackets = rxPackets - rhs.rxPackets;
				ret.rxBytes = rxBytes - rhs.rxBytes;
				
				ret.rxPacketsLost = rxPacketsLost - rhs.rxPacketsLost;
				ret.txPacketsMaxRT = txPacketsMaxRT - rhs.txPacketsMaxRT;
				ret.txPacketsAcked = txPacketsAcked - rhs.txPacketsAcked;
				
				ret.txPackets &= 0xFFFF;
				ret.txBytes &= 0xFFFF;
				ret.rxPackets &= 0xFFFF;
				ret.rxBytes &= 0xFFFF;
				ret.rxPacketsLost &= 0xFFFF;
				ret.txPacketsMaxRT &= 0xFFFF;
				ret.txPacketsAcked &= 0xFFFF;
				
				return ret;
			}
			
			
			
			public double getLinkQuality()
			{
				double allGoodPackets = txPacketsAcked + rxPackets + 1; // +1 to prevent div by zero
				double allLostPackets = rxPacketsLost + txPacketsMaxRT;
				
				return allGoodPackets / (allGoodPackets + allLostPackets);
			}
			
			
			
			public double getTxLoss()
			{
				double txAll = txPackets + 1;
				double txBad = txPacketsMaxRT;
				
				return txBad / txAll;
			}
			
			
			
			public double getRxLoss()
			{
				double rxGood = rxPackets + 1;
				double rxBad = rxPacketsLost;
				
				return rxBad / (rxBad + rxGood);
			}
			
			
			
			public double getTxSaturation(final double rate)
			{
				return txBytes / (rate * 32);
			}
			
			
			
			public double getRxSaturation(final double rate)
			{
				return rxBytes / (rate * 32);
			}
		}
		
		
		public static class QueueIOStats
		{
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txPackets;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txBytes;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxPackets;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxBytes;
			
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	txPacketsLost;
			
			@SerialData(type = ESerialDataType.UINT16)
			public int	rxPacketsLost;
			
			
			
			public QueueIOStats subtractNew(final QueueIOStats rhs)
			{
				QueueIOStats ret = new QueueIOStats();
				
				ret.txPackets = txPackets - rhs.txPackets;
				ret.txBytes = txBytes - rhs.txBytes;
				ret.rxPackets = rxPackets - rhs.rxPackets;
				ret.rxBytes = rxBytes - rhs.rxBytes;
				ret.txPacketsLost = txPacketsLost - rhs.txPacketsLost;
				ret.rxPacketsLost = rxPacketsLost - rhs.rxPacketsLost;
				
				ret.txPackets &= 0xFFFF;
				ret.txBytes &= 0xFFFF;
				ret.rxPackets &= 0xFFFF;
				ret.rxBytes &= 0xFFFF;
				ret.txPacketsLost &= 0xFFFF;
				ret.rxPacketsLost &= 0xFFFF;
				
				return ret;
			}
			
			
			
			public double getTxLoss()
			{
				double txGood = txPackets + 1;
				double txBad = txPacketsLost;
				
				return txBad / (txGood + txBad);
			}
			
			
			
			public double getRxLoss()
			{
				double rxGood = rxPackets + 1;
				double rxBad = rxPacketsLost;
				
				return rxBad / (rxGood + rxBad);
			}
		}
		
		
		@SerialData(type = ESerialDataType.UINT8)
		private int				botId	= 0xFF;
		
		
		@SerialData(type = ESerialDataType.EMBEDDED)
		public NRF24IOStats	nrf	= new NRF24IOStats();
		
		
		@SerialData(type = ESerialDataType.EMBEDDED)
		public QueueIOStats	queue	= new QueueIOStats();
		
		
		
		public BotStats subtractNew(final BotStats rhs)
		{
			BotStats ret = new BotStats();
			
			ret.botId = rhs.botId;
			ret.nrf = nrf.subtractNew(rhs.nrf);
			ret.queue = queue.subtractNew(rhs.queue);
			
			return ret;
		}
		
		
		
		public BotID getBotId()
		{
			return BotID.createBotIdFromIdWithColorOffsetBS(botId);
		}
	}
	
	@SerialData(type = ESerialDataType.EMBEDDED)
	private final BotStats	bots[]		= new BotStats[NUM_BOTS];
	
	@SerialData(type = ESerialDataType.UINT16)
	private int					updateRate	= 1;
	
	
	
	public BaseStationWifiStats()
	{
		super(ECommand.CMD_BASE_WIFI_STATS);
		
		for (int i = 0; i < NUM_BOTS; i++)
		{
			bots[i] = new BotStats();
		}
	}
	
	
	
	public BaseStationWifiStats(final BaseStationWifiStats a, final BaseStationWifiStats b)
	{
		super(ECommand.CMD_BASE_WIFI_STATS);
		
		updateRate = a.updateRate;
		
		for (int i = 0; i < NUM_BOTS; i++)
		{
			bots[i] = a.getBotStats()[i].subtractNew(b.getBotStats()[i]);
		}
	}
	
	
	
	public BotStats[] getBotStats()
	{
		return bots;
	}
	
	
	
	public int getUpdateRate()
	{
		return updateRate;
	}
	
	
	
	public boolean isBotConnected(final BotID id)
	{
		int number = id.getNumberWithColorOffsetBS();
		
		for (BotStats s : bots)
		{
			if (s.botId == number)
			{
				return true;
			}
		}
		
		return false;
	}
}
