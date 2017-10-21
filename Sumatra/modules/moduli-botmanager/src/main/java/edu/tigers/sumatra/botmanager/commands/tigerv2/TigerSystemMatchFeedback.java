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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.bot.EFeature;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.botmanager.serial.SerialData;
import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public class TigerSystemMatchFeedback extends ACommand
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	@SerialData(type = ESerialDataType.INT16)
	private final int					curPosition[]							= new int[3];
	
	
	@SerialData(type = ESerialDataType.INT16)
	private final int					curVelocity[]							= new int[3];
	
	
	@SerialData(type = ESerialDataType.UINT8)
	private int							kickerLevel;
	
	
	@SerialData(type = ESerialDataType.INT16)
	private int							dribblerSpeed;
	
	
	@SerialData(type = ESerialDataType.UINT16)
	private int							batteryLevel;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int							barrierKickCounter;
	
	@SerialData(type = ESerialDataType.UINT16)
	private int							features									= 0x001F;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int							hardwareId;
	
	@SerialData(type = ESerialDataType.UINT8)
	private int							dribblerTemp;
	
	private static final int		UNUSED_FIELD							= 0x7FFF;
	
	private static final double	BAT_CELL_COUNT_THRESHOLD_VOLTAGE	= 13.0;
	private static final double	BAT_3S_MIN_VOLTAGE					= 10.5;
	private static final double	BAT_3S_MAX_VOLTAGE					= 12.6;
	private static final double	BAT_4S_MIN_VOLTAGE					= 13.6;
	private static final double	BAT_4S_MAX_VOLTAGE					= 16.8;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public TigerSystemMatchFeedback()
	{
		super(ECommand.CMD_SYSTEM_MATCH_FEEDBACK);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Vector2 getVelocity()
	{
		return new Vector2(curVelocity[0] / 1000.0, curVelocity[1] / 1000.0);
	}
	
	
	
	public double getAngularVelocity()
	{
		return curVelocity[2] / 1000.0;
	}
	
	
	
	public Vector2 getAcceleration()
	{
		return new Vector2();
	}
	
	
	
	public double getAngularAcceleration()
	{
		return 0;
	}
	
	
	
	public Vector2 getPosition()
	{
		return new Vector2(curPosition[0] / 1000.0, curPosition[1] / 1000.0);
	}
	
	
	
	public double getOrientation()
	{
		return curPosition[2] / 1000.0;
	}
	
	
	
	public boolean isPositionValid()
	{
		if (curPosition[0] == UNUSED_FIELD)
		{
			return false;
		}
		
		return true;
	}
	
	
	
	public boolean isVelocityValid()
	{
		if (curVelocity[0] == UNUSED_FIELD)
		{
			return false;
		}
		
		return true;
	}
	
	
	
	public boolean isAccelerationValid()
	{
		return false;
	}
	
	
	
	public double getKickerLevel()
	{
		return kickerLevel;
	}
	
	
	
	public double getDribblerSpeed()
	{
		return dribblerSpeed;
	}
	
	
	
	public double getBatteryLevel()
	{
		return batteryLevel * 0.001;
	}
	
	
	
	public double getBatteryPercentage()
	{
		double bat = getBatteryLevel();
		
		if (bat < BAT_CELL_COUNT_THRESHOLD_VOLTAGE)
		{
			// That's a 3S battery
			if (bat > BAT_3S_MAX_VOLTAGE)
			{
				return 1.0;
			}
			
			if (bat < BAT_3S_MIN_VOLTAGE)
			{
				return 0.0;
			}
			
			return ((bat - BAT_3S_MIN_VOLTAGE)) / (BAT_3S_MAX_VOLTAGE - BAT_3S_MIN_VOLTAGE);
		}
		
		// And that's a 4S
		if (bat > BAT_4S_MAX_VOLTAGE)
		{
			return 1.0;
		}
		
		if (bat < BAT_4S_MIN_VOLTAGE)
		{
			return 0.0;
		}
		
		return ((bat - BAT_4S_MIN_VOLTAGE)) / (BAT_4S_MAX_VOLTAGE - BAT_4S_MIN_VOLTAGE);
	}
	
	
	
	public boolean isBarrierInterrupted()
	{
		if ((barrierKickCounter & 0x80) == 0x80)
		{
			return true;
		}
		
		return false;
	}
	
	
	
	public int getKickCounter()
	{
		return barrierKickCounter & 0x7F;
	}
	
	
	
	public int getHardwareId()
	{
		return hardwareId;
	}
	
	
	
	public double getDribblerTemp()
	{
		return dribblerTemp * 0.5;
	}
	
	
	
	public boolean isFeatureWorking(final EFeature feature)
	{
		if ((features & feature.getId()) != 0)
		{
			return true;
		}
		
		return false;
	}
	
	
	
	public void setFeature(final EFeature feature, final boolean working)
	{
		if (working)
		{
			features |= feature.getId();
		} else
		{
			features &= (feature.getId() ^ 0xFFFF);
		}
	}
}
