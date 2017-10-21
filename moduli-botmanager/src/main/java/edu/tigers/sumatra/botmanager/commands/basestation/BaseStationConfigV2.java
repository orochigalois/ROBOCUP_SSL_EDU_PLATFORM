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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public class BaseStationConfigV2 extends ACommand
{
	
	public enum EWifiSpeed
	{
		
		WIFI_SPEED_250K(0),
		
		WIFI_SPEED_1M(1),
		
		WIFI_SPEED_2M(2),
		
		WIFI_SPEED_UNKNOWN(255);
		
		private final int id;
		
		
		private EWifiSpeed(final int id)
		{
			this.id = id;
		}
		
		
		
		public int getId()
		{
			return id;
		}
		
		
		
		public static EWifiSpeed getSpeedConstant(final int id)
		{
			for (EWifiSpeed s : values())
			{
				if (s.getId() == id)
				{
					return s;
				}
			}
			
			return WIFI_SPEED_UNKNOWN;
		}
	}
	
	
	public static final class BSModuleConfig
	{
		@SerialData(type = ESerialDataType.UINT8)
		private int		channel			= 100;
		@SerialData(type = ESerialDataType.UINT8)
		private int		speed				= 2;
		@SerialData(type = ESerialDataType.UINT8)
		private int		maxBots			= 8;
		@SerialData(type = ESerialDataType.UINT8)
		private int		fixedRuntime	= 0;
		@SerialData(type = ESerialDataType.UINT32)
		private long	timeout			= 1000;
		
		
		
		public int getChannel()
		{
			return channel;
		}
		
		
		
		public void setChannel(final int channel)
		{
			if (channel > MAX_CHANNEL)
			{
				log.error("Invalid max channel: " + channel + ". Max is: " + MAX_CHANNEL);
				return;
			}
			
			this.channel = channel;
		}
		
		
		
		public EWifiSpeed getSpeed()
		{
			return EWifiSpeed.getSpeedConstant(speed);
		}
		
		
		
		public void setSpeed(final EWifiSpeed speed)
		{
			this.speed = speed.getId();
		}
		
		
		
		public int getMaxBots()
		{
			return maxBots;
		}
		
		
		
		public void setMaxBots(final int maxBots)
		{
			if ((maxBots > MAX_BOTS) || (maxBots == 0))
			{
				log.error("Invalid max bots: " + maxBots + ". Max is: " + MAX_BOTS);
				return;
			}
			
			this.maxBots = maxBots;
		}
		
		
		
		public boolean isFixedRuntime()
		{
			return fixedRuntime > 0;
		}
		
		
		
		public void setFixedRuntime(final boolean fixedRuntime)
		{
			this.fixedRuntime = fixedRuntime ? 1 : 0;
		}
		
		
		
		public long getTimeout()
		{
			return timeout;
		}
		
		
		
		public void setTimeout(final long timeout)
		{
			this.timeout = timeout;
		}
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	@SerialData(type = ESerialDataType.UINT8)
	private final int						visionIp[]	= new int[4];
	@SerialData(type = ESerialDataType.UINT16)
	private int								visionPort	= 10002;
	@SerialData(type = ESerialDataType.EMBEDDED)
	private final BSModuleConfig[]	modules		= new BSModuleConfig[NUM_MODULES];
	@SerialData(type = ESerialDataType.UINT8)
	private int								rstEnabled	= 0;
	@SerialData(type = ESerialDataType.UINT8)
	private int								rstRate		= 50;
	@SerialData(type = ESerialDataType.UINT16)
	private int								rstPort		= 10010;
	
	private static final int			MAX_CHANNEL	= 127;
	private static final int			MAX_BOTS		= 24;
	private static final int			NUM_MODULES	= 2;
	
	private static final Logger		log			= Logger.getLogger(BaseStationConfigV2.class.getName());
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BaseStationConfigV2()
	{
		super(ECommand.CMD_BASE_CONFIG_V2);
		
		for (int i = 0; i < NUM_MODULES; i++)
		{
			modules[i] = new BSModuleConfig();
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void setVisionIp(final String ip)
	{
		InetAddress addr;
		try
		{
			addr = InetAddress.getByName(ip);
		} catch (UnknownHostException err)
		{
			log.error("Unknown host: " + ip);
			return;
		}
		
		visionIp[0] = addr.getAddress()[0] & 0xFF;
		visionIp[1] = addr.getAddress()[1] & 0xFF;
		visionIp[2] = addr.getAddress()[2] & 0xFF;
		visionIp[3] = addr.getAddress()[3] & 0xFF;
	}
	
	
	
	public String getVisionIp()
	{
		return new String("" + visionIp[0] + "." + visionIp[1] + "." + visionIp[2] + "." + visionIp[3]);
	}
	
	
	
	public int getVisionPort()
	{
		return visionPort;
	}
	
	
	
	public void setVisionPort(final int visionPort)
	{
		this.visionPort = visionPort;
	}
	
	
	
	public int getRstPort()
	{
		return rstPort;
	}
	
	
	
	public void setRstPort(final int rstPort)
	{
		this.rstPort = rstPort;
	}
	
	
	
	public BSModuleConfig getModuleConfig(final int index)
	{
		if (index > NUM_MODULES)
		{
			log.error("Invalid module index: " + index + ". Max is: " + NUM_MODULES);
			return null;
		}
		
		return modules[index];
	}
	
	
	
	public int getNumModules()
	{
		return NUM_MODULES;
	}
	
	
	
	public int getRstEnabled()
	{
		return rstEnabled;
	}
	
	
	
	public void setRstEnabled(final boolean rstEnabled)
	{
		this.rstEnabled = rstEnabled ? 1 : 0;
	}
	
	
	
	public int getRstRate()
	{
		return rstRate;
	}
	
	
	
	public void setRstRate(final int rstRate)
	{
		this.rstRate = rstRate;
	}
}
